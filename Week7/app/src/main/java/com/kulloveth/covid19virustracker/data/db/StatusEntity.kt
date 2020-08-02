package com.kulloveth.covid19virustracker.data.db

import android.os.Parcelable
import androidx.room.Embedded
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
    @Embedded
    var countryInfoEntity: CountryInfoEntity = CountryInfoEntity(),
    var cases: Int = 0,
    var todayCases: Int = 0,
    var deaths: Int = 0,
    var todayDeaths: Int = 0,
    var recovered: Int = 0,
    var todayRecovered: Int = 0,
    var active: Int = 0,
    var critical: Int = 0
) : Parcelable

@Parcelize
@Entity(tableName = "country_info")
data class CountryInfoEntity(
    @PrimaryKey
    var id: Int = 0,
    var flag: String = ""
) : Parcelable