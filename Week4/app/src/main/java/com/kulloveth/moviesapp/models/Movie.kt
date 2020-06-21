package com.kulloveth.moviesapp.models

data class Movie(
    val id: Int,
    var title: String,
    val overview: String,
    val releaseDate: String,
    val image: Int,
    val genre: String,
    var isFavorite:Boolean = false
){
    val thumbnail: String
        get() = "drawable/thumbnail_$image"
}
