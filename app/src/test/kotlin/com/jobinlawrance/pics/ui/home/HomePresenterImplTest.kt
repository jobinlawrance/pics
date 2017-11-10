package com.jobinlawrance.pics.ui.home

import android.accounts.NetworkErrorException
import com.jobinlawrance.pics.data.retrofit.services.PhotoService
import com.jobinlawrance.pics.di.application.DaggerAppComponent
import com.jobinlawrance.pics.di.application.NetModule
import com.jobinlawrance.pics.utils.inputStreamToString
import com.jobinlawrance.pics.utils.photoResponsesFromString
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
    lateinit var presenter: HomePresenterImpl

    companion object {

        var mockPhotoResponseJson: String? = null

        @JvmStatic
        @BeforeClass
        fun init() {
            // Tell RxAndroid to not use android main ui thread scheduler
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

            val inputStream = this::class.java.classLoader.getResourceAsStream("photo-responses.json")
            mockPhotoResponseJson = inputStreamToString(inputStream)
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

        val mockRetrofit =
                DaggerAppComponent.builder()
                        .netModule(NetModule(baseUrl, HashMap()))
                        .build()
                        .getRetrofit()
        val mockPhotoService = mockRetrofit.create(PhotoService::class.java)

        presenter = HomePresenterImpl(HomeInteractorImpl(mockPhotoService))
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
        mockWebServer.enqueue(MockResponse().setBody(mockPhotoResponseJson))


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
        val firstPage = HomeViewState.Builder().data(photoResponsesFromString(mockPhotoResponseJson!!)).build()

        // Check if as expected
        robot.assertViewStateRendered(loadingFirstPageState, firstPage)
    }

    @Test
    fun testLoadingFirstFailsWithConnectionError() {
        //
        // Prepare mock server to deliver mock response on incoming http request
        //
        mockWebServer.shutdown()

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

    @Test
    fun testFirstPageNetworkState() {

        mockWebServer.enqueue(MockResponse().setBody(mockPhotoResponseJson))

        val robot = HomeViewRobot(presenter)

        //We are mocking a no internet state
        robot.fireNetworkStateIntent(false)

        //Then simulating an active state
        robot.fireNetworkStateIntent(true)

        val fistPageNetworkError = HomeViewState.Builder().firstPageError(NetworkErrorException()).build()

        val firstPageLoading = HomeViewState.Builder().firstPageLoading(true).build()

        val firstPage = HomeViewState.Builder().data(photoResponsesFromString(mockPhotoResponseJson!!)).build()

        // Asserting the sequence in order
        // 1. First loading
        // 2. Then network fail
        // 3. Then network comes back and loading comes back again
        // 4. Then mock successful fetching of data from api
        robot.assertViewStateRendered(firstPageLoading, fistPageNetworkError, firstPageLoading, firstPage)
    }

    @Test
    fun testFirstPageNetworkStateChangePostFirstLoad() {
        mockWebServer.enqueue(MockResponse().setBody(mockPhotoResponseJson))

        val robot = HomeViewRobot(presenter)


        //Simulating an active state
        robot.fireNetworkStateIntent(true)

        //Then simulating a non active internet state
        robot.fireNetworkStateIntent(false)

        val firstPageLoading = HomeViewState.Builder().firstPageLoading(true).build()

        val firstPage = HomeViewState.Builder().data(photoResponsesFromString(mockPhotoResponseJson!!)).build()

        robot.assertViewStateRendered(firstPageLoading, firstPage)
    }

}