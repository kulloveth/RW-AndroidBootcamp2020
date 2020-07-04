package com.kulloveth.moviesapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 *  annotation [Parcelize] simplifies [Parcelable]
 *  implementation and automatically generates the methods and
 *  creator factorys for you but for only primary constructor
 *  properties
 * */

@Parcelize
@Entity
data class Movie(
    @PrimaryKey
    val id: Int,
    var title: String,
    val overview: String,
    val releaseDate: String,
    val image: Int,
    val genre: String,
    var isFavorite: Boolean = false
) : Parcelable
