package com.example.movieAppTask.feature_list_movies.network_module.data.model



interface MovieApiHelper {
    suspend fun getMoviesGeneries(): CategoriesResponse
    suspend fun getMoviesList(): MoviesResponse
}