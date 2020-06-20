package com.kulloveth.moviesapp

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.kulloveth.moviesapp.favorites.FavoriteList
import com.kulloveth.moviesapp.models.CompositeItem
import com.kulloveth.moviesapp.models.Header
import com.kulloveth.moviesapp.models.Movie
import java.util.HashSet


/**
 * This is a viewModel that helps to
 * manage data and simplify retaining data
 * during configuration change
 * */
class MoviesDataManager : ViewModel() {

    private val KEY_FAVORITES = "KEY_FAVORITES"
    private val _movieLiveData: MutableLiveData<Movie> = MutableLiveData()
    val movieLiveData = _movieLiveData

    private val _favoriteLiveData: MutableLiveData<List<Movie>> = MutableLiveData()


    /*
    * this method sorts  the movies and arrange
    * according to its genres
    * */
    fun getMovieComposites(): List<CompositeItem>? {
        val moviesByGenre = movieList.sortedBy { it.genre }
        val genres = moviesByGenre.map { it.genre }.distinct()

        val compositeItem = mutableListOf<CompositeItem>()
        genres.let {
            genres.forEach { genre ->
                compositeItem.add(CompositeItem.withHeader(Header(genre)))
                val movies = moviesByGenre.filter { it.genre == genre }.map { CompositeItem.withMovie(it) }
                compositeItem.addAll(movies)
            }
        }
        return compositeItem
    }

    fun setUpMovie(movie: Movie) {
        _movieLiveData.value = movie
    }

    fun saveList(key: String, mutableList: MutableList<String>, context: Context) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPrefs.putStringSet(key, mutableList.toHashSet())
        sharedPrefs.apply()
    }

    fun addFavorite(movie: Movie, context: Context) {
        val favorites = getFavorites(context)
        favorites?.let {
            movie.isFavorite = true
            favorites.add(movie.title)
            saveList(KEY_FAVORITES, favorites, context)
            Log.d("favs", "" + favorites)
        }
    }


    fun removeFavorite(movie: Movie, context: Context) {
        val favorites = getFavorites(context)
        favorites?.let {
            movie.isFavorite = false
            favorites.remove(movie.title)
            saveList(KEY_FAVORITES, favorites, context)
        }
    }

    private fun getFavorites(context: Context): MutableList<String>? {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val content = sharedPrefs.all
        val movieList = mutableListOf<String>()
        for (movie in content) {
            val taskItem = ArrayList(movie.value as HashSet<String>)
            taskItem.forEach {
                movieList.add(it)
            }

        }
        return movieList
    }

    fun getFavoriteMovies(context: Context): LiveData<List<Movie>>? {
        _favoriteLiveData.value = getFavorites(context)?.mapNotNull { getCreatureByTitle(it) }
        Log.d("favl", "" + _favoriteLiveData.value)
        return _favoriteLiveData

    }

    fun getCreatureByTitle(title: String) = movieList.firstOrNull { it.title == title }


    //fun getMovieList(): MutableList<Movie> = movieList


    // create  movie data
        val movieList = mutableListOf(
            Movie(
                1,
                "Artemis Fowl",
                "With the help of his loyal protector Butler, 12-year-old genius Artemis Fowl, descendant of a long line of criminal masterminds, seeks to find his mysteriously disappeared father, and in doing so, uncovers an ancient, underground civilization—the amazingly advanced world of fairies. Deducing that his father’s disappearance is somehow connected to the secretive, reclusive fairy world, cunning Artemis concocts a dangerous plan—so dangerous that he ultimately finds himself in a perilous war of wits with the all-powerful fairies.",
                "2020-06-12",
                R.drawable.artemis_fowl,
                "Fantasy"
            ),
            Movie(
                2,
                "Ad Astra",
                "The near future, a time when both hope and hardships drive humanity to look to the stars and beyond. While a mysterious phenomenon menaces to destroy life on planet Earth, astronaut Roy McBride undertakes a mission across the immensity of space and its many perils to uncover the truth about a lost expedition that decades before boldly faced emptiness and silence in search of the unknown.",
                "2019-09-17",
                R.drawable.ad_astra,
                "Drama"
            ),

            Movie(
                3,
                "Sonic the Hedgehog",
                "Based on the global blockbuster videogame franchise from Sega, Sonic the Hedgehog tells the story of the world’s speediest hedgehog as he embraces his new home on Earth. In this live-action adventure comedy, Sonic and his new best friend team up to defend the planet from the evil genius Dr. Robotnik and his plans for world domination.",
                "2020-02-12",
                R.drawable.sonic,
                "Action"
            ),

            Movie(
                4,
                "Da 5 Bloods",
                "Four African-American Vietnam veterans return to Vietnam. They are in search of the remains of their fallen squad leader and the promise of buried treasure. These heroes battle forces of humanity and nature while confronted by the lasting ravages of the immorality of the Vietnam War.",
                "2020-06-12",
                R.drawable.da_5bloods,
                "Drama"
            ),
            Movie(
                5,
                "Joker",
                "During the 1980s, a failed stand-up comedian is driven insane and turns to a life of crime and chaos in Gotham City while becoming an infamous psychopathic crime figure.",
                "2019-10-02",
                R.drawable.joker,
                "Action"
            ),
            Movie(
                6, "The Hunt",
                "Twelve strangers wake up in a clearing. They don't know where they are—or how they got there. In the shadow of a dark internet conspiracy theory, ruthless elitists gather at a remote location to hunt humans for sport. But their master plan is about to be derailed when one of the hunted turns the tables on her pursuers.",
                "2020-0-3-11",
                R.drawable.the_hunt,
                "Thriller"
            ),
            Movie(
                7, "Birds Of Prey",
                "Harley Quinn joins forces with a singer, an assassin and a police detective to help a young girl who had a hit placed on her after she stole a rare diamond from a crime lord.",
                "2020-02-05",
                R.drawable.birds_of_prey,
                "Comedy"
            ),
            Movie(
                8, "The Hobit",
                "Immediately after the events of The Desolation of Smaug, Bilbo and the dwarves try to defend Erebor's mountain of treasure from others who claim it: the men of the ruined Laketown and the elves of Mirkwood. Meanwhile an army of Orcs led by Azog the Defiler is marching on Erebor, fueled by the rise of the dark lord Sauron. Dwarves, elves and men must unite, and the hope for Middle-Earth falls into Bilbo's hands.",
                "2014-12-10",
                R.drawable.the_hobbit,
                "Fantasy"
            ),
            Movie(
                9, "Britt-Marie Was Here",
                "Britt-Marie, a woman in her sixties, decides to leave her husband and start anew. Having been housewife for most of her life and and living in small backwater town of Borg, there isn't many jobs available and soon she finds herself fending a youth football team.",
                "2019-01-25",
                R.drawable.britt_marie,
                "Comedy"
            ),

            Movie(
                10,
                "The Avengers",
                "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.",
                "2018-04-25",
                R.drawable.avengers,
                "Thriller"
            )

        )
}