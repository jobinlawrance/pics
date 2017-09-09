package com.jobinlawrance.pics.home

import com.jobinlawrance.pics.application.DaggerAppComponent
import com.jobinlawrance.pics.application.NetModule
import com.jobinlawrance.pics.home.dagger.DaggerHomeComponent
import com.jobinlawrance.pics.retrofit.mock.MockPhotoResponses
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import java.net.ConnectException

/**
 * Created by jobinlawrance on 8/9/17.
 */
class HomePresenterImplTest {
    lateinit var mockWebServer: MockWebServer
    lateinit var baseUrl: String

    companion object {

        @JvmStatic
        @BeforeClass
        fun init() {
            // Tell RxAndroid to not use android main ui thread scheduler
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        }

        @JvmStatic
        @AfterClass
        fun tearDown() {
            RxAndroidPlugins.reset()
        }
    }

//    lateinit var appComponent: AppComponent

//    @get:Rule
//    val daggerMock = DaggerMockRule<AppComponent>(AppComponent::class.java, NetModule(baseUrl, HashMap())).set {
//        println("Inside dagger mock")
//        appComponent = it
//    }

    @Before
    fun beforeEachTest() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        baseUrl = mockWebServer.url("").toString()
    }

    @After
    fun afterEachTest() {
        mockWebServer.shutdown()
    }

//    @Test
//    fun testDaggerMock() {
//        println("Url is ${appComponent.getRetrofit().baseUrl()}")
//    }

    @Test
    fun testLoadingPage() {
        mockWebServer.enqueue(MockResponse().setBody(MockPhotoResponses.jsonString))

        val presenter =
                DaggerHomeComponent.builder()
                        .appComponent(DaggerAppComponent.builder().netModule(NetModule(baseUrl.toString(), HashMap())).build())
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

    @Test
    fun testLoadingFirstFailsWithConnectionError() {
        //
        // Prepare mock server to deliver mock response on incoming http request
        //
        mockWebServer.shutdown()

        val presenter =
                DaggerHomeComponent.builder()
                        .appComponent(DaggerAppComponent.builder().netModule(NetModule(baseUrl, HashMap())).build())
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
        // 2. show error indicator
        //
        val loadingFirstPageState = HomeViewState.Builder().firstPageLoading(true).build()
        val firstPageError =
                HomeViewState.Builder()
//                        .firstPageError(retrofit2.adapter.rxjava2.HttpException(
//                                Response.error<String>(ResponseBody.create(null, ""),
//                                        okhttp3.Response.Builder()
//                                                .code(404)
//                                                .protocol(Protocol.HTTP_1_1)
//                                                .request(Request.Builder().url("http://localhost/").build())
//                                                .message("Client Error")
//                                                .build())))
                        .firstPageError(ConnectException())
                        .build()

        // Check if as expected
        robot.assertViewStateRendered(loadingFirstPageState, firstPageError)
    }

}