package com.kulloveth.moviesapp.db.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kulloveth.moviesapp.models.Movie
import kotlinx.coroutines.flow.Flow

/**
 * contains data access objects
 * to query room database
 * */
@Dao
interface MovieDao {

    //insert list of movies ignoring conflicts
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllMovie(movieEntity: List<Movie>)

    //insert a movie ignoring conflict
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movieEntity: Movie)

    //retrieve a particular movie by its id
    @Query("Select * from movie where id =:id")
    fun getMovie(id: Int): LiveData<Movie>

    //retrieve list of movies
    @Query("Select * from movie order by id ASC")
    fun getAllMovie(): List<Movie>

    //retrieve list of movies using flow
    @Query("Select * from movie order by id ASC")
    fun getAllMovies(): Flow<List<Movie>>

    //update a particular movie using coroutine
    @Update
    suspend fun updateMovie(movieEntity: Movie)

    //delete a particular movie using coroutine
    @Delete
    suspend fun deleteMovie(movieEntity: Movie)

    //fetch favorite movies using flow
    @Query("Select * FROM Movie where isFavorite =:isFavorite")
    fun getFavorites(isFavorite: Boolean): Flow<List<Movie>>
}