package com.jgiovannysn.movies.ui.main.adapters

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jgiovannysn.movies.R
import com.jgiovannysn.movies.ui.main.fragments.MapsFragment
import com.jgiovannysn.movies.ui.main.fragments.MoviesFragment
import com.jgiovannysn.movies.ui.main.fragments.PhotosFragment
import java.util.*

/**
 * @author JGiovannySN 05/01/22.
 * @version 1.0
 * @since 1.0
 */
internal class MainViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    internal enum class Tab(
        val position: Int,
        @param:StringRes val title: Int
    ) {
        MOVIES(0, R.string.tab_movies),
        MAPS(1, R.string.tab_maps),
        PHOTOS(2, R.string.tab_photos);

        companion object {
            private var map: MutableMap<Int, Tab>? = null
            fun byPosition(position: Int): Tab? {
                return map!![position]
            }

            init {
                map = HashMap()
                for (t in values()) {
                    (map as HashMap<Int, Tab>)[t.position] = t
                }
            }
        }
    }

    /**
     * Retrieve the fragments used by the viewpager
     */
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            Tab.MOVIES.position -> {
                MoviesFragment.newInstance()
            }
            Tab.MAPS.position -> {
                MapsFragment.newInstance()
            }
            Tab.PHOTOS.position -> {
                PhotosFragment.newInstance()
            }
            else -> {
                throw IllegalArgumentException(
                    "unknown position $position"
                )
            }
        }
    }

    /**
     * Retrieve number of fragments used by the viewpager
     */
    override fun getItemCount(): Int {
        return Tab.values().size
    }
}
