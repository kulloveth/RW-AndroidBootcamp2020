package com.kulloveth.covid19virustracker.ui.news

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.kulloveth.covid19virustracker.data.Repository
import com.kulloveth.covid19virustracker.data.db.NewsEntity
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.timeout
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class NewsViewModelTest {
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var repository: Repository
    private lateinit var newsObserver: Observer<List<NewsEntity>>
    private val newsEntity = NewsEntity(
        id = 1,
        title = "Trump attempts shift in tone on gloomy election polls",
        description = "Mishandling of the response to Covid-19 has left US president trailing Democratic rival Biden",
        url = "https://www.ft.com/content/bb58a24a-c22f-44a4-89a7-61d5385c8728",
        urlToImage = "https:www.trump.img/trump.jpg"
    )
    val result = listOf(newsEntity)


    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(mainThreadSurrogate)
        repository = mock()
        newsViewModel =
            NewsViewModel(
                repository
            )
        newsObserver = mock()
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }


    @Test
    fun `when News is queried from database`() = runBlocking {
        whenever(repository.fetchNewsFromRoom()).thenReturn(result)
        newsViewModel.getNewCovidNews().observeForever(newsObserver)
        delay(30)
        verify(repository).fetchNewsFromRoom()
        verify(newsObserver, timeout(50)).onChanged(result)
    }


}