package com.kulloveth.covid19virustracker.data.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kulloveth.covid19virustracker.model.CountryStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface StatusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(status: List<StatusEntity>)


    @Query("SELECT * FROM status order by country ASC")
   fun getNewStatus():Flow<List<StatusEntity>>

    //get status from room as a datasource for paging
    @Query("SELECT * FROM status order by country ASC")
    fun statusByCountry(): DataSource.Factory<Int, StatusEntity>

}