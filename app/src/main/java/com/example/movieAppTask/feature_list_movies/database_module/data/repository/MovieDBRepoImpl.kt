package com.example.movieAppTask.feature_list_movies.database_module.data.repository

import com.example.movieAppTask.feature_list_movies.database_module.data.model.MovieDao
import com.example.movieAppTask.feature_list_movies.database_module.data.model.MovieModel
import com.example.movieAppTask.feature_list_movies.database_module.domain.repository.MoviesDBRepository
import javax.inject.Inject

class MovieDBRepoImpl @Inject constructor(val dao:MovieDao) :MoviesDBRepository {
    override suspend fun insertMovies(movie: MovieModel) {
        dao.insertMovies(movie)
    }

    override suspend fun getMovies(): MovieModel {
        return dao.getMovies()
    }
}