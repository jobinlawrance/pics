package com.jobinlawrance.pics.application

import android.app.Application
import com.jobinlawrance.pics.BuildConfig
import timber.log.Timber

/**
 * Created by jobinlawrance on 5/9/17.
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }
}