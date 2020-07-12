package com.kulloveth.covid19virustracker.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kulloveth.covid19virustracker.App
import com.kulloveth.covid19virustracker.Injection
import com.kulloveth.covid19virustracker.model.CountryStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Database(entities = [CountryStatus::class], version = 1, exportSchema = false)
abstract class StatusDatabase : RoomDatabase() {
    abstract fun getStatusDao(): StatusDao

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


        //build database
        private fun buildDatabase():StatusDatabase{
            return Room.databaseBuilder(
                context,
                StatusDatabase::class.java,
                "status_database"
                //insert data once when room is created
            ).addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(context = Dispatchers.IO).launch {
                        //defer status result
                        val status = async { Injection.provideRepository().fetchStatus() }

                        //insert status to database
                        Injection.db.getStatusDao().insert(status.await())
                    }
                }
            }).build()
        }
    }
}