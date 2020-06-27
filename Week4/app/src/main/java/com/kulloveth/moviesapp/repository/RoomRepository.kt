package com.kulloveth.moviesapp.repository

import com.kulloveth.moviesapp.MoviesDataManager
import com.kulloveth.moviesapp.models.Movie
import com.kulloveth.moviesapp.room.MovieDatabse


class RoomRepository : MovieRepository {
    val dao = MovieDatabse.invoke().getMovieDao()
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


    override fun getAllMovie() =
        dao.getAllMovie()


}