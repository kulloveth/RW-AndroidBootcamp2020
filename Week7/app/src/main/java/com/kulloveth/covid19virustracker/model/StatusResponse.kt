package com.kulloveth.covid19virustracker.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*


//object representation of the api data
data class StatusBaseResponse(val data: Data)
data class Data(
    val paginationMeta: PaginationMeta,
    val last_update: String,
    @SerializedName("rows")
    val status: List<CountryStatus>
)

@Parcelize
@Entity(tableName = "status")
data class CountryStatus(
    @PrimaryKey
    val country: String,
    val country_abbreviation: String? = "",
    val total_cases: String? = "",
    val new_cases: String? = "",
    val total_deaths: String? = "",
    val new_deaths: String? = "",
    val total_recovered: String? = "",
    val active_cases: String? = "",
    val serious_critical: String? = "",
    val cases_per_mill_pop: String? = "",
    val flag: String? = ""
) : Parcelable

data class PaginationMeta(
    val currentPage: Int,
    val currentPageSize: Int,
    val totalPages: Int,
    val totalRecords: Int
)



