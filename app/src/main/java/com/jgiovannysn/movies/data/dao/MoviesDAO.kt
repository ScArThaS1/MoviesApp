package com.jgiovannysn.movies.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.jgiovannysn.movies.data.entities.Movie
import io.reactivex.Single

@Dao
interface MoviesDAO : BaseDao<Movie> {
    @Query("SELECT * FROM MOVIES")
    fun getAll(): Single<List<Movie>>
}