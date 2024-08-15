package ru.androidschool.intensiv.presentation.feed

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.repository.MovieRepository
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.databinding.FragmentFeedBinding
import ru.androidschool.intensiv.models.data.response.MoviesResponse
import ru.androidschool.intensiv.presentation.BaseFragment
import ru.androidschool.intensiv.presentation.OffsetItemDecorator
import ru.androidschool.intensiv.presentation.afterTextChanged
import ru.androidschool.intensiv.presentation.converters.MovieConverter
import timber.log.Timber

class FeedFragment : BaseFragment<FragmentFeedBinding>() {

    private var _searchBinding: FeedHeaderBinding? = null
    private val searchBinding get() = _searchBinding!!

    private val adapter by lazy { GroupAdapter<GroupieViewHolder>() }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentFeedBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _searchBinding = FeedHeaderBinding.bind(binding.root)

        searchBinding.searchToolbar.binding.searchEditText.afterTextChanged {
            Timber.d(it.toString())
            if (it.toString().length > MIN_LENGTH) {
                openSearch(it.toString())
            }
        }

        adapter.clear()
        binding.moviesRecyclerView.addItemDecoration(OffsetItemDecorator())
        binding.moviesRecyclerView.adapter = adapter

        getMovies()
    }

    private fun getMovies() {
        MovieRepository.getTopRatedMovies().enqueue(object : Callback<MoviesResponse> {

            override fun onResponse(
                call: Call<MoviesResponse>,
                respose: Response<MoviesResponse>
            ) {
                respose.body()?.let { body ->
                    val topMovies = MovieConverter().convert(body) { openMovieDetails(it) }
                    binding.moviesProgressbar.hide()
                    adapter.apply {
                        add(
                            MainCardContainer(
                                title = R.string.recommended,
                                items = topMovies
                            )
                        )
                    }

                    getPopularMovies()
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, error: Throwable) {
                showToast(R.string.load_data_error)
                Timber.e(error)
            }
        })
    }

    private fun getPopularMovies() {
        MovieRepository.getPopularMovies().enqueue(object : Callback<MoviesResponse> {

            override fun onResponse(
                call: Call<MoviesResponse>,
                respose: Response<MoviesResponse>
            ) {
                respose.body()?.let { body ->
                    val popularMovies = MovieConverter().convert(body) { openMovieDetails(it) }
                    binding.moviesProgressbar.hide()
                    adapter.apply {
                        add(
                            MainCardContainer(
                                title = R.string.popular,
                                items = popularMovies
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, error: Throwable) {
                showToast(R.string.load_data_error)
                Timber.e(error)
            }
        })
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
        const val MIN_LENGTH = 3
        const val MOVIE_KEY = "MOVIE_KEY"
        const val KEY_SEARCH = "search"
    }
}
