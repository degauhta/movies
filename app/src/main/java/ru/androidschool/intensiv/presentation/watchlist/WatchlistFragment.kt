package ru.androidschool.intensiv.presentation.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.coroutines.launch
import ru.androidschool.intensiv.databinding.FragmentWatchlistBinding
import ru.androidschool.intensiv.models.presentation.profile.ProfileScreenState
import ru.androidschool.intensiv.models.presentation.profile.ProfileTabType
import ru.androidschool.intensiv.presentation.profile.ProfileViewModel

class WatchlistFragment : Fragment() {

    private val viewModel by activityViewModels<ProfileViewModel>()
    private var _binding: FragmentWatchlistBinding? = null
    private val binding get() = _binding!!
    private var profileTabType: ProfileTabType? = null

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)
        profileTabType = arguments?.getSerializable(TYPE) as? ProfileTabType
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moviesRecyclerView.layoutManager = GridLayoutManager(context, 4)
        binding.moviesRecyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is ProfileScreenState.Content -> {
                            adapter.clear()
                            when (profileTabType) {
                                ProfileTabType.FAVORITE -> adapter.addAll(state.model.favorites)
                                ProfileTabType.WATCH -> Unit // adapter.addAll(state.model.watch)
                                null -> Unit
                            }
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TYPE = "TYPE"

        @JvmStatic
        fun newInstance(type: ProfileTabType?): WatchlistFragment {
            return WatchlistFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(TYPE, type)
                }
            }
        }
    }
}
