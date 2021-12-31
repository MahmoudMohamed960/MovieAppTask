package com.example.movieAppTask.feature_list_movies.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movieAppTask.R
import com.example.movieAppTask.databinding.FragmentDetailsBinding
import com.example.movieAppTask.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    lateinit var fragmentDetailsBinding: FragmentDetailsBinding
    lateinit var viewModel: ListMoviesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentDetailsBinding = FragmentDetailsBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity())[ListMoviesViewModel::class.java]
        getMovieDetails()
    }

    private fun getMovieDetails() {
        val movieResult = viewModel.moviesResult
        if (movieResult != null) {
            Glide.with(this).load(Constants.POSTER_URL + movieResult.poster_path)
                .placeholder(R.drawable.ic_local_movies)
                .into(fragmentDetailsBinding.moviePosterImage)
            fragmentDetailsBinding.movieTitle.text = movieResult.title
            fragmentDetailsBinding.releaseDate.text = movieResult.release_date
            fragmentDetailsBinding.movieDetails.text = movieResult.overview


        }
    }
}