package com.kulloveth.moviesapp.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kulloveth.moviesapp.MoviesDataManager

import com.kulloveth.moviesapp.R
import com.kulloveth.moviesapp.databinding.FragmentMoviesBinding

/**
 * A simple [Fragment] subclass.
 */
class MoviesFragment : Fragment() {

    lateinit var adapter: MovieAdapter
    lateinit var moviesDataManager: MoviesDataManager
    lateinit var recyclerView: RecyclerView
    lateinit var binding: FragmentMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = MovieAdapter()
        recyclerView = binding.contentLayout.showMoviesRv
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        moviesDataManager = ViewModelProvider(this).get(MoviesDataManager::class.java)
        adapter.submitList(moviesDataManager.getMovieList())
        recyclerView.adapter = adapter

    }

}
