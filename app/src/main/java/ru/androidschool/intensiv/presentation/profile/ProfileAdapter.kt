package ru.androidschool.intensiv.presentation.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.androidschool.intensiv.models.presentation.profile.ProfileTabType
import ru.androidschool.intensiv.presentation.watchlist.WatchlistFragment

class ProfileAdapter(
    fragment: Fragment,
    private val itemsCount: Int
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        val type = when (position) {
            0 -> ProfileTabType.FAVORITE
            1 -> ProfileTabType.WATCH
            else -> null
        }
        return WatchlistFragment.newInstance(type)
    }
}
