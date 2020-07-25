package com.kulloveth.covid19virustracker.ui.status

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.ui.MainActivity
import com.kulloveth.covid19virustracker.ui.news.NewsAdapter
import com.kulloveth.covid19virustracker.ui.news.NewsViewHolder
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StatusFragmentTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun testThatAlgeriaIsVisibleWhenThirdPositionIsClicked() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItem<NewsViewHolder>(hasDescendant(withText("Algeria")),click()))
    }

    @Test
    fun testThatBelgiumIsVisibleWhenItsPositionIsClicked() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItem<NewsViewHolder>(hasDescendant(withText("Belgium")),click()))
    }
}