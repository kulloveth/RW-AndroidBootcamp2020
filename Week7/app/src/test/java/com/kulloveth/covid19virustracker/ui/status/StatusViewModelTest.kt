package com.kulloveth.covid19virustracker.ui.status

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.kulloveth.covid19virustracker.data.Repository
import com.kulloveth.covid19virustracker.data.db.StatusEntity
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
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class StatusViewModelTest {
    @Mock
    private lateinit var statusViewModel: StatusViewModel
    private lateinit var repository: Repository
    private lateinit var statusObserver: Observer<List<StatusEntity>>
    private val statusEntity = StatusEntity(
        country = "Algeria",
        country_abbreviation = "DZ",
        total_cases = "6,442",
        new_cases = "0",
        total_deaths = "529",
        new_deaths = "0",
        total_recovered = "3,158",
        active_cases = "2,755",
        serious_critical = "22",
        cases_per_mill_pop = "147.0",
        flag = "https://www.worldometers.info/img/flags/ag-flag.gif"
    )
    val result = listOf(statusEntity)


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
        statusViewModel =
            StatusViewModel(
                repository
            )
        statusObserver = mock()
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList = Mockito.mock(PagedList::class.java) as PagedList<T>
        Mockito.`when`(pagedList.get(ArgumentMatchers.anyInt())).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        Mockito.`when`(pagedList.size).thenReturn(list.size)
        return pagedList
    }

    @FlowPreview
    @Test
    fun `when status is queried from database`() = runBlocking {
        whenever(repository.fetchStatusFromRoom()).thenReturn(result)
        statusViewModel.getNewStatus().observeForever(statusObserver)
        delay(30)
        verify(repository).fetchStatusFromRoom()
        verify(statusObserver, timeout(50)).onChanged(result)
    }
}