package com.example.endclothing

import androidx.lifecycle.MediatorLiveData
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.endclothing.utils.LoadState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock

@RunWith(AndroidJUnit4ClassRunner::class)
class MainProductFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Mock
    val fakeLoadingState = MediatorLiveData<LoadState>()

    @Test
    fun test_mainActivityIsDisplayed() {
        emitLoadingState(LoadState.LOADING)
        Espresso.onView(withId(R.id.main)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isFragmentProductListVisible() {
        emitLoadingState(LoadState.LOADING)
        Espresso.onView(withId(R.id.Main_Product_fragment_layout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isRecyclerviewVisible() {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    @Test
    fun test_isProgressBarNotShowingWhenSuccess() {
        emitLoadingState(LoadState.SUCCESS)
        Espresso.onView(withId(R.id.progressbar)).check(
            ViewAssertions.matches(
                ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)
            )
        )
    }

    private fun emitLoadingState(loadingState: LoadState) {
        fakeLoadingState.postValue(loadingState)
    }
}