package com.example.movieAppTask.feature_list_movies.database_module.data.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MovieModel::class], version = 1)
@TypeConverters(Converters::class)

abstract class MoviesDB: RoomDatabase() {
    abstract val movieDao: MovieDao

    companion object {
        const val DATABASE_NAME = "Movies_DB"
    }
}