package com.example.movieAppTask.feature_list_movies.presentation


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.movieAppTask.MovieWorkManager
import com.example.movieAppTask.feature_list_movies.database_module.data.model.MovieModel
import com.example.movieAppTask.feature_list_movies.database_module.domain.use_cases.MoviesDBUseCases
import com.example.movieAppTask.feature_list_movies.network_module.data.model.MoviesResult
import com.example.movieAppTask.feature_list_movies.network_module.domain.use_cases.ListMoviesUseCases
import com.example.movieAppTask.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class ListMoviesViewModel @Inject constructor(
    private val listMoviesUseCases: ListMoviesUseCases,
    private val moviesDBUseCases: MoviesDBUseCases,
    private val context: Context
) :
    ViewModel() {

    private val _moviesListRes =
        MutableLiveData<Response<MutableMap<String, ArrayList<MoviesResult>>?>>()
    val moviesListRes: LiveData<Response<MutableMap<String, ArrayList<MoviesResult>>?>>
        get() = _moviesListRes

    var moviesResult: MoviesResult? = null

    init {
        _moviesListRes.value = Response.loading()
        getMoviesList()
    }


    private fun getMoviesList() =
        viewModelScope.launch() {
            val moviesMap = mutableMapOf<String, ArrayList<MoviesResult>>()
            val categoriesJob = async { listMoviesUseCases.getCategoriesUseCase.invoke() }
            val moviesJob = async { listMoviesUseCases.getMoviesListUseCase.invoke() }
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
                    moviesDBUseCases.insertMovie.invoke(MovieModel(moviesMap))
                    _moviesListRes.value = Response.success(moviesMap)
                    initWorkManger()
                } else
                    _moviesListRes.value = Response.emptyData()
            } else {
                _moviesListRes.value = moviesResponse.message?.let { Response.error(it) }
                _moviesListRes.value = categoriesResponse.message?.let { Response.error(it) }
                getMoviesFromDB()
            }
        }

     fun initWorkManger(): WorkRequest{
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest =
            PeriodicWorkRequest.Builder(MovieWorkManager::class.java, 4, TimeUnit.HOURS)
                .setConstraints(constraint)
                .build()

        val workManager = WorkManager.getInstance(context)
        workManager.enqueueUniquePeriodicWork(
            "FetchMovieData",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )

        return workRequest
    }


    private fun getMoviesFromDB() = viewModelScope.launch {
        try {
            val moviesModel = moviesDBUseCases.fetchMovies.invoke()
            _moviesListRes.value = Response.success(moviesModel.movie?.toMutableMap())
        } catch (ex: Exception) {
            _moviesListRes.value = Response.error("Error occured !!")
        }
    }


}