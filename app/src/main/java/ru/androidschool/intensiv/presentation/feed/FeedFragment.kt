package ru.androidschool.intensiv.presentation.feed

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.MovieFinderApp
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.databinding.FragmentFeedBinding
import ru.androidschool.intensiv.di.feed.DaggerFeedInnerApi
import ru.androidschool.intensiv.models.domain.MovieTypes
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsArgs
import ru.androidschool.intensiv.presentation.BaseFragment
import ru.androidschool.intensiv.presentation.OffsetItemDecorator
import ru.androidschool.intensiv.models.presentation.feed.FeedScreenAction as Action
import ru.androidschool.intensiv.models.presentation.feed.FeedScreenEffect as Effect
import ru.androidschool.intensiv.models.presentation.feed.FeedScreenState as State
import ru.androidschool.intensiv.presentation.feed.FeedViewModel as ViewModel

class FeedFragment : BaseFragment<State, Effect, Action, ViewModel, FragmentFeedBinding>() {

    private var _searchBinding: FeedHeaderBinding? = null
    private val searchBinding get() = _searchBinding!!

    private val adapter by lazy { GroupAdapter<GroupieViewHolder>() }

    private val adapterGroupIndexes = mutableMapOf<MovieTypes, Int>()

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentFeedBinding.inflate(inflater, container, false)

    override fun createVm(): ViewModel {
        val innerApi =
            DaggerFeedInnerApi.builder().coreComponent(MovieFinderApp.coreDaggerComponent).build()
        return ViewModel(innerApi.feedInteractor, innerApi.movieConverter)
    }

    override fun renderState(state: State) {
        when (state) {
            State.Loading -> binding.moviesProgressbar.show()
            is State.Error -> {
                binding.moviesProgressbar.hide()
                showMessage(state.title, state.message)
            }
            is State.Content -> handleSuccess(state.data)
        }
    }

    override fun renderEffect(effect: Effect) {
        when (effect) {
            is Effect.NavigateToMovieDetail -> openMovieDetails(effect.args)
            is Effect.ShowMessage -> showToast(effect.stringRes)
        }
    }

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
        adapterGroupIndexes.clear()
        binding.moviesRecyclerView.addItemDecoration(OffsetItemDecorator())
        binding.moviesRecyclerView.adapter = adapter

        viewModel.handleAction(Action.Load)
    }

    private fun handleSuccess(keyItems: Map<MovieTypes, List<MovieItem>>) {
        binding.moviesProgressbar.hide()
        populateAdapterGroup(keyItems, MovieTypes.TOP)
        populateAdapterGroup(keyItems, MovieTypes.POPULAR)
        populateAdapterGroup(keyItems, MovieTypes.NOW_PLAYING)
    }

    private fun populateAdapterGroup(
        keyItems: Map<MovieTypes, List<MovieItem>>,
        type: MovieTypes
    ) {
        keyItems[type].takeIf { !it.isNullOrEmpty() }?.let { movies ->
            adapterGroupIndexes[type]?.let { index ->
                (adapter.getGroupAtAdapterPosition(index) as MainCardContainer).updateAsync(movies)
            } ?: run {
                val newIndex = adapter.groupCount
                adapter.add(MainCardContainer(title = type.resId, items = movies))
                adapterGroupIndexes[type] = newIndex
            }
        }
    }

    private fun openMovieDetails(args: MovieDetailsArgs) {
        val bundle = Bundle()
        bundle.putParcelable(MOVIE_KEY, args)
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
