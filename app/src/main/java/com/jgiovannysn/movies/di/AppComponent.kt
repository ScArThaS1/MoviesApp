package com.jgiovannysn.movies.di

import android.app.Application
import android.content.Context
import com.jgiovannysn.movies.MoviesApplication
import com.jgiovannysn.movies.di.annotations.ApplicationContext
import com.jgiovannysn.movies.di.annotations.DatabaseInfo
import com.jgiovannysn.movies.di.modules.*
import com.jgiovannysn.movies.network.services.MoviesService
import com.jgiovannysn.movies.ui.main.MainActivity
import com.jgiovannysn.movies.ui.main.MainViewModel
import com.jgiovannysn.movies.ui.main.fragments.MapsFragment
import com.jgiovannysn.movies.ui.main.fragments.MoviesFragment
import dagger.Component
import javax.inject.Singleton

/**
 * @author JGiovannySN 05/01/22.
 * @version 1.0
 * @since 1.0
 * The "modules" attribute in the @Component annotation tells Dagger what Modules to include when building the graph
 */
@Singleton
@Component(modules = [NetworkModule::class, ServicesModule::class, ViewModelModule::class, DatabaseModule::class, ApplicationModule::class])
interface AppComponent {
    /**
     * Injects.
     */
    fun inject(application: MoviesApplication)
    fun inject(viewModel: MainViewModel)
    fun inject(activity: MainActivity)
    fun inject(fragment: MoviesFragment)
    fun inject(fragment: MapsFragment)

    @ApplicationContext
    fun getContext(): Context

    fun getApplication(): Application

    @DatabaseInfo
    fun getDatabaseName(): String

    /**
     * Provides injection for an implementation of the [MoviesService] interface.
     */
    fun provideMoviesService(): MoviesService
}