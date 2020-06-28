package com.kulloveth.moviesapp.db.repository

import androidx.lifecycle.LiveData
import com.kulloveth.moviesapp.models.Movie

interface MovieRepository {

    suspend fun insertAllMovie(movieEntity: List<Movie>)

    suspend fun updateMovie(movieEntity: Movie)

    suspend fun deleteMovie(movieEntity: Movie)

    fun getMovie(id: Int): LiveData<Movie>

    suspend fun getAllMovie(): List<Movie>

    fun getAllMovies(): LiveData<List<Movie>>

    fun getFavorite(isFavorite: Boolean): LiveData<List<Movie>>


}