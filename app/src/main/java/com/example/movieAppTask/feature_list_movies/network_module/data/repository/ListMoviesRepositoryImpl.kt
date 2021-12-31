package com.example.movieAppTask.feature_list_movies.network_module.data.repository

import com.example.movieAppTask.feature_list_movies.network_module.data.model.CategoriesResponse
import com.example.movieAppTask.feature_list_movies.network_module.data.model.MovieApiHelper
import com.example.movieAppTask.feature_list_movies.network_module.data.model.MoviesResponse
import com.example.movieAppTask.feature_list_movies.network_module.domain.repository.ListMoviesRepository
import com.example.movieAppTask.feature_list_movies.network_module.data.NoConnectivityException
import com.example.movieAppTask.util.Response
import java.net.SocketTimeoutException
import javax.inject.Inject

class ListMoviesRepositoryImpl @Inject constructor(private val movieApiHelper: MovieApiHelper) :
    ListMoviesRepository {
    override suspend fun getMoviesCategories(): Response<CategoriesResponse> = try {
        Response.success(movieApiHelper.getMoviesGeneries())
    } catch (ex: Exception) {
        when (ex) {
            is SocketTimeoutException -> {
                Response.error("Time out !!")
            }
            is NoConnectivityException -> {
                Response.error("No InternetConnection")
            }
            else -> {
                Response.error("Error occured !!")
            }
        }
    }

    override suspend fun getMoviesList(): Response<MoviesResponse> = try {
        Response.success(movieApiHelper.getMoviesList())
    } catch (ex: Exception) {
        when (ex) {
            is SocketTimeoutException -> {
                Response.error("Time out !!")
            }
            is NoConnectivityException -> {
                Response.error("No InternetConnection")
            }
            else -> {
                Response.error("Error occured !!")
            }
        }

    }

}