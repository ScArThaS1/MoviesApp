package com.jgiovannysn.movies.ui.main.viewHolders

import android.view.View
import com.jgiovannysn.movies.BuildConfig
import com.jgiovannysn.movies.base.BaseViewHolder
import com.jgiovannysn.movies.data.entities.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*

/**
 * @author JGiovannySN 06/01/22.
 * @version 1.0
 * @since 1.0
 */
class MoviesViewHolder(override val container: View): BaseViewHolder<Movie>(container) {

    /**
     * Binding information of item with controls in view
     */
    override fun bind(item: Movie) = with(container) {
        super.bind(item)
        title_movie.text = item.title
        original_title_movie.text = item.originalTitle

        Picasso.get()
            .load("${BuildConfig.IMAGE_URL}${item.posterPath}")
            .into(thumbnail)
    }
}