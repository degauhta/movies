package ru.androidschool.intensiv.presentation.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.presentation.converters.TvShowConverter
import ru.androidschool.intensiv.data.repository.MovieRepository
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.databinding.FragmentTvShowsBinding
import ru.androidschool.intensiv.models.data.response.MoviesResponse
import ru.androidschool.intensiv.presentation.BaseFragment
import ru.androidschool.intensiv.presentation.feed.FeedFragment
import timber.log.Timber

class TvShowsFragment : BaseFragment<FragmentTvShowsBinding>() {

    private val adapter by lazy { GroupAdapter<GroupieViewHolder>() }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentTvShowsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.serialsRecyclerView.adapter = adapter
        getSerials()
    }

    private fun getSerials() {
        MovieRepository.getTopRatedTvShow().enqueue(object : Callback<MoviesResponse> {

            override fun onResponse(
                call: Call<MoviesResponse>,
                respose: Response<MoviesResponse>
            ) {
                respose.body()?.let { body ->
                    val tvShowsItems = TvShowConverter().convert(body) { openTvShowDetails(it) }
                    binding.serialsProgressbar.hide()
                    adapter.apply { addAll(tvShowsItems) }
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, error: Throwable) {
                showToast(R.string.load_data_error)
                Timber.e(error)
            }
        })
    }

    private fun openTvShowDetails(movie: Movie) {
        val bundle = Bundle()
        bundle.putParcelable(FeedFragment.MOVIE_KEY, movie)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }
}
