package com.kulloveth.moviesapp.ui.movies

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kulloveth.moviesapp.R
import com.kulloveth.moviesapp.databinding.FragmentMoviesBinding
import com.kulloveth.moviesapp.models.CompositeItem
import com.kulloveth.moviesapp.models.Movie
import com.kulloveth.moviesapp.ui.MoviesDataManager
import com.kulloveth.moviesapp.ui.signin.AuthenticationActivity
import com.kulloveth.moviesapp.ui.signin.ProfileFragment
import com.kulloveth.moviesapp.ui.signin.SignInFragment
import com.kulloveth.moviesapp.ui.signin.SignInRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class MoviesFragment : Fragment(), MovieAdapter.MovieItemCLickedListener {

    var adapter: MovieAdapter? = null
    var moviesDataManager: MoviesDataManager? = null
    var recyclerView: RecyclerView? = null
    var binding: FragmentMoviesBinding? = null
    var movies = mutableListOf<CompositeItem>()
    var searchView: SearchView? = null


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
        adapter = MovieAdapter(this)
        recyclerView = binding?.contentLayout?.showMoviesRv
        recyclerView?.layoutManager =
            LinearLayoutManager(requireActivity())
        recyclerView?.adapter = adapter

        (requireActivity() as AppCompatActivity?)?.setSupportActionBar(binding?.contentLayout?.toolbar)

        moviesDataManager = ViewModelProvider(requireActivity()).get(MoviesDataManager::class.java)
        bindMoviesRecyclerView()


    }


    //bind data to adapter and recyclerview
    private fun bindMoviesRecyclerView() {
        moviesDataManager?.getMovieComposites()?.observe(requireActivity(), Observer {
            movies = it.toMutableList()
            adapter?.submitList(movies)
            if (movies.isEmpty()) {
                binding?.emptyMovie?.visibility = View.VISIBLE
                recyclerView?.visibility = View.INVISIBLE
            } else {
                binding?.emptyMovie?.visibility = View.INVISIBLE
                recyclerView?.visibility = View.VISIBLE
            }

        })


        //delete  a movie item from room by swiping an item either left or right
        ItemTouchHelper(object : ItemTouchHelper.Callback() {

            override fun isLongPressDragEnabled() = false

            override fun isItemViewSwipeEnabled() = true

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.START or ItemTouchHelper.END)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter?.getMovieAt(viewHolder.adapterPosition)?.movie?.let {
                    moviesDataManager?.deleteMovie(
                        it
                    )
                    Snackbar.make(
                        requireView(),
                        getString(R.string.delete_message),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

            }

        }).attachToRecyclerView(recyclerView)
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

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView
        searchMovieByTitle()
        val searchBtn = searchView?.findViewById(R.id.search_button) as ImageView
        searchBtn.imageTintList = ColorStateList.valueOf(resources.getColor(android.R.color.white))
    }


    //search movies by its title using Stateflow
    @FlowPreview
    @ExperimentalCoroutinesApi
    fun searchMovieByTitle() {
        lifecycleScope.launch {
            searchView?.getQueryTextChangeStateFlow()
                ?.debounce(300)
                ?.filter { query ->
                    if (query.isEmpty()) {
                        adapter?.submitList(movies)
                        return@filter false
                    } else {
                        return@filter true
                    }
                }
                ?.distinctUntilChanged()
                ?.flatMapLatest { query ->
                    val title = "%$query%"
                    moviesDataManager?.searchMovieByTitle(title)?.asFlow()
                        ?.catch {
                            emit(movies)
                        }!!
                }
                ?.flowOn(Dispatchers.Default)
                ?.collect { result ->
                    if (result.isEmpty()) {
                        adapter?.submitList(emptyList())
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.not_found),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        adapter?.submitList(result)
                    }
                }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        //sign user out of the app by clearing their data from shared pref
        when (item.itemId) {
            R.id.logout -> {
                SignInRepository.clearUser()
                SignInFragment.startNewActivity(
                    requireActivity(),
                    AuthenticationActivity::class.java
                )
                requireActivity().finish()
                return true
            }
            R.id.profile -> {
                val dialog = ProfileFragment()
                val fm = requireActivity().supportFragmentManager
                dialog.show(fm, "profile")
                return true
            }
        }

        return super.onOptionsItemSelected(item)


    }

}


