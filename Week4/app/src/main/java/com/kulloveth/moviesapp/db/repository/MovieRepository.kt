package com.kulloveth.moviesapp.db.repository

import androidx.lifecycle.LiveData
import com.kulloveth.moviesapp.models.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun insertAllMovie(movieEntity: List<Movie>)

    suspend fun updateMovie(movieEntity: Movie)

    suspend fun deleteMovie(movieEntity: Movie)

    fun getMovie(id: Int): LiveData<Movie>

    fun getAllMovie(): List<Movie>

    fun getAllMovies(): Flow<List<Movie>>

    fun getFavorite(isFavorite: Boolean): LiveData<List<Movie>>


}