package com.jgiovannysn.movies.data

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * @author JGiovannySN 05/01/22.
 * @version 1.0
 * @since 1.0
 * Thread executor for CRUD database
 */
object Executor {
    fun thread(t: Runnable?) {
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute(t)
    }
}