package com.jgiovannysn.movies

import android.app.Application
import com.jgiovannysn.movies.di.AppComponent
import com.jgiovannysn.movies.di.DaggerAppComponent
import com.jgiovannysn.movies.di.modules.ApplicationModule
import com.jgiovannysn.movies.di.modules.DatabaseModule
import com.jgiovannysn.movies.di.modules.NetworkModule

/**
 * @author JGiovannySN 06/01/22.
 * @version 1.0
 * @since 1.0
 */
class MoviesApplication: Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = initDaggerComponent()
    }

    private fun initDaggerComponent(): AppComponent {
        appComponent = DaggerAppComponent
            .builder()
            .networkModule(NetworkModule())
            .databaseModule(DatabaseModule(this))
            .applicationModule(ApplicationModule(this))
            .build()
        return  appComponent
    }
}