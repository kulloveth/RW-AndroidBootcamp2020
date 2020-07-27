package com.kulloveth.covid19virustracker.model

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


//object representation of the api data
data class StatusBaseResponse(val data: Data)
data class Data(
    val paginationMeta: PaginationMeta,
    val last_update: String,
    @SerializedName("rows")
    val status: List<CountryStatus>
)

@Parcelize
data class CountryStatus(
    @PrimaryKey
    @SerializedName("country")
    val country: String,
    @SerializedName("country_abbreviation")
    val country_abbreviation: String? = "",
    @SerializedName("total_cases")
    val total_cases: String? = "",
    @SerializedName("new_cases")
    val new_cases: String? = "",
    @SerializedName("total_deaths")
    val total_deaths: String? = "",
    @SerializedName("new_deaths")
    val new_deaths: String? = "",
    @SerializedName("total_recovered")
    val total_recovered: String? = "",
    @SerializedName("active_cases")
    val active_cases: String? = "",
    @SerializedName("serious_critical")
    val serious_critical: String? = "",
    @SerializedName("cases_per_mill_pop")
    val cases_per_mill_pop: String? = "",
    @SerializedName("flag")
    val flag: String? = ""
) : Parcelable

data class PaginationMeta(
    val currentPage: Int,
    val currentPageSize: Int,
    val totalPages: Int,
    val totalRecords: Int
)



