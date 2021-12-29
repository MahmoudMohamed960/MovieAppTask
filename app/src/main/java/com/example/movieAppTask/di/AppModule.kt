package com.example.movieAppTask.di

import com.example.movieAppTask.BuildConfig
import com.example.movieAppTask.feature_list_movies.network_module.data.model.MovieApiHelper
import com.example.movieAppTask.feature_list_movies.network_module.data.model.MovieApiService
import com.example.movieAppTask.feature_list_movies.network_module.data.model.MovieApisImpl
import com.example.movieAppTask.feature_list_movies.network_module.data.repository.ListMoviesRepositoryImpl
import com.example.movieAppTask.feature_list_movies.network_module.domain.repository.ListMoviesRepository
import com.example.movieAppTask.feature_list_movies.network_module.domain.use_cases.GetCategoriesUseCase
import com.example.movieAppTask.feature_list_movies.network_module.domain.use_cases.GetMoviesListUseCase
import com.example.movieAppTask.feature_list_movies.network_module.domain.use_cases.ListMoviesUseCases
import com.example.movieAppTask.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() =
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val api_interceptor = Interceptor {
                val originalRequest = it.request()
                val newHttpUrl = originalRequest.url.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.MOVIE_API_KEY)
                    .build()
                val newRequest = originalRequest.newBuilder()
                    .url(newHttpUrl)
                    .build()
                it.proceed(newRequest)
            }
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(api_interceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build()
        } else {
            OkHttpClient
                .Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build()
        }


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()


    @Provides
    @Singleton
    fun provideMovieApisService(retrofit: Retrofit): MovieApiService =
        retrofit.create(MovieApiService::class.java)

    @Provides
    @Singleton
    fun provideMovieApisHelper(movieApiService: MovieApiService): MovieApiHelper =
        MovieApisImpl(movieApiService)

    @Provides
    @Singleton
    fun provideListMoviesRepository(movieApiHelper: MovieApiHelper): ListMoviesRepository =
        ListMoviesRepositoryImpl(movieApiHelper)

    @Provides
    @Singleton
    fun provideListMoviesUseCases(listMoviesRepository: ListMoviesRepository): ListMoviesUseCases =
        ListMoviesUseCases(GetCategoriesUseCase(listMoviesRepository), GetMoviesListUseCase(listMoviesRepository))
}