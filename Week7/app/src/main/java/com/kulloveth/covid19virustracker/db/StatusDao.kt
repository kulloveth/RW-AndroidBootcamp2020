package com.kulloveth.covid19virustracker.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kulloveth.covid19virustracker.model.CountryStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface StatusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(status: List<CountryStatus>)

    @Query("SELECT * FROM status order by country ASC")
    fun statusByCountry(): PagingSource<Int, CountryStatus>

    @Query("DELETE FROM status")
    suspend fun clearStatus()
}