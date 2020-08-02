package com.kulloveth.covid19virustracker.ui

import android.view.MenuItem
import androidx.core.view.size
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kulloveth.covid19virustracker.R
import org.hamcrest.CoreMatchers.allOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private lateinit var bottomNavigationView: BottomNavigationView
    private val menuContentId = arrayOf(R.id.statusFragment,R.id.newsFragment)
    private var menuStringContent = mutableMapOf<Int,String>()
    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp(){
        val activity = activityTestRule.activity
        bottomNavigationView = activity.findViewById(R.id.nav_view)
        val res = activity.resources
        menuStringContent = HashMap(menuContentId.size)
        (menuStringContent as HashMap<Int, String>)[R.id.statusFragment] = res.getString(R.string.status)
        (menuStringContent as HashMap<Int, String>)[R.id.newsFragment] = res.getString(R.string.news)
    }

    @Test
    fun checkThatBottomMenuItemsArePresent(){
        val menu = bottomNavigationView.menu
        assertNotNull(menu)
        assertEquals(menuContentId.size,menu.size)
    }

    @Test
    fun checkThatNewsMenuIsClicked(){
        val mockedListener = mock(BottomNavigationView.OnNavigationItemSelectedListener::class.java)
        bottomNavigationView.setOnNavigationItemSelectedListener(mockedListener)
        `when`(mockedListener.onNavigationItemSelected(ArgumentMatchers.any(MenuItem::class.java))).thenReturn(true)
        onView(allOf(withText(menuStringContent[R.id.newsFragment]),
        isDescendantOfA(withId(R.id.nav_view)), isDisplayed())).perform(click())
        verify(mockedListener, times(1)).onNavigationItemSelected(bottomNavigationView.menu.findItem(R.id.newsFragment))
        assertTrue(bottomNavigationView.menu.findItem(R.id.newsFragment).isChecked)
    }

}