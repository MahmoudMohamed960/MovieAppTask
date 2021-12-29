package com.example.movieAppTask.feature_list_movies.network_module.data.model

import retrofit2.http.GET

interface MovieApiService {
    @GET("genre/movie/list")
    suspend fun getMoviesCategories(): CategoriesResponse

    @GET("discover/movie")
    suspend fun getMoviesList(): MoviesResponse

}