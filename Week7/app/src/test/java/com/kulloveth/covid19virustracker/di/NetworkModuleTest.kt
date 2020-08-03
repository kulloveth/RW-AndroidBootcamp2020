package com.kulloveth.covid19virustracker.di

import com.kulloveth.covid19virustracker.api.NewsApiService
import com.kulloveth.covid19virustracker.api.StatusApiService
import junit.framework.Assert.assertNotNull
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject

class NetworkModuleTest : KoinTest {
    val newsApi: NewsApiService by inject()
    val statusApi: StatusApiService by inject()
    val okHttpClient: OkHttpClient by inject()
    val newsBaseUrl: String by lazy { get(named("NEWS_BASE_URL")) as String }
    val statusBaseUrl: String by lazy { get(named("STATUS_BASE_URL")) as String }

    @Before
    fun setup() {
        startKoin {
            modules(listOf(provideNetwork))
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `Test News Base Url Created`() {
        assert(newsBaseUrl == "https://newsapi.org/")
    }

    @Test
    fun `Test Status Base Url Created`() {
        assert(statusBaseUrl == "https://corona-virus-stats.herokuapp.com/api/")
    }

    @Test
    fun `Test OkHttp Created`() {
        assertNotNull(okHttpClient)
    }

    @Test
    fun `Test News Api  Created`() {
        assertNotNull(statusApi)
    }

    @Test
    fun `Test Status API  Created`() {
        assertNotNull(newsApi)
    }

}