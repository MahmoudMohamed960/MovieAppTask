package com.example.movieAppTask.feature_list_movies.database_module.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieAppTask.feature_list_movies.network_module.data.model.MoviesResult
@Entity(tableName = "movies")
data class MovieModel(
    val movie: Map<String, ArrayList<MoviesResult>>?,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)