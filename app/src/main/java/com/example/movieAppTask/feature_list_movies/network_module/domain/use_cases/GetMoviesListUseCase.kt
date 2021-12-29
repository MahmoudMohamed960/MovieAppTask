package com.example.movieAppTask.feature_list_movies.network_module.domain.use_cases

import com.example.movieAppTask.feature_list_movies.network_module.data.model.MoviesResponse
import com.example.movieAppTask.feature_list_movies.network_module.domain.repository.ListMoviesRepository
import javax.inject.Inject

class GetMoviesListUseCase @Inject constructor(private val listMoviesRepository: ListMoviesRepository) {
    suspend operator fun invoke(): MoviesResponse {
        return listMoviesRepository.getMoviesList()
    }
}