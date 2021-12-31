package com.example.movieAppTask

import android.content.Context
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.example.movieAppTask.feature_list_movies.database_module.data.model.MovieModel
import com.example.movieAppTask.feature_list_movies.database_module.data.model.MoviesDB
import com.example.movieAppTask.feature_list_movies.network_module.data.RetrofitFactory
import com.example.movieAppTask.feature_list_movies.network_module.data.model.MovieApiService
import com.example.movieAppTask.feature_list_movies.network_module.data.model.MovieApisImpl
import com.example.movieAppTask.feature_list_movies.network_module.data.model.MoviesResult
import com.example.movieAppTask.feature_list_movies.network_module.data.repository.ListMoviesRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MovieWorkManager(val context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val retrofitFactory = RetrofitFactory(context)
        val apiService = retrofitFactory.createService(MovieApiService::class.java)
        val moviesApisImpl = MovieApisImpl(apiService)
        val listMoviesRepositoryImpl = ListMoviesRepositoryImpl(moviesApisImpl)
        withContext(Dispatchers.IO)
        {
            val moviesMap = mutableMapOf<String, ArrayList<MoviesResult>>()
            val categoriesJob = async { listMoviesRepositoryImpl.getMoviesCategories() }
            val moviesJob = async { listMoviesRepositoryImpl.getMoviesList() }
            val categoriesResponse = categoriesJob.await()
            val moviesResponse = moviesJob.await()
            if (moviesResponse.data != null && categoriesResponse.data != null) {
                for (movie in moviesResponse.data.results) {
                    for (genre in categoriesResponse.data.genres) {
                        val filteredList = movie.genre_ids.filter { id ->
                            id == genre.id
                        }
                        if (!filteredList.isNullOrEmpty()) {
                            if (moviesMap[genre.name] != null) {
                                moviesMap[genre.name]!!.add(movie)
                            } else {
                                moviesMap[genre.name] = arrayListOf(movie)
                            }
                        }
                    }
                }
                if (moviesMap.isNotEmpty()) {
                    //setData To DB
                    val movieDb = Room.databaseBuilder(
                        context,
                        MoviesDB::class.java,
                        MoviesDB.DATABASE_NAME
                    ).build()
                    movieDb.movieDao.insertMovies(MovieModel(moviesMap))
                    var data = Data.Builder()
                        .putBoolean("Done", true)
                        .build()
                    Result.success(data)
                } else {

                    Result.retry()
                }
            } else {
                var data = Data.Builder()
                    .putBoolean("Done", false)
                    .build()
                Result.failure(data)
            }
        }

        return Result.success()
    }
}