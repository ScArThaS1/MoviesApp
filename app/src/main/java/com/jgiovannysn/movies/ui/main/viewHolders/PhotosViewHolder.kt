package com.jgiovannysn.movies.ui.main.viewHolders

import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.storage.StorageReference
import com.jgiovannysn.movies.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_photo.view.*

/**
 * @author JGiovannySN 06/01/22.
 * @version 1.0
 * @since 1.0
 */
class PhotosViewHolder(override val container: View): BaseViewHolder<StorageReference>(container) {

    /**
     * Binding information of item with controls in view
     */
    override fun bind(item: StorageReference) {
        super.bind(item)
        item.downloadUrl.addOnSuccessListener { Uri->
            val imageURL = Uri.toString()
            Glide.with(container.context)
                .load(imageURL)
                .into(container.thumbnail)
        }
    }
}