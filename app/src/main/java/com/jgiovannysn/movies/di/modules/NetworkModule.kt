package com.jgiovannysn.movies.di.modules

import com.jgiovannysn.movies.BuildConfig
import com.jgiovannysn.movies.MoviesApplication
import com.jgiovannysn.movies.network.repositories.MoviesRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author JGiovannySN 05/01/22.
 * @version 1.0
 * @since 1.0
 */
@Module
class NetworkModule {

    /**
     * Provides injection for an implementation of the [OkHttpClient] for Use Retrofit.
     */
    @Singleton
    @Provides
    fun provideOKHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        client.addInterceptor(interceptor)

        client.addInterceptor { chain ->
            val request = chain.request().newBuilder()
            request.addHeader(BuildConfig.AUTHORIZATION, BuildConfig.TOKEN)
            val originalHttpUrl = chain.request().url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter(BuildConfig.API_KEY_TEXT, BuildConfig.API_KEY).build()
            request.url(url)
            return@addInterceptor chain.proceed(request.build())
        }
        return client.build()
    }

    /**
     * Provides injection for an implementation of the [GsonConverterFactory] for Use Retrofit.
     */
    @Singleton
    @Provides
    fun provideGSON(): GsonConverterFactory {
        return  GsonConverterFactory.create()
    }

    /**
     * Provides injection for an implementation of the [Retrofit].
     */
    @Singleton
    @Provides
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory,okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    /**
     * Provides injection for an implementation of the [MoviesRepository].
     */
    @Provides
    fun provideMoviesRepository(): MoviesRepository {
        return MoviesRepository(MoviesApplication.appComponent.provideMoviesService())
    }
}