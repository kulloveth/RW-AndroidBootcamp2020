package com.kulloveth.moviesapp.room

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
    fun getAllMovie(): LiveData<List<Movie>>

    @Update
    suspend fun updateMovie(movieEntity: Movie)

    @Delete
    suspend fun deleteMovie(movieEntity: Movie)
}