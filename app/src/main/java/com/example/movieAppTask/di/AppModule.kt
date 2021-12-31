package com.example.movieAppTask.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.movieAppTask.feature_list_movies.database_module.data.model.MoviesDB
import com.example.movieAppTask.feature_list_movies.database_module.data.repository.MovieDBRepoImpl
import com.example.movieAppTask.feature_list_movies.database_module.domain.repository.MoviesDBRepository
import com.example.movieAppTask.feature_list_movies.database_module.domain.use_cases.FetchMovies
import com.example.movieAppTask.feature_list_movies.database_module.domain.use_cases.InsertMovie
import com.example.movieAppTask.feature_list_movies.database_module.domain.use_cases.MoviesDBUseCases
import com.example.movieAppTask.feature_list_movies.network_module.data.RetrofitFactory
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
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    fun provideContext(@ApplicationContext appContext: Context) = appContext

    @Singleton
    @Provides
    fun provideRetrofitFactory(context: Context): RetrofitFactory = RetrofitFactory(context)


    @Provides
    @Singleton
    fun provideMovieApisService(retrofit: RetrofitFactory): MovieApiService =
        retrofit.createService(MovieApiService::class.java)

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
        ListMoviesUseCases(
            GetCategoriesUseCase(listMoviesRepository),
            GetMoviesListUseCase(listMoviesRepository)
        )

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): MoviesDB {
        return Room.databaseBuilder(
            app,
            MoviesDB::class.java,
            MoviesDB.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: MoviesDB): MoviesDBRepository {
        return MovieDBRepoImpl(db.movieDao)
    }

    @Provides
    @Singleton
    fun provideDBMoviesUseCases(moviesDBRepository: MoviesDBRepository): MoviesDBUseCases =
        MoviesDBUseCases(InsertMovie(moviesDBRepository), FetchMovies(moviesDBRepository))

}