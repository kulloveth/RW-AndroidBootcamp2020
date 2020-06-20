package com.kulloveth.moviesapp.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kulloveth.moviesapp.databinding.MovieListItemsBinding
import com.kulloveth.moviesapp.models.Movie

class FavoriteAdapter(val movieItemCLickedListener: MovieItemCLickedListener) :
    ListAdapter<Movie, FavoriteAdapter.ViewHolder>(MovieDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val movieBinding =
            MovieListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(movieBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        //setup click listener passing the movie through an interface
        holder.itemView.setOnClickListener {
            movieItemCLickedListener.movieItemCLicked(getItem(position))
        }
    }


    /**
     * this a diffutil class which uses your Object type
     * [Movie] in this class to automatically
     * manage changes in you adapter without
     * the need to call notifyDataItemChanged
     * from every method updating the adapter
     * */
    class MovieDiffCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }


    /**
     * A class responsible for creating and managing the view items
     * @param movieListItemsBinding helps with easy reference to the views
     * eliminating findViewById
     * */
    class ViewHolder(var movieListItemsBinding: MovieListItemsBinding) :
        RecyclerView.ViewHolder(movieListItemsBinding.root) {


        /**
         * a method to bind the data to the
         * viewholder using the [movie] class created for the purpose
         * */
        fun bind(movie: Movie) {
            movieListItemsBinding.title.text = movie.title
            movieListItemsBinding.moviePoster.setImageResource(movie.image)
        }

    }


    interface MovieItemCLickedListener {
        fun movieItemCLicked(movie: Movie)
    }

}