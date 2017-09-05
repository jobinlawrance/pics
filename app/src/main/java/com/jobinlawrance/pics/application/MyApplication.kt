package com.jobinlawrance.pics.application

import android.app.Application
import com.facebook.stetho.Stetho
import com.jobinlawrance.pics.BuildConfig
import com.jobinlawrance.pics.R
import timber.log.Timber

/**
 * Created by jobinlawrance on 5/9/17.
 */
class MyApplication : Application() {

    private lateinit var appComponent: AppComponent


    private val baseUrl = "https://api.unsplash.com/"
    private lateinit var headers: HashMap<String, String>

    companion object {
        private lateinit var applicationInstance: MyApplication
        @JvmStatic
        fun getInstance() = applicationInstance
    }

    override fun onCreate() {
        super.onCreate()
        applicationInstance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        } else {
            Timber.plant(ReleaseTree())
        }

        headers = hashMapOf(Pair("Authorization", "Client-ID ${getString(R.string.unsplash_id)}"))

        appComponent = DaggerAppComponent.builder()
                .netModule(NetModule(baseUrl, headers))
                .build()
    }

    fun getAppComponent() = appComponent
}