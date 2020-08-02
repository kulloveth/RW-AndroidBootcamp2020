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
    var id: Int=0,
    var title: String? = "",
    var description: String? = "",
    var url: String? = "",
    var urlToImage: String? = ""
): Parcelable