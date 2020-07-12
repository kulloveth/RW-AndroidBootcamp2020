package com.kulloveth.covid19virustracker.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kulloveth.covid19virustracker.model.CountryStatus

@Dao
interface StatusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(status: List<CountryStatus>)

    @Query("SELECT * FROM status order by country ASC")
    fun statusByCountry(): DataSource.Factory<Int, CountryStatus>

    @Query("DELETE FROM status")
    suspend fun clearStatus()
}