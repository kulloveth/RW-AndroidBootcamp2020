package com.kulloveth.covid19virustracker.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


//object representation of the api data
@Parcelize
data class StatusResponse(
    @SerializedName("updated") val updated: Long,
    @SerializedName("country") val country: String,
    @SerializedName("countryInfo") val countryInfo: CountryInfo,
    @SerializedName("cases") val cases: Int,
    @SerializedName("todayCases") val todayCases: Int,
    @SerializedName("deaths") val deaths: Int,
    @SerializedName("todayDeaths") val todayDeaths: Int,
    @SerializedName("recovered") val recovered: Int,
    @SerializedName("todayRecovered") val todayRecovered: Int,
    @SerializedName("active") val active: Int,
    @SerializedName("critical") val critical: Int
) : Parcelable

@Parcelize
data class CountryInfo(
    @SerializedName("_id") val _id: Int,
    @SerializedName("flag") val flag: String
) : Parcelable



