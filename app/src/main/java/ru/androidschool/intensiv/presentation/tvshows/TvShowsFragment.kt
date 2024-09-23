package ru.androidschool.intensiv.presentation.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.ServiceLocator
import ru.androidschool.intensiv.databinding.FragmentTvShowsBinding
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsArgs
import ru.androidschool.intensiv.models.presentation.tvshows.TvShowScreenEffect
import ru.androidschool.intensiv.presentation.BaseFragment
import ru.androidschool.intensiv.presentation.converters.TvShowConverter
import ru.androidschool.intensiv.presentation.feed.FeedFragment
import ru.androidschool.intensiv.models.presentation.tvshows.TvShowScreenAction as Action
import ru.androidschool.intensiv.models.presentation.tvshows.TvShowScreenEffect as Effect
import ru.androidschool.intensiv.models.presentation.tvshows.TvShowScreenState as State
import ru.androidschool.intensiv.presentation.tvshows.TvShowViewModel as ViewModel

class TvShowsFragment : BaseFragment<State, Effect, Action, ViewModel, FragmentTvShowsBinding>() {

    private val adapter by lazy { GroupAdapter<GroupieViewHolder>() }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentTvShowsBinding.inflate(inflater, container, false)

    override fun createVm(): ViewModel {
        return ViewModel(ServiceLocator.provideTvShowInteractor(), TvShowConverter())
    }

    override fun renderState(state: State) {
        when (state) {
            State.Loading -> binding.serialsProgressbar.show()
            is State.Error -> {
                binding.serialsProgressbar.hide()
                showMessage(state.title, state.message)
            }
            is State.Content -> renderUi(state.data)
        }
    }

    override fun renderEffect(effect: Effect) {
        when (effect) {
            is Effect.NavigateTvShowDetail -> navigateTvShowDetails(effect.args)
            is TvShowScreenEffect.ShowMessage -> showToast(effect.stringRes)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.serialsRecyclerView.adapter = adapter
        viewModel.handleAction(Action.Load)
    }

    private fun renderUi(movies: List<TvShowItem>) {
        binding.serialsProgressbar.hide()
        adapter.apply { addAll(movies) }
    }

    private fun navigateTvShowDetails(args: MovieDetailsArgs) {
        val bundle = Bundle()
        bundle.putParcelable(FeedFragment.MOVIE_KEY, args)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }
}
