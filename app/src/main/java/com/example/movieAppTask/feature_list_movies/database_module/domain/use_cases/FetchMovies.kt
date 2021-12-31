package com.example.movieAppTask.feature_list_movies.database_module.domain.use_cases

import com.example.movieAppTask.feature_list_movies.database_module.domain.repository.MoviesDBRepository
import javax.inject.Inject

class FetchMovies @Inject constructor(val moviesDBRepository: MoviesDBRepository) {
    suspend operator fun invoke() = moviesDBRepository.getMovies()
}