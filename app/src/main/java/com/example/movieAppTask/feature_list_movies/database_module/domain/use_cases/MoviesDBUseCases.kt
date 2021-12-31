package com.example.movieAppTask.feature_list_movies.database_module.domain.use_cases

data class MoviesDBUseCases(
    val insertMovie: InsertMovie,
    val fetchMovies: FetchMovies
)
