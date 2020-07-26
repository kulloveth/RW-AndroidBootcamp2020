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
    var country: String,
    var country_abbreviation: String? = "",
    var total_cases: String? = "",
    var new_cases: String? = "",
    var total_deaths: String? = "",
    var new_deaths: String? = "",
    var total_recovered: String? = "",
    var active_cases: String? = "",
    var serious_critical: String? = "",
    var cases_per_mill_pop: String? = "",
    var flag: String? = ""
) : Parcelable
