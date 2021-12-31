package com.example.movieAppTask.feature_list_movies.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieAppTask.R
import com.example.movieAppTask.databinding.MoviesItemBinding
import com.example.movieAppTask.feature_list_movies.network_module.data.model.MoviesResult
import com.example.movieAppTask.util.Constants

class MoviesAdapter(
    private val moviesList: ArrayList<MoviesResult>,
    private val context: Context,
    private val onMovieItemClick: OnMovieItemClick
) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            MoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setMovieData(moviesList[position], context)
        holder.itemView.setOnClickListener {
            onMovieItemClick.sendMovieDetails(moviesList[position])
        }
    }

    override fun getItemCount() = moviesList.size


    class ViewHolder(private val itemBinding: MoviesItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun setMovieData(movie: MoviesResult, context: Context) {
            itemBinding.movieTitle.text = movie.title
            itemBinding.releaseDate.text = movie.release_date
            Glide.with(context).load(Constants.POSTER_URL + movie.poster_path)
                .placeholder(R.drawable.ic_local_movies).into(itemBinding.moviePoster)
        }
    }

    interface OnMovieItemClick {
        fun sendMovieDetails(moviesResult: MoviesResult)
    }
}