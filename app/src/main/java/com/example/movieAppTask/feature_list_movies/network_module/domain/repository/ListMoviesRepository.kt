package com.example.movieAppTask.feature_list_movies.network_module.domain.repository

import com.example.movieAppTask.feature_list_movies.network_module.data.model.CategoriesResponse
import com.example.movieAppTask.feature_list_movies.network_module.data.model.MoviesResponse
import com.example.movieAppTask.util.Response

interface ListMoviesRepository {
    suspend fun getMoviesCategories():Response<CategoriesResponse>
    suspend fun getMoviesList():Response<MoviesResponse>
}