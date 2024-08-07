package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MockRepository
import ru.androidschool.intensiv.data.TvShow
import ru.androidschool.intensiv.databinding.FragmentTvShowsBinding
import ru.androidschool.intensiv.ui.BaseFragment
import ru.androidschool.intensiv.ui.feed.FeedFragment

class TvShowsFragment : BaseFragment<FragmentTvShowsBinding>() {

    private val adapter by lazy { GroupAdapter<GroupieViewHolder>() }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentTvShowsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.serialsRecyclerView.adapter = adapter.apply { addAll(getSerials()) }
    }

    private fun getSerials(): List<TvShowItem> =
        MockRepository.getSerials().map {
            TvShowItem(it) { show ->
                openTvShowDetails(show)
            }
        }.toList()

    private fun openTvShowDetails(show: TvShow) {
        val bundle = Bundle()
        bundle.putString(FeedFragment.KEY_TITLE, show.title)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }
}
