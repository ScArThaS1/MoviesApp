package com.jgiovannysn.movies.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * @author JGiovannySN 05/01/22.
 * @version 1.0
 * @since 1.0
 */
abstract class BaseFragment: Fragment() {

    /**
     * Initializes the fragment and sets layout resource id
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initViewModel()
        listeners()
        observers()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            onRestoreExtras(it)
        }
    }

    /**
     * Gets layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Initialize components like adapters, maps and more
     */
    abstract fun init()

    /**
     * Initializes view model
     */
    abstract fun initViewModel()

    /**
     * Setup listeners like click buttons and others events
     */
    abstract fun listeners()

    /**
     * Attaches view model [android.arch.lifecycle.LiveData] observers
     */
    abstract fun observers()

    /**
     * Function optional that receive extras from other activity
     */
    open fun onRestoreExtras(extras: Bundle) {}
    open fun onRestoreSaveInstance(outState: Bundle) {}
}