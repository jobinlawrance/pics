package com.jobinlawrance.pics.ui.home

import android.util.Pair
import android.view.View
import com.jobinlawrance.pics.data.retrofit.model.PhotoResponse
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import org.junit.Assert
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Created by jobinlawrance on 8/9/17.
 */
class HomeViewRobot(val presenter: HomeContract.Presenter) {
    private val loadFirstPageSubject = PublishSubject.create<Boolean>()
    private val loadNextPageSubject = PublishSubject.create<Boolean>()
    private val networkStateSubject = PublishSubject.create<Boolean>()

    private val renderEventSubject = ReplaySubject.create<HomeViewState>()
    private val renderEvents = CopyOnWriteArrayList<HomeViewState>()

    private val view = object : HomeContract.View {
        override fun render(viewState: HomeViewState) {
            renderEvents.add(viewState)
            renderEventSubject.onNext(viewState)
        }

        override fun loadingFirstPageIntent(): Observable<Boolean> = loadFirstPageSubject

        override fun loadNextPageIntent(): Observable<Boolean> = loadNextPageSubject

        override fun networkStateIntent(): Observable<Boolean> = networkStateSubject

        override fun openDetails(photoResponse: PhotoResponse, sharedElementsPair: Pair<View, String>) {}
    }

    init {
        presenter.attachView(view)
    }

    fun fireLoadFirstPageIntent() = loadFirstPageSubject.onNext(true)

    fun fireNetworkStateIntent(connected: Boolean) = networkStateSubject.onNext(connected)

    fun fireNextLoadingPageIntent() = loadNextPageSubject.onNext(true)

    fun assertViewStateRendered(vararg expectedHomeViewStates: HomeViewState) {

        if (expectedHomeViewStates == null) {
            throw NullPointerException("expectedHomeViewStates == null")
        }

        val eventsCount = expectedHomeViewStates.size

        // Wait for few seconds to ensure that no more render events have occurred
        // If a timeout exception is encountered it means we are waiting for a render viewState that is not going to happen
        // or something else went wrong in mosby  ¯\_(ツ)_/¯
        renderEventSubject.take(eventsCount.toLong())
                .timeout(10, TimeUnit.SECONDS)
                .onErrorResumeNext { t: Throwable ->
                    if (t is TimeoutException) {
                        Assert.fail("Expected $eventsCount but there were only ${renderEventSubject.values.size} events in total, "
                                + "which is less than expected : ${arrayToString(renderEventSubject.values)}")
                        Observable.empty<HomeViewState>()
                    } else {
                        Observable.error(t)
                    }

                }
                .blockingSubscribe()

        if (renderEventSubject.values.size > eventsCount) {
            Assert.fail("Expected to wait for "
                    + eventsCount
                    + ", but there were "
                    + renderEventSubject.values.size
                    + " Events in total, which is more than expected: "
                    + arrayToString(renderEventSubject.values))
        }

        Assert.assertEquals(Arrays.asList(*expectedHomeViewStates), renderEvents)
    }

    /**
     * Simple helper function to print the content of an array as a string
     */
    private fun arrayToString(array: Array<Any>): String {
        val buffer = StringBuffer()
        for (o in array) {
            buffer.append(o.toString())
            buffer.append("\n")
        }

        return buffer.toString()
    }
}