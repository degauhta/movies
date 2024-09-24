package ru.androidschool.intensiv.presentation.moviedetails

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.chip.Chip
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.MovieFinderApp
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.FragmentMovieDetailsBinding
import ru.androidschool.intensiv.di.moviedetails.DaggerMovieDetailsInnerApi
import ru.androidschool.intensiv.models.domain.Genre
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsArgs
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsScreenAction
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsScreenModel
import ru.androidschool.intensiv.presentation.BaseFragment
import ru.androidschool.intensiv.presentation.OffsetItemDecorator
import ru.androidschool.intensiv.presentation.feed.FeedFragment
import ru.androidschool.intensiv.utils.loadImage
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsScreenAction as Action
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsScreenEffect as Effect
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsScreenState as State
import ru.androidschool.intensiv.presentation.moviedetails.MovieDetailsViewModel as ViewModel

class MovieDetailsFragment : BaseFragment<State, Effect, Action, ViewModel, FragmentMovieDetailsBinding>() {

    private val actorsAdapter by lazy { GroupAdapter<GroupieViewHolder>() }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMovieDetailsBinding.inflate(inflater, container, false)

    override fun createVm(): ViewModel {
        val component = DaggerMovieDetailsInnerApi.builder()
            .coreComponent(MovieFinderApp.coreDaggerComponent)
            .build()
        return ViewModel(component.movieDetailsInteractor, component.actorConverter)
    }

    override fun renderState(state: State) {
        when (state) {
            is State.Content -> renderUi(state.screenModel)
        }
    }

    override fun renderEffect(effect: Effect) {
        when (effect) {
            is Effect.ShowMessage -> showToast(effect.stringRes)
        }
    }

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.actorsRecycler.adapter = actorsAdapter
        binding.actorsRecycler.addItemDecoration(OffsetItemDecorator())

        val args = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(FeedFragment.MOVIE_KEY, MovieDetailsArgs::class.java)
        } else {
            arguments?.getParcelable(FeedFragment.MOVIE_KEY) as? MovieDetailsArgs
        }

        viewModel.handleAction(Action.Load(args))
    }

    private fun renderUi(screenModel: MovieDetailsScreenModel) {
        initBaseViews(screenModel.movie)
        screenModel.genre.takeIf { it.isNotEmpty() }?.let { showGenres(it) }
        screenModel.actors.takeIf { it.isNotEmpty() }?.let { showActors(it) }
    }

    private fun initBaseViews(movie: Movie) {
        binding.image.loadImage(
            imageUrl = movie.imageUrl,
            placeholderResId = R.drawable.item_placeholder
        )

        binding.title.text = movie.title
        binding.rating.rating = movie.rating

        binding.overviewTitle.isVisible = movie.overview.isNotEmpty()
        binding.overview.text = movie.overview

        binding.favoriteCheckbox.isChecked = movie.isFavorite
        binding.favoriteCheckbox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.handleAction(MovieDetailsScreenAction.FavoriteClick(isChecked))
        }
    }

    private fun showGenres(genres: List<Genre>) {
        if (genres.isNotEmpty()) {
            binding.genresChipGroup.removeAllViews()
        }
        genres.forEach { genre ->
            val chip = Chip(requireActivity()).apply {
                text = genre.name
                isClickable = false
                isCloseIconVisible = false
            }
            binding.genresChipGroup.addView(chip)
        }
    }

    private fun showActors(actors: List<ActorItem>) {
        binding.actorsTitle.isVisible = true
        actorsAdapter.apply { addAll(actors) }
    }
}
