package com.jobinlawrance.pics.ui.home

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingPolicies
import android.support.test.espresso.IdlingResource
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import com.jobinlawrance.pics.FragmentTestRule
import com.jobinlawrance.pics.MyTestApplication
import com.jobinlawrance.pics.R
import com.jobinlawrance.pics.data.mock.MockPhotoResponses
import com.jobinlawrance.pics.ui.home.dagger.HomeComponent
import com.jobinlawrance.pics.utils.inputStreamToString
import com.linkedin.android.testbutler.TestButler
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import java.util.concurrent.TimeUnit

/**
 * Created by jobinlawrance on 26/9/17.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeFragmentIntentTest {
    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @get:Rule
    var fragmentRule = FragmentTestRule<HomeFragment>(HomeFragment::class.java)
//    var fragmentRule = ActivityTestRule<HomeActivity>(HomeActivity::class.java,true,false)

    var builder: HomeComponent.Builder = mock()
    var homeInteractor: HomeInteractorImpl = mock()

    var idlingResource: IdlingResource? = null

    private val homeFragmentComponent = object : HomeComponent {
        override fun injectMembers(instance: HomeFragment?) {
            instance?.presenter = HomePresenterImpl(homeInteractor)
        }
    }

    @Before
    fun setUp() {
        //mocking the presenter in DI graph
        whenever(builder.build()).thenReturn(homeFragmentComponent)
        whenever(builder.fragmentModule(any())).thenReturn(builder)

        //mocking the homeInteractor
        val inputStream = this::class.java.classLoader.getResourceAsStream("photo-responses.json")
        val mockJsonString = inputStreamToString(inputStream)
        whenever(homeInteractor.getPictures()).thenReturn(Observable.just(PartialStateChanges.FirstPageLoaded(MockPhotoResponses.asList(mockJsonString!!))))
        whenever(homeInteractor.viewStateReducer(any(), any())).thenCallRealMethod()

        val testApplication = InstrumentationRegistry.getTargetContext().applicationContext as MyTestApplication
        testApplication.putFragmentComponentBuilder(builder, HomeFragment::class.java)

    }

    @Test
    @Throws(Exception::class)
    fun testNetworkState() {
        //Emulate disabled network while starting the app
        TestButler.setWifiState(false)

        //Launch the app
        fragmentRule.launchActivity(null)

        onView(withId(R.id.networkImage)).check(matches(isDisplayed()))

        //register idleResource so that espresso waits till network is reconnected
        idlingResource = NetworkStateIdlingResource(fragmentRule.getFragment())
        Espresso.registerIdlingResources(idlingResource)
        IdlingPolicies.setIdlingResourceTimeout(2, TimeUnit.MINUTES) //default is 1 min

        //Enable the network
        TestButler.setWifiState(true)

        onView(withId(R.id.networkImage)).check(matches(not(isDisplayed())))

        Espresso.unregisterIdlingResources(idlingResource)
    }

}