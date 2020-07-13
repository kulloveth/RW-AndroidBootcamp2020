package com.kulloveth.moviesapp.ui.favorites

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kulloveth.moviesapp.databinding.FragmentFavoriteBinding
import com.kulloveth.moviesapp.ui.MoviesDataManager

class FavoriteFragment : Fragment() {

    private val TAG = FavoriteFragment::class.java.simpleName
    var moviesDataManager: MoviesDataManager? = null
    var binding: FragmentFavoriteBinding? = null
    var recyclerView: RecyclerView? = null
    var adapter: FavoriteAdapter? = null

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
        val view = binding?.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding?.contentLayout?.toolbar?.title = "Favorite Movies"
        recyclerView = binding?.contentLayout?.showMoviesRv

        //initialiazing the moviesDataManager
        moviesDataManager = ViewModelProvider(this).get(MoviesDataManager::class.java)

        adapter = FavoriteAdapter()
        bindFavoritesToRecyclerView()
    }


    fun bindFavoritesToRecyclerView() {
        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView?.layoutManager =
                LinearLayoutManager(requireActivity())
        } else {

            val layoutManager =
                GridLayoutManager(requireActivity(), 2)
            recyclerView?.layoutManager = layoutManager
        }
        recyclerView?.adapter = adapter
        moviesDataManager?.getFavoriteMovies(true)
            ?.observe(requireActivity(), Observer {
                Log.d(TAG, "" + it)
                adapter?.submitList(it)
                if (it.isEmpty()) {
                    binding?.noLikeTv?.visibility = View.VISIBLE
                    recyclerView?.visibility = View.INVISIBLE
                } else {
                    binding?.noLikeTv?.visibility = View.INVISIBLE
                    recyclerView?.visibility = View.VISIBLE
                }

            })
    }


}
