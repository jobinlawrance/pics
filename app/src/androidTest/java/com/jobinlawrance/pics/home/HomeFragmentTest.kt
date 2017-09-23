package com.jobinlawrance.pics.home

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.jobinlawrance.pics.MainActivity
import com.jobinlawrance.pics.MyTestApplication
import com.jobinlawrance.pics.R
import com.jobinlawrance.pics.home.dagger.HomeComponent
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit


/**
 * Created by jobinlawrance on 20/9/17.
 */
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @get:Rule
    var activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java, true, false)

    var builder: HomeComponent.Builder = mock()

    var presenterRobot = HomePresenterRobot()

    private val homeFragmentComponent = object : HomeComponent {
        override fun injectMembers(instance: HomeFragment?) {
            instance?.presenter = presenterRobot
        }
    }

    @Before
    fun setUp() {
        //mocking the presenter in DI graph
        whenever(builder.build()).thenReturn(homeFragmentComponent)
        whenever(builder.fragmentModule(any())).thenReturn(builder)

        val testApplication = InstrumentationRegistry.getTargetContext().applicationContext as MyTestApplication
        testApplication.putFragmentComponentBuilder(builder, HomeFragment::class.java)

    }

    @Test
    @Throws(Exception::class)
    fun testLoadingProgressBar() {
        activityRule.launchActivity(Intent())
        activityRule.runOnUiThread(Runnable { presenterRobot.customRender(HomeViewState.Builder().firstPageLoading(true).build()) })
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
    }
}