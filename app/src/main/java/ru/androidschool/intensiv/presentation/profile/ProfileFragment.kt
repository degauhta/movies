package ru.androidschool.intensiv.presentation.profile

import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.ServiceLocator
import ru.androidschool.intensiv.databinding.FragmentProfileBinding
import ru.androidschool.intensiv.models.presentation.moviedetail.MovieDetailsArgs
import ru.androidschool.intensiv.models.presentation.profile.ProfileScreenAction
import ru.androidschool.intensiv.models.presentation.profile.ProfileScreenEffect
import ru.androidschool.intensiv.models.presentation.profile.ProfileScreenModel
import ru.androidschool.intensiv.models.presentation.profile.ProfileScreenState
import ru.androidschool.intensiv.presentation.BaseFragment
import ru.androidschool.intensiv.presentation.feed.FeedFragment.Companion.MOVIE_KEY
import ru.androidschool.intensiv.models.presentation.profile.ProfileScreenAction as Action
import ru.androidschool.intensiv.models.presentation.profile.ProfileScreenEffect as Effect
import ru.androidschool.intensiv.models.presentation.profile.ProfileScreenState as State
import ru.androidschool.intensiv.presentation.profile.ProfileViewModel as ViewModel

class ProfileFragment : BaseFragment<State, Effect, Action, ViewModel, FragmentProfileBinding>() {

    init {
        useViewModelStoreOwner = true
    }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentProfileBinding.inflate(inflater, container, false)

    override fun createVm() = ViewModel(ServiceLocator.provideProfileInteractor())

    override fun renderState(state: State) {
        when (state) {
            is ProfileScreenState.Content -> renderUi(state.model)
        }
    }

    private fun renderUi(model: ProfileScreenModel) {
        TabLayoutMediator(binding.tabLayout, binding.profileViewPager) { tab, position ->
            model.tabNames.getOrNull(position)?.let { pair ->
                val title = "${pair.first}${pair.second}"
                val number = pair.first
                val spannableStringTitle = SpannableString(title).apply {
                    setSpan(RelativeSizeSpan(2f), 0, number.toString().count(), 0)
                }
                tab.text = spannableStringTitle
            }
        }.attach()
    }

    override fun renderEffect(effect: Effect) {
        when (effect) {
            is ProfileScreenEffect.NavigateToMovieDetail -> openMovieDetails(effect.args)
            is ProfileScreenEffect.ShowMessage -> showToast(effect.stringRes)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.handleAction(ProfileScreenAction.Load)
        Picasso.get()
            .load(R.drawable.ic_avatar)
            .transform(CropCircleTransformation())
            .placeholder(R.drawable.ic_avatar)
            .into(binding.avatar)
        val profileAdapter = ProfileAdapter(this, TAB_COUNT)
        binding.profileViewPager.adapter = profileAdapter
    }

    private fun openMovieDetails(args: MovieDetailsArgs) {
        val bundle = Bundle()
        bundle.putParcelable(MOVIE_KEY, args)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    companion object {
        private const val TAB_COUNT = 2
    }
}
