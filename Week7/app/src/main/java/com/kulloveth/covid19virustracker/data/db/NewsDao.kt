package com.kulloveth.covid19virustracker.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: List<NewsEntity>)

    @Query("SELECT * FROM news order by id DESC")
    suspend fun getNewCovidNews(): List<NewsEntity>


}