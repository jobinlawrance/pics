package com.jobinlawrance.pics.ui.home

import android.accounts.NetworkErrorException
import com.jobinlawrance.pics.utils.plusAssign
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by jobinlawrance on 7/9/17.
 */
class HomePresenterImpl @Inject constructor(private val interactor: HomeContract.Interactor) : HomeContract.Presenter() {

    val disposables = CompositeDisposable()

    override fun bindIntents() {

        var isFirstPageLoaded = false
        val loadFirstSubject = PublishSubject.create<Boolean>()

        disposables += intent(HomeContract.View::loadingFirstPageIntent)
                .map { t -> loadFirstSubject.onNext(t) }
                .subscribe()

        disposables += intent(HomeContract.View::networkStateIntent)
                .doAfterNext { connected ->
                    if (!isFirstPageLoaded) {

                        loadFirstSubject.onNext(connected)

                    }
                }.subscribe()

        val loadFirstPage: Observable<PartialStateChanges> = loadFirstSubject
                .doOnNext { Timber.d("intent: Load First Page") }
                .flatMap {
                    if (it) {
                        isFirstPageLoaded = true
                        interactor.getPictures()
                    } else
                        Observable.just(PartialStateChanges.FirstPageError(NetworkErrorException("No Internet")))
                }

        val initialState = HomeViewState.Builder().firstPageLoading(true).build()

        val allIntentsObservable: Observable<PartialStateChanges> =
                loadFirstPage
                        .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(
                allIntentsObservable.scan(initialState, interactor::viewStateReducer).distinctUntilChanged(),
                HomeContract.View::render
        )
    }

    override fun unbindIntents() {
        super.unbindIntents()
        disposables.clear()
    }

}