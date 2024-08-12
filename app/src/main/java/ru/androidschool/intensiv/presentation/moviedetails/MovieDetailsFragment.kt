package ru.androidschool.intensiv.presentation.moviedetails

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.repository.MovieRepository
import ru.androidschool.intensiv.databinding.FragmentMovieDetailsBinding
import ru.androidschool.intensiv.models.data.response.CreditsResponse
import ru.androidschool.intensiv.models.data.response.DetailsResponse
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.presentation.BaseFragment
import ru.androidschool.intensiv.presentation.OffsetItemDecorator
import ru.androidschool.intensiv.presentation.converters.ActorConverter
import ru.androidschool.intensiv.presentation.feed.FeedFragment
import timber.log.Timber

class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {

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
        } ?: {
            showToast(R.string.load_data_error)
        }
    }

    private fun initBaseViews(movie: Movie) {
        Picasso.get()
            .load(movie.imageUrl)
            .placeholder(R.drawable.item_placeholder)
            .into(binding.image)

        binding.title.text = movie.title
        binding.rating.rating = movie.voteAverage

        binding.overviewTitle.isVisible = movie.overview.isNotEmpty()
        binding.overview.text = movie.overview

        binding.actorsRecycler.adapter = actorsAdapter
        binding.actorsRecycler.addItemDecoration(OffsetItemDecorator())
    }

    private fun getDetails(id: Int, isMovie: Boolean) {
        MovieRepository.getMovieDetails(id, isMovie).enqueue(object : Callback<DetailsResponse> {

            override fun onResponse(
                call: Call<DetailsResponse>,
                response: Response<DetailsResponse>
            ) {
                response.body()?.let {
                    if (it.genres.isNotEmpty()) {
                        binding.genresChipGroup.removeAllViews()
                    }
                    it.genres.forEach { genre ->
                        val chip = Chip(requireActivity()).apply {
                            text = genre.name
                            isClickable = false
                            isCloseIconVisible = false
                        }
                        binding.genresChipGroup.addView(chip)
                    }
                }
                getTvShowCredits(id, isMovie)
            }

            override fun onFailure(call: Call<DetailsResponse>, error: Throwable) {
                showToast(R.string.load_data_error)
                Timber.e(error)
            }
        })
    }

    private fun getTvShowCredits(id: Int, isMovie: Boolean) {
        MovieRepository.getMovieCredits(id, isMovie).enqueue(object : Callback<CreditsResponse> {

            override fun onResponse(
                call: Call<CreditsResponse>,
                response: Response<CreditsResponse>
            ) {
                response.body()?.let {
                    if (it.cast.isNotEmpty()) {
                        binding.actorsTitle.isVisible = true
                    }
                    actorsAdapter.apply { addAll(ActorConverter().convert(it)) }
                }
            }

            override fun onFailure(call: Call<CreditsResponse>, error: Throwable) {
                showToast(R.string.load_data_error)
                Timber.e(error)
            }
        })
    }
}
