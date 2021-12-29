package com.example.movieAppTask.feature_list_movies.network_module.data.repository

import com.example.movieAppTask.feature_list_movies.network_module.data.model.CategoriesResponse
import com.example.movieAppTask.feature_list_movies.network_module.data.model.MovieApiHelper
import com.example.movieAppTask.feature_list_movies.network_module.data.model.MoviesResponse
import com.example.movieAppTask.feature_list_movies.network_module.domain.repository.ListMoviesRepository
import com.example.movieAppTask.util.Response
import javax.inject.Inject

class ListMoviesRepositoryImpl @Inject constructor(private val movieApiHelper: MovieApiHelper) :
    ListMoviesRepository {
    override suspend fun getMoviesCategories():CategoriesResponse =
        movieApiHelper.getMoviesGeneries()

    override suspend fun getMoviesList(): MoviesResponse =
        movieApiHelper.getMoviesList()

}