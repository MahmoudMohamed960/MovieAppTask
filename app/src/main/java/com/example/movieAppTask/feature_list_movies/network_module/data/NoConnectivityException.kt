package com.example.movieAppTask.feature_list_movies.network_module.data

import java.io.IOException


class NoConnectivityException() : IOException() {
   fun errorMsg(): String {
        return "No Internet Connection"
    }
}