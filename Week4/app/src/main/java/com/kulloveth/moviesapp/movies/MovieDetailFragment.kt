package com.kulloveth.moviesapp.movies

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.kulloveth.moviesapp.MoviesDataManager
import com.kulloveth.moviesapp.R
import com.kulloveth.moviesapp.databinding.FragmentMovieDetailBinding
import com.kulloveth.moviesapp.models.Movie

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailFragment : Fragment() {

    var binding: FragmentMovieDetailBinding? = null
    var moviesDataManager: MoviesDataManager? = null
    var favoriteButton: FloatingActionButton? = null
    var movie: Movie? = null
    var favorite: Boolean? = null
    var mOverview: String? = null
    var mTitle: String? = null
    var mGenre: String? = null
    var mPoster: Int? = null
    var mDate: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        setupFavoriteButton()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        arguments?.let {
            mTitle = it.getString("movie_title")
            mGenre = it.getString("movie_genre")
            mOverview = it.getString("movie_overview")
            mDate = it.getString("movie_date")
            mPoster = it.getInt("movie_poster")
            favorite = it.getBoolean("movie_isFavourite")
        }
        val toolbar = binding?.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar?.setNavigationOnClickListener {
            requireView().findNavController()
                .navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentToMovieList())
        }
        favoriteButton = binding?.favoriteBtn
        moviesDataManager = ViewModelProvider(requireActivity()).get(MoviesDataManager::class.java)
        moviesDataManager?.movieLiveData?.observe(requireActivity(), Observer {
            //favorite = moviesDataManager?.isFavorite(it)
            movie = it
        })
        getMovies()
        //setupFavoriteButton()

    }

    //set movie detail
    private fun getMovies() {


        binding?.apply {
            toolbar.title = mTitle
            title.text = mTitle
            genre.text = mGenre
            overview.text = mOverview
            releaseDate.text = mDate
            mPoster?.let {
                moviePoster.setImageResource(it)
            }

        }

    }

    private fun setupFavoriteButton() {
        movie?.apply {
            setupFavoriteButtonImage()
            setupFavoriteButtonClickListener(this)
        }
    }

    private fun setupFavoriteButtonImage() {
        if (favorite == true) {
            favoriteButton?.setImageDrawable(
                getDrawable(
                    requireActivity(),
                    R.drawable.ic_favorite_black_24dp
                )
            )
        } else {
            favoriteButton?.setImageDrawable(
                getDrawable(
                    requireActivity(),
                    R.drawable.ic_favorite_border_black_24dp
                )
            )
        }
    }

    /**
     * setup favorite onclick listener checking
     * */
    private fun setupFavoriteButtonClickListener(movie: Movie) {
        favoriteButton?.setOnClickListener {
            if (favorite == true) {
                favoriteButton?.setImageDrawable(
                    getDrawable(
                        requireActivity(),
                        R.drawable.ic_favorite_border_black_24dp
                    )
                )
                moviesDataManager?.removeFavorite(movie, requireActivity())
                Snackbar.make(requireView(), getString(R.string.unlike), Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                favoriteButton?.setImageDrawable(
                    getDrawable(
                        requireActivity(),
                        R.drawable.ic_favorite_black_24dp
                    )
                )
                moviesDataManager?.addFavorite(movie, requireActivity())
                Snackbar.make(requireView(), getString(R.string.like), Snackbar.LENGTH_SHORT).show()
            }
        }
    }


    override fun onPause() {
        super.onPause()
        setupFavoriteButton()
    }

    override fun onResume() {
        super.onResume()
        setupFavoriteButton()
    }


    override fun onStart() {
        super.onStart()
        setupFavoriteButton()
    }

    override fun onStop() {
        super.onStop()
        setupFavoriteButton()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            favorite = savedInstanceState.getBoolean("movie_isFavourite")
        }else{
            setupFavoriteButton()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("movie_isFavourite",favorite!!)
    }
}
