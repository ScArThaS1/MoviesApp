package com.jgiovannysn.movies.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * @author JGiovannySN 05/01/22.
 * @version 1.0
 * @since 1.0
 */
abstract class BaseAdapter<T: Any>: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Contain the value array of items
     */
    var data: MutableList<T> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    /**
     * Gets size of dataset. If data is empty getItemCount returns 0.
     */
    override fun getItemCount(): Int = data.size

    /**
     * Gets view type based on his layout id
     */
    override fun getItemViewType(position: Int): Int = getLayoutId(position, data[position])

    /**
     * Creates new views for adapter
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = getViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false), viewType)

    /**
     * Replaces the contents of a view
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BaseViewHolder<T>).bind(data[position])
    }

    /**
     * Gets resource layout id for inflates the item layout
     */
    @LayoutRes
    protected abstract fun getLayoutId(position: Int, item: T): Int

    /**
     * Gets [RecyclerView.ViewHolder] handles to individual view resources
     */
    protected abstract fun getViewHolder(container: View, viewType: Int): RecyclerView.ViewHolder

    /**
     * Adds a collection of [T] items to dataset
     * @param items [T] items collection
     */
    fun addAll(items: Collection<T>) {
        val previewPosition = data.size
        data.addAll(items)
        notifyItemRangeInserted(previewPosition, items.size)
    }

    /**
     * Remove the data and notify the change
     */
    fun removeAll() {
        data.clear()
        notifyDataSetChanged()
    }
}