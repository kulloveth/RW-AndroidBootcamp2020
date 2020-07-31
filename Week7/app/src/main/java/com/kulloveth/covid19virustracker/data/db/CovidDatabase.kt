package com.kulloveth.covid19virustracker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StatusEntity::class, NewsEntity::class], version = 1, exportSchema = false)
abstract class CovidDatabase : RoomDatabase() {

    private val TAG = CovidDatabase::class.java.simpleName
    abstract fun getStatusDao(): StatusDao
    abstract fun getNewsDao(): NewsDao
}