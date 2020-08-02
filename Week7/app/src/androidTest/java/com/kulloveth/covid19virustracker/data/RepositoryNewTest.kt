package com.kulloveth.covid19virustracker.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kulloveth.covid19virustracker.data.db.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryNewTest {
    private lateinit var covidDatabase: CovidDatabase
    private lateinit var statusDao: StatusDao
    private lateinit var newsDao: NewsDao
    private lateinit var repository: Repository
    private val newsEntity = NewsEntity(
        id = 1,
        title = "Trump attempts shift in tone on gloomy election polls",
        description = "Mishandling of the response to Covid-19 has left US president trailing Democratic rival Biden",
        url = "https://www.ft.com/content/bb58a24a-c22f-44a4-89a7-61d5385c8728",
        urlToImage = "https:www.trump.img/trump.jpg"
    )
    val result = listOf(newsEntity)
    val countryInfoEntity = CountryInfoEntity(1,"https://www.worldometers.info/img/flags/ag-flag.gif")
    private val statusEntity = StatusEntity(
        country = "Algeria",
        countryInfoEntity = countryInfoEntity,
        cases = 6442,
        todayCases = 4,
        deaths = 52,
        todayDeaths = 6,
        recovered =158,
        todayRecovered = 55,
        critical = 6,
        active = 2
    )
    val statusResult = listOf(statusEntity)

    @Before
    fun setUp() {
        covidDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CovidDatabase::class.java
        ).build()
        statusDao = covidDatabase.getStatusDao()
        newsDao = covidDatabase.getNewsDao()
        repository = Repository(statusDao, newsDao)
    }

    @After
    fun closeDb() {
        covidDatabase.close()
    }

    @Test
    fun fetchNews() = runBlocking {
        newsDao.insert(result)
        assertEquals(result, repository.fetchNewsFromRoom())
    }

    @Test
    fun fetchStatus() = runBlocking {
        statusDao.insert(statusResult)
        assertEquals(statusResult, repository.fetchStatusFromRoom())
    }
}