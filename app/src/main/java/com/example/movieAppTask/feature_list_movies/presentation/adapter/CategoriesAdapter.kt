package com.example.movieAppTask.feature_list_movies.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieAppTask.databinding.CategoriesItemBinding
import com.example.movieAppTask.feature_list_movies.network_module.data.model.MoviesResult

class CategoriesAdapter(
    private val categoriesList: List<String>,
    private val moviesList: List<ArrayList<MoviesResult>>,
    private val context: Context,
    private val onMovieItemClick: MoviesAdapter.OnMovieItemClick
) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            CategoriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, onMovieItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setCategoriesData(categoriesList[position], moviesList[position], context)
    }

    override fun getItemCount() = categoriesList.size


    class ViewHolder(
        private val itemBinding: CategoriesItemBinding,
        private val onMovieItemClick: MoviesAdapter.OnMovieItemClick
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun setCategoriesData(category: String, movies: ArrayList<MoviesResult>, context: Context) {
            itemBinding.categoryName.text = category
            itemBinding.moviesItems.adapter = MoviesAdapter(movies, context, onMovieItemClick)

        }
    }

}