package com.kulloveth.moviesapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.kulloveth.moviesapp.MovieApplication
import com.kulloveth.moviesapp.R
import com.kulloveth.moviesapp.db.Injection
import com.kulloveth.moviesapp.models.CompositeItem
import com.kulloveth.moviesapp.models.Header
import com.kulloveth.moviesapp.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


/**
 * This is a viewModel class that helps to
 * manage data and simplify retaining data
 * during configuration change
 * */
class MoviesDataManager(application: Application) : AndroidViewModel(application) {

    private val TAG = MoviesDataManager::class.java.simpleName
    val context = application.applicationContext
    val repository = Injection.provideRepository


    private val _compositeLiveData: MutableLiveData<List<CompositeItem>> = MutableLiveData()
    val _fompositeLiveData: MutableLiveData<List<CompositeItem>> = MutableLiveData()

    // setting up movies to pass between fragments
    private val _movieLiveData: MutableLiveData<Movie> = MutableLiveData()
    val movieLiveData = _movieLiveData


    /*
    * fetch movies from room db on background thread
    * moves them to the ui then
    * sorts  the movies and arrange
    * according to its genres
    * */

    fun getMovieComposites(): LiveData<List<CompositeItem>> {
        return repository.getAllMovies().map {
            val moviesList = it.toMutableList()
            Log.d(TAG, "$moviesList")
            val moviesByGenre = moviesList.sortedBy { it.genre }
            val genres = moviesByGenre.map { it.genre }.distinct()

            val compositeItem = mutableListOf<CompositeItem>()
            genres.let {
                genres.forEach { genre ->
                    compositeItem.add(CompositeItem.withHeader(Header(genre)))
                    val movies =
                        moviesByGenre.filter { it.genre == genre }
                            .map { CompositeItem.withMovie(it) }
                    compositeItem.addAll(movies)
                }
                Log.d(TAG, "flow = $compositeItem")

            }
            compositeItem
        }.asLiveData()
    }

    //deletes a single movie from database
    fun deleteMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMovie(movie)
        }
    }

    fun setUpMovie(movie: Movie) {
        _movieLiveData.value = movie
    }


    /**
     * changes isFavorite to true
     * when a movie is liked and updates database
     *
     *
     * */
    fun addFavorite(movie: Movie) {
        var favorites = false
        viewModelScope.launch(Dispatchers.IO) {
            val movies = repository.getAllMovie()
            movies.forEach {
                favorites = it.isFavorite
            }
            favorites.let {
                movie.isFavorite = true
                repository.updateMovie(movie)
                Log.d(TAG, "$favorites")
            }
        }

    }


    /*
    * changes isFavorite to false
    * and updates movie in database
    * */
    fun removeFavorite(movie: Movie) {

        var favorites = false
        viewModelScope.launch(Dispatchers.IO) {
            val movies = repository.getAllMovie()
            movies.forEach {
                favorites = it.isFavorite
            }
            favorites.let {
                movie.isFavorite = false
                repository.updateMovie(movie)
                Log.d(TAG, "$favorites")
            }
        }
    }


    /*
    * setup the favorite movies to be observed
    * */
    fun getFavoriteMovies(isFavorite: Boolean): LiveData<List<Movie>>? {
        return repository.getFavorite(isFavorite).asLiveData()

    }


    // movie data
    companion object {
        val context =
            MovieApplication.getContext()
        val movieList = mutableListOf(
            Movie(
                1,
                context.getString(
                    R.string.artemis_title
                ),
                context.getString(
                    R.string.artemis_overview
                ),
                context.getString(
                    R.string.artemis_release_date
                ),
                R.drawable.artemis_fowl,
                context.getString(
                    R.string.artemis_genre
                )
            ),
            Movie(
                2,
                context.getString(
                    R.string.astra_title
                ),
                context.getString(
                    R.string.astra_overview
                ),
                context.getString(
                    R.string.astra_release_date
                ),
                R.drawable.ad_astra,
                context.getString(
                    R.string.astra_genre
                )
            ),

            Movie(
                3,
                context.getString(
                    R.string.sonic_title
                ),
                context.getString(
                    R.string.sonic_overview
                ),
                context.getString(
                    R.string.sonic_release_date
                ),
                R.drawable.sonic,
                context.getString(
                    R.string.sonic_genre
                )
            ),

            Movie(
                4,
                context.getString(
                    R.string.da5bloods_title
                ),
                context.getString(
                    R.string.da5_bloods_overview
                ),
                context.getString(
                    R.string.da5blood_date
                ),
                R.drawable.da_5bloods,
                context.getString(
                    R.string.da5blood_genre
                )
            ),
            Movie(
                5,
                context.getString(
                    R.string.joker_title
                ),
                context.getString(
                    R.string.joker_overview
                ),
                context.getString(
                    R.string.joker_release_date
                ),
                R.drawable.joker,
                context.getString(
                    R.string.joker_genre
                )
            ),
            Movie(
                6, context.getString(
                    R.string.hunt_title
                ),
                context.getString(
                    R.string.hunt_overview
                ),
                context.getString(
                    R.string.hunt_release_date
                ),
                R.drawable.the_hunt,
                context.getString(
                    R.string.hunt_genre
                )
            ),
            Movie(
                7, context.getString(
                    R.string.prey_title
                ),
                context.getString(
                    R.string.prey_overview
                ),
                context.getString(
                    R.string.prey_release_date
                ),
                R.drawable.birds_of_prey,
                context.getString(
                    R.string.prey_genre
                )
            ),
            Movie(
                8, context.getString(
                    R.string.hobit_title
                ),
                context.getString(
                    R.string.hobit_overview
                ),
                context.getString(
                    R.string.hobit_release_date
                ),
                R.drawable.the_hobbit,
                context.getString(
                    R.string.hobit_genre
                )
            ),
            Movie(
                9, context.getString(
                    R.string.britt_title
                ),
                context.getString(
                    R.string.britt_overview
                ),
                "2019-01-25",
                R.drawable.britt_marie,
                context.getString(
                    R.string.britt_genre
                )
            ),

            Movie(
                10,
                context.getString(
                    R.string.avenger_title
                ),
                context.getString(
                    R.string.avenger_overview
                ),
                context.getString(
                    R.string.avenger_release_date
                ),
                R.drawable.avengers,
                context.getString(
                    R.string.avenger_genre
                )
            )

        )
    }
}