package com.jobinlawrance.pics.home

import com.jobinlawrance.pics.application.AppComponent
import com.jobinlawrance.pics.application.NetModule
import com.jobinlawrance.pics.home.dagger.DaggerHomeComponent
import com.jobinlawrance.pics.retrofit.mock.MockPhotoResponses
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import it.cosenonjaviste.daggermock.DaggerMockRule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*

/**
 * Created by jobinlawrance on 8/9/17.
 */
class HomePresenterImplTest {

    companion object {
        val mockWebServer = MockWebServer()

        @JvmStatic
        @BeforeClass
        fun init() {
            // Tell RxAndroid to not use android main ui thread scheduler
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

            println("Before class")
            mockWebServer.start()
        }

        @JvmStatic
        @AfterClass
        fun tearDown() {
            RxAndroidPlugins.reset()
            mockWebServer.shutdown()
        }
    }

    lateinit var appComponent: AppComponent

    @get:Rule
    val daggerMock = DaggerMockRule<AppComponent>(AppComponent::class.java, NetModule(mockWebServer.url("").toString(), HashMap())).set {
        println("Inside dagger mock")
        appComponent = it
    }

    @Before
    fun beforeEachTest() {

    }

    @After
    fun afterEachTest() {

    }

    @Test
    fun testDaggerMock() {
        println("Url is ${appComponent.getRetrofit().baseUrl()}")
    }

    @Test
    fun testLoadingPage() {
        mockWebServer.enqueue(MockResponse().setBody(MockPhotoResponses.jsonString))

        val presenter =
                DaggerHomeComponent.builder()
                        .appComponent(appComponent)
                        .build()
                        .providePresenter()

        val robot = HomeViewRobot(presenter)

        //
        // We are ready, so let's start: fire an intent
        //
        robot.fireLoadFirstPageIntent()

        //
        // we expect that 2 view.render() events happened with the following HomeViewState:
        // 1. show loading indicator
        // 2. show the items with the first page

        val loadingFirstPageState = HomeViewState.Builder().firstPageLoading(true).build()
        val firstPage = HomeViewState.Builder().data(MockPhotoResponses.asList()).build()

        // Check if as expected
        robot.assertViewStateRendered(loadingFirstPageState, firstPage)
    }

}