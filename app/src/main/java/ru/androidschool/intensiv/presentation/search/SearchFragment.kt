package ru.androidschool.intensiv.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.MovieFinderApp
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.databinding.FragmentSearchBinding
import ru.androidschool.intensiv.di.search.DaggerSearchInnerApi
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsArgs
import ru.androidschool.intensiv.models.presentation.search.SearchScreenAction
import ru.androidschool.intensiv.models.presentation.search.SearchScreenEffect
import ru.androidschool.intensiv.models.presentation.search.SearchScreenState
import ru.androidschool.intensiv.presentation.BaseFragment
import ru.androidschool.intensiv.presentation.feed.FeedFragment.Companion.KEY_SEARCH
import ru.androidschool.intensiv.presentation.feed.FeedFragment.Companion.MOVIE_KEY
import ru.androidschool.intensiv.models.presentation.search.SearchScreenAction as Action
import ru.androidschool.intensiv.models.presentation.search.SearchScreenEffect as Effect
import ru.androidschool.intensiv.models.presentation.search.SearchScreenState as State
import ru.androidschool.intensiv.presentation.search.SearchViewModel as ViewModel

class SearchFragment : BaseFragment<State, Effect, Action, ViewModel, FragmentSearchBinding>() {

    private var _searchBinding: FeedHeaderBinding? = null
    private val searchBinding get() = _searchBinding!!

    private val adapter by lazy { GroupAdapter<GroupieViewHolder>() }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSearchBinding.inflate(inflater, container, false)

    override fun createVm(): ViewModel {
        val innerApi =
            DaggerSearchInnerApi.builder().coreComponent(MovieFinderApp.coreDaggerComponent).build()
        return ViewModel(innerApi.searchInteractor)
    }

    override fun renderEffect(effect: SearchScreenEffect) {
        when (effect) {
            is SearchScreenEffect.NavigateToMovieDetail -> openMovieDetails(effect.detailsArgs)
            is SearchScreenEffect.ShowMessage -> showToast(effect.stringRes)
        }
    }

    override fun renderState(state: SearchScreenState) {
        when (state) {
            SearchScreenState.Loading -> {
                adapter.clear()
                binding.emptyContent.isVisible = false
                binding.progressBar.isVisible = true
            }

            is SearchScreenState.Content -> {
                binding.emptyContent.isVisible = false
                binding.progressBar.isGone = true
                adapter.addAll(state.movies)
            }

            is SearchScreenState.Empty -> {
                adapter.clear()
                binding.progressBar.isGone = true
                binding.emptyContent.isVisible = true
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _searchBinding = FeedHeaderBinding.bind(binding.root)
        val searchTerm = requireArguments().getString(KEY_SEARCH)
        searchBinding.searchToolbar.setText(searchTerm)
        searchTerm?.let { viewModel.handleAction(SearchScreenAction.Search(it)) }

        binding.moviesRecyclerView.layoutManager = GridLayoutManager(context, 4)
        binding.moviesRecyclerView.adapter = adapter

        rxCompositeDisposable.add(
            searchBinding.searchToolbar.observeSearchText()
                .subscribe {
                    viewModel.handleAction(SearchScreenAction.Search(it))
                }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _searchBinding = null
    }

    private fun openMovieDetails(args: MovieDetailsArgs) {
        val bundle = Bundle()
        bundle.putParcelable(MOVIE_KEY, args)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }
}
