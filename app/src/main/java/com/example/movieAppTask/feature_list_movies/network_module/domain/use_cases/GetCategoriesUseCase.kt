package com.example.movieAppTask.feature_list_movies.network_module.domain.use_cases

import com.example.movieAppTask.feature_list_movies.network_module.data.model.CategoriesResponse
import com.example.movieAppTask.feature_list_movies.network_module.domain.repository.ListMoviesRepository
import com.example.movieAppTask.util.Response
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val listMoviesRepository: ListMoviesRepository) {
    suspend operator fun invoke(): CategoriesResponse {
        return listMoviesRepository.getMoviesCategories()
    }
}