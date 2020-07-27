package com.kulloveth.covid19virustracker.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kulloveth.covid19virustracker.App

@Database(entities = [StatusEntity::class, NewsEntity::class], version = 1, exportSchema = false)
abstract class CovidDatabase : RoomDatabase() {

    private val TAG = CovidDatabase::class.java.simpleName
    abstract fun getStatusDao(): StatusDao
    abstract fun getNewsDao(): NewsDao

    companion object {
        @Volatile
        private var instance: CovidDatabase? = null
        private val LOCK = Any()
        val context = App.getContext()
        operator fun invoke() = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase().also {
                instance = it
            }
        }


        //build database
        private fun buildDatabase(): CovidDatabase {
            return Room.databaseBuilder(
                context,
                CovidDatabase::class.java,
                "covid_database"
            ).build()
        }

    }
}