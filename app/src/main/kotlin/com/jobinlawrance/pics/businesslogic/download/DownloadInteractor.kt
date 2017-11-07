package com.jobinlawrance.pics.businesslogic.download

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

        val serviceHandler = object : IServiceHandler {
            override fun bind(serviceConnection: ServiceConnection) {
                val intent = Intent(context, DownloadServiceImpl::class.java)
                context.startService(intent)
                context.bindService(intent, serviceConnection, 0)
            }

            override fun unBind(serviceConnection: ServiceConnection) {
                context.unbindService(serviceConnection)
            }
        }

        val serviceSubject = PublishSubject.create<DownloadService>()

        val serviceConnection = object : ServiceConnection {
            override fun onServiceDisconnected(p0: ComponentName?) {
                serviceSubject.onComplete()
            }

            override fun onServiceConnected(p0: ComponentName?, iBinder: IBinder?) {
                val downloadBinder = iBinder as DownloadServiceImpl.DownloadBinder
                val downloadService = downloadBinder.service as DownloadService
                if (serviceSubject.hasObservers())
                    serviceSubject.onNext(downloadService)
            }
        }

        Timber.d("Starting the handler + ${serviceConnection.hashCode()}")
        serviceHandler.bind(serviceConnection) //start the connection

        return serviceSubject.doOnDispose {
            Timber.w("Subscription is being disposed + ${serviceConnection.hashCode()}")
            serviceHandler.unBind(serviceConnection)
        }
    }

    interface IServiceHandler {
        fun bind(serviceConnection: ServiceConnection)
        fun unBind(serviceConnection: ServiceConnection)
    }
}
