package com.jgiovannysn.movies.interfaces

/**
 * @author JGiovannySN 05/01/22.
 * @version 1.0
 * @since 1.0
 */
interface OnViewHolderClick<in T: Any> {
    fun onItemClick(item: T, position: Int)
}

interface Binder<in T: Any> {
    fun bind(item: T)
}