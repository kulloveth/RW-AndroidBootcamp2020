package com.kulloveth.covid19virustracker.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey val statusId: String,
    val prevKey: Int?,
    val nextKey: Int?
)