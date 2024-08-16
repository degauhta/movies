package ru.androidschool.intensiv.presentation.feed

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.repository.MovieRepository
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.databinding.FragmentFeedBinding
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.models.domain.MovieTypes
import ru.androidschool.intensiv.presentation.BaseFragment
import ru.androidschool.intensiv.presentation.OffsetItemDecorator
import ru.androidschool.intensiv.presentation.converters.MovieConverter
import ru.androidschool.intensiv.utils.ioToMainTransform
import ru.androidschool.intensiv.utils.onErrorReturnEmptyList

class FeedFragment : BaseFragment<FragmentFeedBinding>() {

    private var _searchBinding: FeedHeaderBinding? = null
    private val searchBinding get() = _searchBinding!!

    private val adapter by lazy { GroupAdapter<GroupieViewHolder>() }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentFeedBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _searchBinding = FeedHeaderBinding.bind(binding.root)

        rxCompositeDisposable.add(
            searchBinding.searchToolbar.observeSearchText()
                .subscribe {
                    openSearch(it)
                }
        )

        adapter.clear()
        binding.moviesRecyclerView.addItemDecoration(OffsetItemDecorator())
        binding.moviesRecyclerView.adapter = adapter

        loadData()
    }

    private fun loadData() {
        rxCompositeDisposable.add(
            Single.zip(
                MovieRepository.getTopRatedMovies().onErrorReturnEmptyList(),
                MovieRepository.getPopularMovies().onErrorReturnEmptyList(),
                MovieRepository.getNowPlayingMovies().onErrorReturnEmptyList()
            ) { top, popular, nowPlaying ->
                mapOf(
                    MovieTypes.TOP to top,
                    MovieTypes.POPULAR to popular,
                    MovieTypes.NOW_PLAYING to nowPlaying
                )
            }
                .map { it ->
                    it.mapValues { keyMoviesResponse ->
                        MovieConverter().convert(keyMoviesResponse.value) { movie ->
                            openMovieDetails(movie)
                        }
                    }
                }
                .ioToMainTransform()
                .doOnSubscribe { binding.moviesProgressbar.show() }
                .doFinally { binding.moviesProgressbar.hide() }
                .subscribe(::handleSuccess, ::handleError)
        )
    }

    private fun handleSuccess(keyItems: Map<MovieTypes, List<MovieItem>>) {
        adapter.apply {
            keyItems[MovieTypes.TOP].takeIf { !it.isNullOrEmpty() }?.let {
                add(MainCardContainer(title = R.string.recommended, items = it))
            }
            keyItems[MovieTypes.POPULAR].takeIf { !it.isNullOrEmpty() }?.let {
                add(MainCardContainer(title = R.string.popular, items = it))
            }
            keyItems[MovieTypes.NOW_PLAYING].takeIf { !it.isNullOrEmpty() }?.let {
                add(MainCardContainer(title = R.string.upcoming, items = it))
            }
        }.also {
            if (adapter.itemCount == 0) {
                handleError(null)
            }
        }
    }

    private fun openMovieDetails(movie: Movie) {
        val bundle = Bundle()
        bundle.putParcelable(MOVIE_KEY, movie)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    private fun openSearch(searchText: String) {
        val bundle = Bundle()
        bundle.putString(KEY_SEARCH, searchText)
        findNavController().navigate(R.id.search_dest, bundle, options)
    }

    override fun onStop() {
        super.onStop()
        searchBinding.searchToolbar.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _searchBinding = null
    }

    companion object {
        const val MOVIE_KEY = "MOVIE_KEY"
        const val KEY_SEARCH = "search"
    }
}
