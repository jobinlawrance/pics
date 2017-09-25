package com.jobinlawrance.pics.home

import android.accounts.NetworkErrorException
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.runner.AndroidJUnit4
import com.jobinlawrance.pics.FragmentTestRule
import com.jobinlawrance.pics.MyTestApplication
import com.jobinlawrance.pics.R
import com.jobinlawrance.pics.home.dagger.HomeComponent
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import java.net.SocketTimeoutException


/**
 * Created by jobinlawrance on 20/9/17.
 */
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @get:Rule
    var fragmentRule = FragmentTestRule<HomeFragment>(HomeFragment::class.java)

    var builder: HomeComponent.Builder = mock()

    lateinit var presenterRobot: HomePresenterRobot

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

        presenterRobot = HomePresenterRobot()
    }

    @Test
    @Throws(Exception::class)
    fun render_firstPageLoading() {
        fragmentRule.launchActivity(null)
        fragmentRule.runOnUiThread(Runnable { presenterRobot.customRender(HomeViewState.Builder().firstPageLoading(true).build()) })
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
    }

    @Test
    @Throws(Exception::class)
    fun render_firstPageTimeOutException() {
        fragmentRule.launchActivity(Intent())
        fragmentRule.runOnUiThread({
            presenterRobot.customRender(HomeViewState.Builder().firstPageError(SocketTimeoutException()).build())
        })

        //assert that progressBar is hidden
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        //check for the timeout image drawable
        onView(withId(R.id.timeoutImageView)).check(matches(isDisplayed()))
    }

    @Test
    @Throws(Exception::class)
    fun render_firstPageNetworkException() {
        fragmentRule.launchActivity(Intent())
        fragmentRule.runOnUiThread({
            presenterRobot.customRender(HomeViewState.Builder().firstPageError(NetworkErrorException()).build())
        })
        //assert that progressBar is hidden
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        //check for the network image drawable
        onView(withId(R.id.networkImage)).check(matches(isDisplayed()))
    }
}