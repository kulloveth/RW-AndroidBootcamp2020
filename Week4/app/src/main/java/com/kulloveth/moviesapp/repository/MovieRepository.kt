package com.kulloveth.moviesapp.repository

import androidx.lifecycle.LiveData
import com.kulloveth.moviesapp.models.Movie

interface MovieRepository {
    suspend fun insertAllMovie(movieEntity: List<Movie>)

    suspend fun updateMovie(movieEntity: Movie)

    suspend fun deleteMovie(movieEntity: Movie)

    fun getMovie(id: Int): LiveData<Movie>

    fun getAllMovie(): LiveData<List<Movie>>


}