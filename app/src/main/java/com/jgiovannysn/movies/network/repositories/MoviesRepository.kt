package com.jgiovannysn.movies.network.repositories

import com.jgiovannysn.movies.data.entities.Movies
import com.jgiovannysn.movies.network.services.MoviesService
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * @author JGiovannySN 05/01/22.
 * @version 1.0
 * @since 1.0
 */
class MoviesRepository @Inject constructor(
    private val moviesService: MoviesService
) {

    /**
     * Retrieve the movies from the api service
     */
    fun getDiscoverMovies(): Flowable<Movies> {
        return moviesService.getDiscoverMovies()
    }
}