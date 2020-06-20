package com.kulloveth.moviesapp.favorites

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kulloveth.moviesapp.MoviesDataManager

import com.kulloveth.moviesapp.R
import com.kulloveth.moviesapp.databinding.FragmentFavoriteBinding
import com.kulloveth.moviesapp.models.Movie
import com.kulloveth.moviesapp.movies.MovieAdapter
import com.kulloveth.moviesapp.movies.MoviesFragmentDirections

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment(), FavoriteAdapter.MovieItemCLickedListener {

    lateinit var moviesDataManager: MoviesDataManager
    lateinit var binding: FragmentFavoriteBinding
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.contentLayout.toolbar.title = "Favorite Movies"
        recyclerView = binding.contentLayout.showMoviesRv
        moviesDataManager = ViewModelProvider(this).get(MoviesDataManager::class.java)

        adapter = FavoriteAdapter(this)
        bindDataToRecyclerView()


    }


    fun bindDataToRecyclerView() {
        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager =
                LinearLayoutManager(requireActivity())
        } else {

            val layoutManager =
                GridLayoutManager(requireActivity(), 2)
            recyclerView.layoutManager = layoutManager
        }
        recyclerView.adapter = adapter
        moviesDataManager.getFavoriteMovies(requireActivity())
            ?.observe(requireActivity(), Observer {
                Log.d("fav", "" + it)
                adapter.submitList(it)
            })
    }

    override fun movieItemCLicked(movie: Movie) {
    }


}
