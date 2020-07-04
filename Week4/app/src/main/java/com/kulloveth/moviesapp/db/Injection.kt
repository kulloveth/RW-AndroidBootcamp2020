package com.kulloveth.moviesapp.db

import com.kulloveth.moviesapp.db.repository.MovieRepository
import com.kulloveth.moviesapp.db.repository.RoomRepository

object Injection {

    val provideRepository: MovieRepository =
        RoomRepository()
}