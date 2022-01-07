package com.jgiovannysn.movies.di.modules

import com.jgiovannysn.movies.network.services.MoviesService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * @author JGiovannySN 05/01/22.
 * @version 1.0
 * @since 1.0
 */
@Module
class ServicesModule {

    /**
     * Provides injection for an implementation of the [MoviesService] interface.
     */
    @Provides
    fun provideMoviesService(retrofit: Retrofit): MoviesService {
        return retrofit.create(MoviesService::class.java)
    }
}