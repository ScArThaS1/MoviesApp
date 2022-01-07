package com.jgiovannysn.movies.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jgiovannysn.movies.data.dao.MoviesDAO
import com.jgiovannysn.movies.data.entities.Movie

/**
 * @author JGiovannySN 05/01/22.
 * @version 1.0
 * @since 1.0
 */
@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDAO
}