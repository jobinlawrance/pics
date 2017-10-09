package com.jobinlawrance.pics

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

/**
 * Created by jobinlawrance on 6/10/17.
 * ref - https://stackoverflow.com/a/43047412/6448823
 */
class DownloadInteractor(activityContext: Context) {

    var context: Context

    init {
        context = activityContext.applicationContext

    }

    fun getService(): Observable<DownloadService> {

        return Observable.create<DownloadServiceImpl.Handler>({
            if (!it.isDisposed) {
                val handler = object : DownloadServiceImpl.Handler {
                    override fun onStart(serviceConnection: ServiceConnection) {

                        val intent = Intent(context, DownloadServiceImpl::class.java)
                        context.startService(intent)
                        context.bindService(intent, serviceConnection, 0)
                    }

                    override fun onStop(serviceConnection: ServiceConnection) {
                        context.unbindService(serviceConnection)
                    }
                }

                it.onNext(handler)
                it.onComplete()
            }
        }).flatMap { handler ->
            Timber.d("Download Handler is created")
            val serviceSubject = PublishSubject.create<DownloadService>()

            val serviceConnection = object : ServiceConnection {
                override fun onServiceDisconnected(p0: ComponentName?) {
                    serviceSubject.onComplete()
                }

                override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                    val downloadBinder = p1 as DownloadServiceImpl.DownloadBinder
                    val downloadService = downloadBinder.service as DownloadService
                    if (serviceSubject.hasObservers())
                        serviceSubject.onNext(downloadService)
                }
            }

            Timber.d("Starting the handler + ${serviceConnection.hashCode()}")
            handler.onStart(serviceConnection) //start the connection

            serviceSubject.doOnDispose {
                Timber.w("Subscription is being disposed + ${serviceConnection.hashCode()}")
                handler.onStop(serviceConnection)
            }
        }
    }
}
