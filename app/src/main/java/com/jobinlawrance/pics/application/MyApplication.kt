package com.jobinlawrance.pics.application

import android.app.Application
import android.support.v4.app.Fragment
import com.facebook.stetho.Stetho
import com.jobinlawrance.pics.BuildConfig
import com.jobinlawrance.pics.R
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by jobinlawrance on 5/9/17.
 */
class MyApplication : Application(), HasSupportFragmentInjector {

    private lateinit var appComponent: AppComponent
    @Inject
    lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>

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

        appComponent.inject(this)
    }

    fun getAppComponent() = appComponent

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingFragmentInjector
}