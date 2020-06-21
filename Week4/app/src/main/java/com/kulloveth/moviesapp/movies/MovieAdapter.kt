package com.kulloveth.moviesapp.movies

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
import androidx.viewbinding.ViewBinding
import com.kulloveth.moviesapp.R
import com.kulloveth.moviesapp.databinding.HeaderLayoutBinding
import com.kulloveth.moviesapp.databinding.MovieListItemsBinding
import com.kulloveth.moviesapp.models.CompositeItem
import com.kulloveth.moviesapp.models.Movie


class MovieAdapter(val movieItemCLickedListener: MovieItemCLickedListener) :
    ListAdapter<CompositeItem, MovieAdapter.ViewHolder>(MovieDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val movieBinding =
            MovieListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val headBinding =
            HeaderLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return when (viewType) {
            ViewType.HEADER.ordinal -> ViewHolder(headBinding)
            ViewType.MOVIE.ordinal -> ViewHolder(movieBinding)
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        //setup click listener passing the movie through an interface
        holder.itemView.setOnClickListener {
            getItem(position).movie?.let { it1 -> movieItemCLickedListener.movieItemCLicked(it1) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isHeader) {
            ViewType.HEADER.ordinal
        } else {
            ViewType.MOVIE.ordinal
        }
    }

    /**
     * this a diffutil class which uses your Object type
     * [CompositeItem] in this class to automatically
     * manage changes in you adapter without
     * the need to call notifyDataItemChanged
     * from every method updating the adapter
     * */
    class MovieDiffCallBack : DiffUtil.ItemCallback<CompositeItem>() {
        override fun areItemsTheSame(oldItem: CompositeItem, newItem: CompositeItem): Boolean {
            return oldItem.movie?.id == newItem.movie?.id
        }

        override fun areContentsTheSame(oldItem: CompositeItem, newItem: CompositeItem): Boolean {
            return oldItem.movie == newItem.movie
        }

    }


    /**
     * A class responsible for creating and managing the view items
     * @param binding helps with easy reference to the views
     * eliminating findViewById
     * */
    class ViewHolder(var binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {


        /**
         * a method to bind the data to the
         * viewholder using the [compositeItem] class created for the purpose
         * */
        fun bind(compositeItem: CompositeItem) {
            if (compositeItem.isHeader) {
                val headerLayoutBinding: HeaderLayoutBinding = binding as HeaderLayoutBinding
                headerLayoutBinding.title.text = compositeItem.header?.name
            } else {
                val movieBinding: MovieListItemsBinding = binding as MovieListItemsBinding
                val context = itemView.context
                val imageResource = context.resources.getIdentifier(
                    compositeItem.movie?.image.toString(),
                    null,
                    context.packageName
                )
                movieBinding.moviePoster.setImageResource(imageResource)
                movieBinding.title.text = compositeItem.movie?.title
                setBackgroundColors(context, imageResource)
            }
        }

        /**
         * creating a background color same as the image
         * for its sourrounding using
         * the [imageResource]
         * */
        private fun setBackgroundColors(context: Context, imageResource: Int) {
            val movieBinding: MovieListItemsBinding = binding as MovieListItemsBinding
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
                    movieBinding.movieCardContainer.setBackgroundColor(it)
                }
                val textColor =
                    if (backgroundColor?.let { isColorDark(it) }!!) Color.WHITE else Color.BLACK
                movieBinding.title.setTextColor(textColor)

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


    /**
     * a special class to differentiate between the header and the items
     * */
    enum class ViewType {
        HEADER, MOVIE
    }

    //Onclicklistener for the movie items
    interface MovieItemCLickedListener {
        fun movieItemCLicked(movie: Movie)
    }

}