package com.example.movieAppTask.feature_list_movies.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieAppTask.feature_list_movies.network_module.data.model.MoviesResult
import com.example.movieAppTask.feature_list_movies.network_module.domain.use_cases.ListMoviesUseCases
import com.example.movieAppTask.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class ListMoviesViewModel @Inject constructor(private val listMoviesUseCases: ListMoviesUseCases) :
    ViewModel() {

    private val _moviesListRes =
        MutableLiveData<Response<MutableMap<String, ArrayList<MoviesResult>>?>>()
    val moviesListRes: LiveData<Response<MutableMap<String, ArrayList<MoviesResult>>?>>
        get() = _moviesListRes

    init {
        getMoviesList()
    }

    private fun getMoviesList() =
        viewModelScope.launch {
            val moviesMap = mutableMapOf<String, ArrayList<MoviesResult>>()

            try {
                val categoriesJob = async { listMoviesUseCases.getCategoriesUseCase.invoke() }
                val moviesJob = async { listMoviesUseCases.getMoviesListUseCase.invoke() }
                val categoriesResponse = categoriesJob.await()
                val moviesResponse = moviesJob.await()

                for (movie in moviesResponse.results) {
                    for (genre in categoriesResponse.genres) {
                        val filteredList = movie.genre_ids.filter { id ->
                            id == genre.id
                        }
                        if (!filteredList.isNullOrEmpty()) {
                            if (moviesMap[genre.name] != null) {
                               moviesMap[genre.name]!!.add(movie)
                            }
                            else {
                                moviesMap[genre.name] = arrayListOf(movie)
                            }
                        }
                    }
                }

                if (moviesMap.isNotEmpty())
                    _moviesListRes.value = Response.success(moviesMap)
                else
                    _moviesListRes.value = Response.emptyData()
            } catch (ex: Exception) {
                if (ex is SocketTimeoutException) {
                    _moviesListRes.value = Response.error("Time out !!")
                } else {
                    _moviesListRes.value = Response.error("Error occured !!")
                }
            }
        }


}