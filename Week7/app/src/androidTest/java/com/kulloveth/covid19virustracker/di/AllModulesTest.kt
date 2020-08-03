package com.kulloveth.covid19virustracker.di

import androidx.test.core.app.ApplicationProvider
import com.kulloveth.covid19virustracker.di.provideNetwork
import com.kulloveth.covid19virustracker.di.provideRepository
import com.kulloveth.covid19virustracker.di.provideRoomDatabase
import com.kulloveth.covid19virustracker.di.provideViewModel
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

@RunWith(JUnit4::class)
class AllModulesTest : KoinTest {


    @Test
    fun testKoinModules() {
        stopKoin()
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(
                listOf(
                    provideRoomDatabase,
                    provideRepository,
                    provideViewModel,
                    provideNetwork
                )
            )
        }.checkModules()
        stopKoin()
    }
}