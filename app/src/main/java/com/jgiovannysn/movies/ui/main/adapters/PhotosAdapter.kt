package com.jgiovannysn.movies.ui.main.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.StorageReference
import com.jgiovannysn.movies.R
import com.jgiovannysn.movies.base.BaseAdapter
import com.jgiovannysn.movies.ui.main.viewHolders.PhotosViewHolder

/**
 * @author JGiovannySN 06/01/22.
 * @version 1.0
 * @since 1.0
 */
class PhotosAdapter(): BaseAdapter<StorageReference>() {
    /**
     *Define layout for items of RecyclerView
     */
    override fun getLayoutId(position: Int, item: StorageReference): Int = R.layout.item_photo

    /**
     *Define viewHolder of RecyclerView
     */
    override fun getViewHolder(container: View, viewType: Int): RecyclerView.ViewHolder {
        return PhotosViewHolder(container)
    }
}