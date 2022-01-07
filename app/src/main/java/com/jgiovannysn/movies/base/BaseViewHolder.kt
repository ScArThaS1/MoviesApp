package com.jgiovannysn.movies.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jgiovannysn.movies.interfaces.Binder
import com.jgiovannysn.movies.interfaces.OnViewHolderClick

/**
 * @author JGiovannySN 05/01/22.
 * @version 1.0
 * @since 1.0
 */
abstract class BaseViewHolder<T: Any>(
    open val container: View
): RecyclerView.ViewHolder(container), Binder<T>, View.OnClickListener {

    /**
     * Contain the value of item
     */
    lateinit var item: T

    /**
     * Contain item click listener delegate
     */
    private var itemClickListener: OnViewHolderClick<T>? = null

    /**
     * Sets [OnViewHolderClick] listener to the container
     */
    open fun setItemClickListener(listener: OnViewHolderClick<T>) {
        this.container.setOnClickListener(this)
        this.itemClickListener = listener
    }

    /**
     * Binds the [T] item to the provided view
     */
    override fun bind(item: T) {
        this.item = item
    }

    /**
     * Overrides container [View.OnClickListener]
     */
    override fun onClick(view: View?) {
        itemClickListener?.onItemClick(item, adapterPosition)
    }
}