package com.kulloveth.moviesapp.favorites

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kulloveth.moviesapp.MoviesDataManager

import com.kulloveth.moviesapp.R
import com.kulloveth.moviesapp.databinding.FragmentFavoriteBinding
import com.kulloveth.moviesapp.models.Movie
import com.kulloveth.moviesapp.movies.MovieAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView = binding.contentLayout.showMoviesRv
        moviesDataManager = ViewModelProvider(this).get(MoviesDataManager::class.java)

        adapter = FavoriteAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = adapter
        moviesDataManager.getFavoriteMovies(requireActivity())
            ?.observe(requireActivity(), Observer {
                Log.d("fav",""+it)
                adapter.submitList(it)
            })

    }

    override fun movieItemCLicked(movie: Movie) {

    }


}
