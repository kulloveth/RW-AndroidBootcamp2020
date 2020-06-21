package com.kulloveth.moviesapp.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
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

    lateinit var binding: FragmentMovieDetailBinding
    lateinit var moviesDataManager: MoviesDataManager
    lateinit var favoriteButton: FloatingActionButton
    var movie: Movie? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val toolbar = binding.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            requireView().findNavController()
                .navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentToMovieList())
        }
        favoriteButton = binding.favoriteBtn
        moviesDataManager = ViewModelProvider(requireActivity()).get(MoviesDataManager::class.java)

        getMovies()
        setupFavoriteButton()
    }

    //set movie detail
    private fun getMovies() {
        //observing movie items from the viewmodel
        moviesDataManager.movieLiveData.observe(requireActivity(), Observer {
            binding.toolbar.title = it.title
            movie = it
            binding.title.text = it.title
            binding.genre.text = it.genre
            binding.overview.text = it.overview
            binding.releaseDate.text = it.releaseDate
            binding.moviePoster.setImageResource(it.image)
        })
    }

    private fun setupFavoriteButton() {
        movie?.let { setupFavoriteButtonImage(it) }
        movie?.let { setupFavoriteButtonClickListener(it) }
    }

    private fun setupFavoriteButtonImage(movie: Movie) {
        if (moviesDataManager.isFavorite(movie)) {
            favoriteButton.setImageDrawable(
                getDrawable(
                    requireActivity(),
                    R.drawable.ic_favorite_black_24dp
                )
            )
        } else {
            favoriteButton.setImageDrawable(
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
        favoriteButton.setOnClickListener {
            if (moviesDataManager.isFavorite(movie)) {
                favoriteButton.setImageDrawable(
                    getDrawable(
                        requireActivity(),
                        R.drawable.ic_favorite_border_black_24dp
                    )
                )
                moviesDataManager.removeFavorite(movie, requireActivity())
                Snackbar.make(requireView(), getString(R.string.unlike), Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                favoriteButton.setImageDrawable(
                    getDrawable(
                        requireActivity(),
                        R.drawable.ic_favorite_black_24dp
                    )
                )
                moviesDataManager.addFavorite(movie, requireActivity())
                Snackbar.make(requireView(), getString(R.string.like), Snackbar.LENGTH_SHORT).show()
            }
        }
    }





}
