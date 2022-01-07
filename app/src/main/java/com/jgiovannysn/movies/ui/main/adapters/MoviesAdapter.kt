package com.jgiovannysn.movies.ui.main.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jgiovannysn.movies.R
import com.jgiovannysn.movies.base.BaseAdapter
import com.jgiovannysn.movies.data.entities.Movie
import com.jgiovannysn.movies.ui.main.viewHolders.MoviesViewHolder

/**
 * @author JGiovannySN 05/01/22.
 * @version 1.0
 * @since 1.0
 */
class MoviesAdapter(): BaseAdapter<Movie>() {
    /**
     *Define layout for items of RecyclerView
     */
    override fun getLayoutId(position: Int, item: Movie): Int = R.layout.item_movie

    /**
     *Define viewHolder of RecyclerView
     */
    override fun getViewHolder(container: View, viewType: Int): RecyclerView.ViewHolder {
        return MoviesViewHolder(container)
    }
}