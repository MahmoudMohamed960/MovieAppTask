package com.example.movieAppTask.feature_list_movies.network_module.data.model

import javax.inject.Inject

class MovieApisImpl @Inject constructor(
    private val apiService: MovieApiService
) : MovieApiHelper {
    override suspend fun getMoviesGeneries(): CategoriesResponse =
        apiService.getMoviesCategories()

    override suspend fun getMoviesList(): MoviesResponse =
        apiService.getMoviesList()

}