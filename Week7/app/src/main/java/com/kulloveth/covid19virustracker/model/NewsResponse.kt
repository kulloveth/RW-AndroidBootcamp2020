package com.kulloveth.covid19virustracker.model

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("url")
    val url: String? = "",
    @SerializedName("urlToImage")
    val urlToImage: String? = ""
) : Parcelable

@Parcelize
data class NewsBaseResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
) : Parcelable
