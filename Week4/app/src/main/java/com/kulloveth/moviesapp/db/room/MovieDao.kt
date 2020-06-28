package com.kulloveth.moviesapp.db.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kulloveth.moviesapp.models.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllMovie(movieEntity: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movieEntity: Movie)

    @Query("Select * from movie where id =:id")
    fun getMovie(id: Int): LiveData<Movie>

    @Query("Select * from movie order by id ASC")
    suspend fun getAllMovie(): List<Movie>

    @Query("Select * from movie order by id ASC")
    fun getAllMovies(): LiveData<List<Movie>>

    @Update
    suspend fun updateMovie(movieEntity: Movie)

    @Delete
    suspend fun deleteMovie(movieEntity: Movie)

    @Query("Select * FROM Movie where isFavorite =:isFavorite")
    fun getFavorites(isFavorite:Boolean):LiveData<List<Movie>>
}