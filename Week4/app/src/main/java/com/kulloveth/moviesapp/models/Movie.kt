package com.kulloveth.moviesapp.models

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val image: Int,
    val genre: String
)
