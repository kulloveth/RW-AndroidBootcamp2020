package com.kulloveth.moviesapp.repository

object Injection {

    val provideRepository: MovieRepository = RoomRepository()
}