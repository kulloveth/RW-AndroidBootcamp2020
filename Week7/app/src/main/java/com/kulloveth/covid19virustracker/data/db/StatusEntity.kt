package com.kulloveth.covid19virustracker.data.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Status entity with tableName
 * status for accessing the status updates
 * */
@Parcelize
@Entity(tableName = "status")
data class StatusEntity(
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
