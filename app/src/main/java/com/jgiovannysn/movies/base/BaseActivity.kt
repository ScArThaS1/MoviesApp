package com.jgiovannysn.movies.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author JGiovannySN 05/01/22.
 * @version 1.0
 * @since 1.0
 */
abstract class BaseActivity: AppCompatActivity() {

    /**
     * Function that runs when the view is created
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutId())

        intent?.let {
            it.extras?.let { extras ->
                onRestoreExtras(extras)
            }
        }

        init()
        initViewModel()
        listeners()
        observers()
    }

    /**
     * Gets layout resource id
     */
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
}