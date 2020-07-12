package com.kulloveth.covid19virustracker.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kulloveth.covid19virustracker.model.Article

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: List<Article>)

    @Query("SELECT * FROM news")
    fun covidNews(): DataSource.Factory<Int, Article>

}