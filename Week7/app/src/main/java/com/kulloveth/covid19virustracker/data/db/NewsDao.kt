package com.kulloveth.covid19virustracker.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: List<NewsEntity>)

    //get news from room as a datasource for paging news in descending order
    @Query("SELECT * FROM news order by id DESC")
    fun covidNews(): DataSource.Factory<Int, NewsEntity>

}