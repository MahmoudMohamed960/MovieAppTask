package com.example.movieAppTask.feature_list_movies.database_module.data.model

import androidx.room.TypeConverter
import com.example.movieAppTask.feature_list_movies.network_module.data.model.MoviesResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromStringToMap(value: String): Map<String, ArrayList<MoviesResult>> {
        val mapType = object : TypeToken<Map<String, ArrayList<MoviesResult>>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromMapToString(map: Map<String, ArrayList<MoviesResult>>): String {
        val gson = Gson()
        return gson.toJson(map)
    }
}