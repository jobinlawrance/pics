package com.jobinlawrance.pics.application

import android.app.Application
import android.os.StrictMode
import android.support.v4.app.Fragment
import com.facebook.stetho.Stetho
import com.jobinlawrance.pics.BuildConfig
import com.jobinlawrance.pics.R
import com.jobinlawrance.pics.di.application.AppComponent
import com.jobinlawrance.pics.di.application.DaggerAppComponent
import com.jobinlawrance.pics.di.application.NetModule
import com.jobinlawrance.pics.di.fragment.FragmentComponentBuilder
import com.jobinlawrance.pics.di.fragment.HasFragmentSubcomponentBuilders
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by jobinlawrance on 5/9/17.
 */
open class MyApplication : Application(), HasFragmentSubcomponentBuilders {

    private lateinit var appComponent: AppComponent

    @Inject
    lateinit var fragmentComponentBuilders: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<FragmentComponentBuilder<*, *>>>

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

        //lazy hack to avoid FileUriException TODO - use content:// instead of file://
        val vmPolicyBuilder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(vmPolicyBuilder.build())

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

    override fun getFragmentComponentBuilder(fragmentClass: Class<out Fragment>): FragmentComponentBuilder<*, *>
            = fragmentComponentBuilders.get(fragmentClass)!!.get()

}