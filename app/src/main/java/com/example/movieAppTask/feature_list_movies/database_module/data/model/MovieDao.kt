package com.example.movieAppTask.feature_list_movies.database_module.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movie: MovieModel)

    @Query("SELECT * FROM movies")
    suspend fun getMovies(): MovieModel
}