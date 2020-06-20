package com.kulloveth.moviesapp.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
        moviesDataManager = ViewModelProvider(requireActivity()).get(MoviesDataManager::class.java)
        getMovies()
    }

    private fun getMovies() {
        moviesDataManager.movieLiveData.observe(requireActivity(), Observer {
            binding.title.text = it.title
            binding.genre.text = it.genre
            binding.overview.text = it.overview
            binding.releaseDate.text = it.releaseDate
            binding.moviePoster.setImageResource(it.image)
        })
    }

}
