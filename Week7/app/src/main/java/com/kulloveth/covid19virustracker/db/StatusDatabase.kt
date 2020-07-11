package com.kulloveth.covid19virustracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kulloveth.covid19virustracker.App
import com.kulloveth.covid19virustracker.model.CountryStatus

@Database(entities = [CountryStatus::class,RemoteKey::class], version = 1, exportSchema = false)
abstract class StatusDatabase : RoomDatabase() {
    abstract fun getStatusDao(): StatusDao
    abstract fun remoteKeysDao(): RemoteKeyDao

    companion object {
        @Volatile
        private var instance: StatusDatabase? = null
        private val LOCK = Any()
        val context = App.getContext()
        operator fun invoke() = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase().also {
                instance = it
            }
        }



        private fun buildDatabase() = Room.databaseBuilder(
            context,
            StatusDatabase::class.java,
            "status_database"
        ).build()
    }
}