package com.kulloveth.moviesapp.favorites

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kulloveth.moviesapp.R
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
     * the need to call notifyDataSetChanged
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
            val context = itemView.context
            val imageResource = context.resources.getIdentifier(
                movie.image.toString(),
                null,
                context.packageName
            )
            movieListItemsBinding.moviePoster.setImageResource(imageResource)
            movieListItemsBinding.title.text = movie.title
            setBackgroundColors(context, imageResource)
        }
        /**
         * creating a background color same as the image
         * for its sourrounding using
         * the [imageResource]
         * */
        private fun setBackgroundColors(context: Context, imageResource: Int) {
            val image = BitmapFactory.decodeResource(context.resources, imageResource)
            Palette.from(image).generate { palette ->
                val backgroundColor =
                    palette?.getDominantColor(
                        ContextCompat.getColor(
                            context,
                            R.color.primaryDarkColor
                        )
                    )
                backgroundColor?.let {
                    movieListItemsBinding.movieCardContainer.setBackgroundColor(it)
                }
                val textColor =
                    if (backgroundColor?.let { isColorDark(it) }!!) Color.WHITE else Color.BLACK
                movieListItemsBinding.title.setTextColor(textColor)

            }
        }


        fun isColorDark(color: Int): Boolean {
            val darkness =
                1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(
                    color
                )) / 255
            return darkness >= 0.5
        }

    }


    interface MovieItemCLickedListener {
        fun movieItemCLicked(movie: Movie)
    }

}