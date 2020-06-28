package com.kulloveth.moviesapp.db.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kulloveth.moviesapp.MovieApplication
import com.kulloveth.moviesapp.ui.signin.MoviesDataManager
import com.kulloveth.moviesapp.models.Movie
import com.kulloveth.moviesapp.db.Injection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



/**
 * Creates a database with table [Movie]
 * using Room ORM
 * insert movies once when the db is created using
 *  [RoomDatabase.Callback.onCreate]
 *
 * */

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabse : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao

    companion object {
        @Volatile
        private var instance: MovieDatabse? = null
        private val LOCK = Any()
        val context = MovieApplication.getContext()
        operator fun invoke() = instance ?: synchronized(LOCK) {
            instance ?: builDatabase().also {
                instance = it
            }
        }



        private fun builDatabase() = Room.databaseBuilder(
            context,
            MovieDatabse::class.java,
            "movie_database"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                GlobalScope.launch(context = Dispatchers.IO) {
                    Injection.provideRepository.insertAllMovie(
                        MoviesDataManager.movieList)
                }
            }
        }).build()
    }
}