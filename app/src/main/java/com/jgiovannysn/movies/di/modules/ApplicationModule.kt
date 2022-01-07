package com.jgiovannysn.movies.di.modules

import android.app.Application
import android.content.Context
import com.jgiovannysn.movies.di.annotations.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author JGiovannySN 05/01/22.
 * @version 1.0
 * @since 1.0
 */
@Module
class ApplicationModule(app: Application) {
    private val mApplication: Application = app

    /**
     * Provides injection for an implementation of the [Context].
     */
    @Singleton
    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return mApplication
    }

    /**
     * Provides injection for an implementation of the [Application].
     */
    @Singleton
    @Provides
    fun provideApplication(): Application {
        return mApplication
    }

}