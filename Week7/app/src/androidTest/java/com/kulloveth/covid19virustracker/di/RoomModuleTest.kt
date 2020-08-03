package com.kulloveth.covid19virustracker.di

import androidx.test.core.app.ApplicationProvider
import com.kulloveth.covid19virustracker.data.db.CovidDatabase
import com.kulloveth.covid19virustracker.data.db.NewsDao
import com.kulloveth.covid19virustracker.data.db.StatusDao
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class RoomModuleTest:KoinTest {

    val db:CovidDatabase by inject()
    val statusDao: StatusDao by inject()
    val newsDao:NewsDao by inject()
    @Before
    fun setup() {
        stopKoin()
        startKoin {
        androidContext(ApplicationProvider.getApplicationContext())
        modules(
            listOf(
                provideRoomDatabase))
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testDatabaseIsCreated(){
     assertNotNull(db)
    }

    @Test
    fun testStatusDaoIsCreated(){
        assertNotNull(statusDao)
    }

    @Test
    fun testNewsDaoIsCreated(){
        assertNotNull(newsDao)
    }
}