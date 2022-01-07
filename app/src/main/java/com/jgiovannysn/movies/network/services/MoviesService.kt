package com.jgiovannysn.movies.network.services

import com.jgiovannysn.movies.data.entities.Movies
import io.reactivex.Flowable
import retrofit2.http.GET

/**
 * @author JGiovannySN 05/01/22.
 * @version 1.0
 * @since 1.0
 */
interface MoviesService {
    /**
     * Get movies url
     */
    @GET("discover/movie")
    fun getDiscoverMovies(): Flowable<Movies>
}