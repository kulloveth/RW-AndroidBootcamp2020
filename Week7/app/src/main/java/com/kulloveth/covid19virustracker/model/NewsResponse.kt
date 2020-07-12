package com.kulloveth.covid19virustracker.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    val id: Int,
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
) : Parcelable

@Parcelize
data class NewsBaseResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
) : Parcelable

@Parcelize
data class Source(val id: String, val name: String): Parcelable