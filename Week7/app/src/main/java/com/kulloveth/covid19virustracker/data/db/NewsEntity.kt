package com.kulloveth.covid19virustracker.data.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * News Entity with table name news
 * for accessing the news update
 * */
@Entity(tableName = "news")
@Parcelize
data class NewsEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val title: String? = "",
    val description: String? = "",
    val url: String? = "",
    val urlToImage: String? = ""
): Parcelable