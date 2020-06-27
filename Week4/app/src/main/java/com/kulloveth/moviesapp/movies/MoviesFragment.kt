package com.kulloveth.moviesapp.movies

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kulloveth.moviesapp.MoviesDataManager
import com.kulloveth.moviesapp.R
import com.kulloveth.moviesapp.databinding.FragmentMoviesBinding
import com.kulloveth.moviesapp.models.Movie
import com.kulloveth.moviesapp.repository.Injection
import com.kulloveth.moviesapp.room.MovieDatabse
import com.kulloveth.moviesapp.signin.SignInActivity


class MoviesFragment : Fragment(), MovieAdapter.MovieItemCLickedListener {

    var adapter: MovieAdapter? = null
    var moviesDataManager: MoviesDataManager? = null
    var recyclerView: RecyclerView? = null
    var binding: FragmentMoviesBinding? = null
    private val PICK_IMAGE = 322


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(layoutInflater)
        val view = binding?.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding?.contentLayout?.toolbar?.title = getString(R.string.movies)
        (requireActivity() as AppCompatActivity?)?.setSupportActionBar(binding?.contentLayout?.toolbar)
        moviesDataManager = ViewModelProvider(requireActivity()).get(MoviesDataManager::class.java)
        bindMoviesRecyclerView()
        val userName = SignInActivity.sharedPref(requireActivity()).getString(SignInActivity.USER_NAME_KEY,"")

        binding?.contentLayout?.userName?.text = userName?.toUpperCase()
        Injection.provideRepository.getAllMovie().observe(requireActivity(),
            Observer {
                Log.d("chukwuo", "$it")
            })

    }


    //bind data to adapter and recyclerview
    private fun bindMoviesRecyclerView() {
        adapter = MovieAdapter(this)
        recyclerView = binding?.contentLayout?.showMoviesRv
        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView?.layoutManager =
                LinearLayoutManager(requireActivity())
        } else {

            val layoutManager =
                GridLayoutManager(requireActivity(), 2)
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (adapter?.getItemViewType(position)) {
                        0 -> 2
                        else -> 1
                    }
                }
            }
            recyclerView?.layoutManager = layoutManager
        }

        adapter?.submitList(moviesDataManager?.getMovieComposites())
        recyclerView?.adapter = adapter
    }

    override fun movieItemCLicked(movie: Movie) {
        //used to get the clicked movie
        moviesDataManager?.setUpMovie(movie)
        requireView().findNavController()
            .navigate(
                MoviesFragmentDirections.actionMovieListToMovieDetailFragment(
                    movie.title,
                    movie.genre,
                    movie.id,
                    movie.overview,
                    movie.releaseDate,
                    movie.image
                )
            )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if (item.itemId == R.id.logout) {
            val preferences: SharedPreferences.Editor = SignInActivity.sharedPref(requireContext()).edit()
            preferences.clear()
            preferences.apply()
            startActivity(Intent(requireActivity(), SignInActivity::class.java))
            return true


        }

        return super.onOptionsItemSelected(item)

    }

    private fun getImagefromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "select a picture"), PICK_IMAGE)
    }




    






}
