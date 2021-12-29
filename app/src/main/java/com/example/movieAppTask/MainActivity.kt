package com.example.movieAppTask

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.movieAppTask.databinding.ActivityMainBinding
import com.example.movieAppTask.feature_list_movies.network_module.data.model.MoviesResult
import com.example.movieAppTask.feature_list_movies.presentation.ListMoviesViewModel
import com.example.movieAppTask.util.Response
import com.example.movieAppTask.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var mainActivityBinding: ActivityMainBinding
    private val viewModel by viewModels<ListMoviesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)


        viewModel.moviesListRes.observe(this, Observer {
            showData(it)
        })
    }

    private fun showData(response: Response<MutableMap<String, ArrayList<MoviesResult>>?>) {
        when (response.status) {
            Status.SUCCESS -> {
                Toast.makeText(this, "size = ${response.data?.size}", Toast.LENGTH_LONG)
                    .show()
            }

            Status.LOADING -> {
                Toast.makeText(this, "loading", Toast.LENGTH_LONG).show()
            }

            Status.EmptyState -> {
                Toast.makeText(this, "Empty State", Toast.LENGTH_LONG).show()
            }

            Status.ERROR -> {
                //can`t fetch data from network  try to fetch locally data
                Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
            }
        }
    }


}