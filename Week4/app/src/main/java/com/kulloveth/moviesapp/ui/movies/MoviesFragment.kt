package com.kulloveth.moviesapp.ui.movies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.kulloveth.moviesapp.R
import com.kulloveth.moviesapp.databinding.FragmentMoviesBinding
import com.kulloveth.moviesapp.models.CompositeItem
import com.kulloveth.moviesapp.models.Movie
import com.kulloveth.moviesapp.ui.MoviesDataManager
import com.kulloveth.moviesapp.ui.signin.AuthenticationActivity
import com.kulloveth.moviesapp.ui.signin.SignInFragment
import com.kulloveth.moviesapp.ui.signin.SignInRepository


class MoviesFragment : Fragment(), MovieAdapter.MovieItemCLickedListener {

    var adapter: MovieAdapter? = null
    var moviesDataManager: MoviesDataManager? = null
    var recyclerView: RecyclerView? = null
    var binding: FragmentMoviesBinding? = null
    var movies = mutableListOf<CompositeItem>()


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
        val sharedPref = SignInRepository.sharedPrefs()
        val userName =
            sharedPref.getString(SignInFragment.USER_NAME_KEY, "")
        val userImage = sharedPref.getString(SignInFragment.USER_IMAGE_KEY, "")
        binding?.contentLayout?.toolbarImage?.let {
            Glide.with(this).load(userImage).placeholder(R.drawable.ic_account_circle_white_24dp)
                .circleCrop().into(it)
        }

        binding?.contentLayout?.userName?.text = userName?.toUpperCase()

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
                adapter?.getNoteAt(viewHolder.adapterPosition)?.movie?.let {
                    moviesDataManager?.deleteMovie(
                        it
                    )
                    movies.removeAt(viewHolder.adapterPosition)
                    adapter?.notifyDataSetChanged()
                    adapter?.notifyItemRangeChanged(viewHolder.adapterPosition, movies.size)
                    moviesDataManager?.getMovieComposites()
                        ?.observe(requireActivity(), Observer { m ->

                            movies = m.toMutableList()
                            adapter?.submitList(movies)
                            recyclerView?.adapter = adapter
                            requireActivity().recreate()
                        })
                    Snackbar.make(requireView(), "movie deleted", Snackbar.LENGTH_SHORT).show()
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if (item.itemId == R.id.logout) {
            SignInRepository.clearUser()
            val intent = Intent(
                requireActivity(),
                AuthenticationActivity::class.java
            )
            requireActivity().startActivity(intent)
            requireActivity().finish()
            return true
        }

        return super.onOptionsItemSelected(item)

    }


}


