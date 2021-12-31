package com.example.movieAppTask.feature_list_movies.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.work.WorkManager
import com.example.movieAppTask.R
import com.example.movieAppTask.databinding.FragmentHomeBinding
import com.example.movieAppTask.feature_list_movies.network_module.data.model.MoviesResult
import com.example.movieAppTask.feature_list_movies.presentation.adapter.CategoriesAdapter
import com.example.movieAppTask.feature_list_movies.presentation.adapter.MoviesAdapter
import com.example.movieAppTask.util.Response
import com.example.movieAppTask.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), MoviesAdapter.OnMovieItemClick {
    lateinit var homeBinding: FragmentHomeBinding
    lateinit var viewModel: ListMoviesViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeBinding = FragmentHomeBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity())[ListMoviesViewModel::class.java]
        viewModel.moviesListRes.observe(viewLifecycleOwner, Observer {
            showData(it)
        })
    }

    private fun showData(response: Response<MutableMap<String, ArrayList<MoviesResult>>?>) {
        when (response.status) {
            Status.SUCCESS -> {
                val categoriesList = response.data?.keys?.toList()
                val moviesList = response.data?.values?.toList()
                categoriesList?.let { categories ->
                    moviesList?.let { movies ->
                        homeBinding.moviesList.adapter =
                            CategoriesAdapter(categories, movies, requireContext(), this)
                        homeBinding.loadingBar.visibility = View.GONE
                        homeBinding.moviesList.visibility = View.VISIBLE
                    }
                }
            }

            Status.LOADING -> {
                homeBinding.loadingBar.visibility = View.VISIBLE
            }

            Status.EmptyState -> {
                homeBinding.loadingBar.visibility = View.GONE
                homeBinding.emptyState.visibility = View.VISIBLE
            }

            Status.ERROR -> {
                Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun sendMovieDetails(moviesResult: MoviesResult) {
        viewModel.moviesResult = moviesResult
        findNavController().navigate(R.id.action_home_fragment_to_details_fragment)
    }

}