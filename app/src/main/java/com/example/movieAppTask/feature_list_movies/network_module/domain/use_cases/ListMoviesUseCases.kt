package com.example.movieAppTask.feature_list_movies.network_module.domain.use_cases

data class ListMoviesUseCases(
    val getCategoriesUseCase: GetCategoriesUseCase,
    val getMoviesListUseCase: GetMoviesListUseCase
)