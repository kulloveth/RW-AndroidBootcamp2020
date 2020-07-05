package com.kulloveth.moviesapp.db.repository

import com.kulloveth.moviesapp.db.room.MovieDatabse
import com.kulloveth.moviesapp.models.Movie
import kotlinx.coroutines.flow.Flow



class RoomRepository : MovieRepository {
    val dao= MovieDatabse.invoke().getMovieDao()

    override suspend fun insertAllMovie(movieEntity: List<Movie>) {
        dao.insertAllMovie(movieEntity)
    }

    override suspend fun updateMovie(movieEntity: Movie) {
        dao.updateMovie(movieEntity)
    }

    override suspend fun deleteMovie(movieEntity: Movie) {
        dao.deleteMovie(movieEntity)
    }

    override fun getAllMovies(): Flow<List<Movie>> {
        return dao.getAllMovies()
    }

    override fun searchMovies(query:String): Flow<List<Movie>> {
        return dao.searchMovies(query)
    }

    override fun getMovie(id: Int) =
        dao.getMovie(id)


    override fun getAllMovie() =
        dao.getAllMovie()

    override fun getFavorite(isFavorite: Boolean) =
        dao.getFavorites(isFavorite)


}