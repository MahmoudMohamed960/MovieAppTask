package com.example.movieAppTask.feature_list_movies.database_module.domain.repository


import com.example.movieAppTask.feature_list_movies.database_module.data.model.MovieModel

interface MoviesDBRepository {
    suspend fun insertMovies(movie: MovieModel)

    suspend fun getMovies(): MovieModel
}