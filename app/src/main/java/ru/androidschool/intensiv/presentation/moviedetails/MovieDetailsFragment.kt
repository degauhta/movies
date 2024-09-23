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
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.repository.MovieRepository
import ru.androidschool.intensiv.databinding.FragmentMovieDetailsBinding
import ru.androidschool.intensiv.models.data.response.CreditsResponse
import ru.androidschool.intensiv.models.data.response.DetailsResponse
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.presentation.BaseFragmentOld
import ru.androidschool.intensiv.presentation.OffsetItemDecorator
import ru.androidschool.intensiv.presentation.converters.ActorConverter
import ru.androidschool.intensiv.presentation.feed.FeedFragment
import ru.androidschool.intensiv.utils.ioToMainTransform
import ru.androidschool.intensiv.utils.loadImage

class MovieDetailsFragment : BaseFragmentOld<FragmentMovieDetailsBinding>() {

    private val actorsAdapter by lazy { GroupAdapter<GroupieViewHolder>() }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMovieDetailsBinding.inflate(inflater, container, false)

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(FeedFragment.MOVIE_KEY, Movie::class.java)
        } else {
            arguments?.getParcelable(FeedFragment.MOVIE_KEY) as? Movie
        }

        movie?.let {
            initBaseViews(it)
            getDetails(it.id, it.isMovie)
            getTvShowCredits(it.id, it.isMovie)
        } ?: {
            showToast(R.string.load_data_error_title)
        }
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

        binding.actorsRecycler.adapter = actorsAdapter
        binding.actorsRecycler.addItemDecoration(OffsetItemDecorator())
    }

    private fun getDetails(id: Int, isMovie: Boolean) {
        rxCompositeDisposable.add(
            MovieRepository.getMovieDetails(id, isMovie)
                .ioToMainTransform()
                .subscribe(::handleDetailsResponse, ::handleError)
        )
    }

    private fun getTvShowCredits(id: Int, isMovie: Boolean) {
        rxCompositeDisposable.add(
            MovieRepository.getMovieCredits(id, isMovie)
                .ioToMainTransform()
                .subscribe(::handleCreditsResponse, ::handleError)
        )
    }

    private fun handleDetailsResponse(detailsResponse: DetailsResponse) {
        if (detailsResponse.genres.isNotEmpty()) {
            binding.genresChipGroup.removeAllViews()
        }
        detailsResponse.genres.forEach { genre ->
            val chip = Chip(requireActivity()).apply {
                text = genre.name
                isClickable = false
                isCloseIconVisible = false
            }
            binding.genresChipGroup.addView(chip)
        }
    }

    private fun handleCreditsResponse(creditsResponse: CreditsResponse) {
        if (creditsResponse.cast.isNotEmpty()) {
            binding.actorsTitle.isVisible = true
        }
        actorsAdapter.apply { addAll(ActorConverter().convert(creditsResponse)) }
    }
}
