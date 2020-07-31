package com.kulloveth.covid19virustracker.ui.status

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.kulloveth.covid19virustracker.data.Repository
import com.kulloveth.covid19virustracker.data.db.CountryInfoEntity
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
    private lateinit var statusViewModel: StatusViewModel
    private lateinit var repository: Repository
    private lateinit var statusObserver: Observer<List<StatusEntity>>
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
    val result = listOf(statusEntity)


    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    //setups dependencies required for test
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