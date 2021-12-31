package com.example.movieAppTask

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.movieAppTask.databinding.ActivityMainBinding
import com.example.movieAppTask.feature_list_movies.network_module.data.model.MoviesResult
import com.example.movieAppTask.feature_list_movies.presentation.ListMoviesViewModel
import com.example.movieAppTask.util.Response
import com.example.movieAppTask.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var mainActivityBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)


    }



}