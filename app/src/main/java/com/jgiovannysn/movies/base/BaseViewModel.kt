package com.jgiovannysn.movies.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author JGiovannySN 06/01/22.
 * @version 1.0
 * @since 1.0
 */
abstract class ViewModelBase : ViewModel(), LifecycleObserver {
    private val subscriptions = CompositeDisposable()

    fun subscribe(disposable: Disposable): Disposable {
        subscriptions.add(disposable)
        return disposable
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun dispose() {
        subscriptions.clear()
    }

    override fun onCleared() {
        dispose()
        super.onCleared()
    }
}