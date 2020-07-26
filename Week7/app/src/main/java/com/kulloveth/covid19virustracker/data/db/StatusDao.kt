package com.kulloveth.covid19virustracker.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StatusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(status: List<StatusEntity>)

    @Query("SELECT * FROM status order by country ASC")
    suspend fun getNewStatus():List<StatusEntity>

    //get status from room as a datasource for paging
    @Query("SELECT * FROM status order by country ASC")
    fun statusByCountry(): DataSource.Factory<Int, StatusEntity>

}