package ru.androidschool.intensiv.presentation.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.presentation.converters.TvShowConverter
import ru.androidschool.intensiv.data.repository.MovieRepository
import ru.androidschool.intensiv.models.domain.Movie
import ru.androidschool.intensiv.databinding.FragmentTvShowsBinding
import ru.androidschool.intensiv.presentation.BaseFragmentOld
import ru.androidschool.intensiv.presentation.feed.FeedFragment
import ru.androidschool.intensiv.utils.ioToMainTransform

class TvShowsFragment : BaseFragmentOld<FragmentTvShowsBinding>() {

    private val adapter by lazy { GroupAdapter<GroupieViewHolder>() }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentTvShowsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.serialsRecyclerView.adapter = adapter
        getSerials()
    }

    private fun getSerials() {
        rxCompositeDisposable.add(MovieRepository.getTopRatedTvShow()
            .map { TvShowConverter().convert(it) { movie -> openTvShowDetails(movie) } }
            .ioToMainTransform()
            .doOnSubscribe { binding.serialsProgressbar.show() }
            .doFinally { binding.serialsProgressbar.hide() }
            .subscribe(::handleSuccess, ::handleError)
        )
    }

    private fun handleSuccess(movies: List<TvShowItem>) {
        binding.serialsProgressbar.hide()
        adapter.apply { addAll(movies) }
    }

    private fun openTvShowDetails(movie: Movie) {
        val bundle = Bundle()
        bundle.putParcelable(FeedFragment.MOVIE_KEY, movie)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }
}
