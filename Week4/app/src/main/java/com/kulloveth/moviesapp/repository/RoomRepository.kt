package com.kulloveth.moviesapp.repository

import android.util.Log
import androidx.lifecycle.*
import com.kulloveth.moviesapp.MovieApplication
import com.kulloveth.moviesapp.MoviesDataManager
import com.kulloveth.moviesapp.models.CompositeItem
import com.kulloveth.moviesapp.models.Header
import com.kulloveth.moviesapp.models.Movie
import com.kulloveth.moviesapp.room.MovieDatabse


class RoomRepository : MovieRepository {
    val dao = MovieDatabse.invoke().getMovieDao()
    var moviesLiveData: LiveData<List<Movie>> = MutableLiveData<List<Movie>>()
    override suspend fun insertAllMovie(movieEntity: List<Movie>) {
        dao.insertAllMovie(movieEntity)
    }

    override suspend fun updateMovie(movieEntity: Movie) {
        dao.updateMovie(movieEntity)
    }

    override suspend fun deleteMovie(movieEntity: Movie) {
        dao.deleteMovie(movieEntity)
    }

    override fun getMovie(id: Int) =
        dao.getMovie(id)

    override fun getFavorite(isFavorite: Boolean): LiveData<List<Movie>> {
        return dao.getFavorites(isFavorite)
    }

    override suspend fun getAllMovie() =
        dao.getAllMovie()



}