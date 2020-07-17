package com.kulloveth.covid19virustracker.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "news")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String? = "",
    val description: String? = "",
    val url: String? = "",
    val urlToImage: String? = ""
) : Parcelable

@Parcelize
data class NewsBaseResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
) : Parcelable
