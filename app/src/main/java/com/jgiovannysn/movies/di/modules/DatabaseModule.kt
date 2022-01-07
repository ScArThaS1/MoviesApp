package com.jgiovannysn.movies.di.modules

import android.content.Context
import androidx.room.Room
import com.jgiovannysn.movies.data.AppDatabase
import com.jgiovannysn.movies.data.dao.MoviesDAO
import com.jgiovannysn.movies.di.annotations.ApplicationContext
import com.jgiovannysn.movies.di.annotations.DatabaseInfo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author JGiovannySN 05/01/22.
 * @version 1.0
 * @since 1.0
 */
@Module
class DatabaseModule(@ApplicationContext context: Context) {

    /**
     * Provides injection for an implementation of the [Context] for Use DATABASE.
     */
    @ApplicationContext
    private val mContext: Context = context

    /**
     * Define name DATABASE.
     */
    @DatabaseInfo
    private val mDBName = "core_database.db"

    /**
     * Provides injection for an implementation of the DATABASE.
     */
    @Singleton
    @Provides
    fun provideDatabase(): AppDatabase {
        return Room.databaseBuilder(
            mContext,
            AppDatabase::class.java,
            mDBName
        ).fallbackToDestructiveMigration().build()
    }

    /**
     * Provides injection for an implementation of name DATABASE.
     */
    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String {
        return mDBName
    }

    /**
     * Provides injection for an implementation of the [MoviesDAO] for Use DATABASE.
     */
    @Singleton
    @Provides
    fun provideMoviesDao(db: AppDatabase): MoviesDAO {
        return db.moviesDao()
    }
}