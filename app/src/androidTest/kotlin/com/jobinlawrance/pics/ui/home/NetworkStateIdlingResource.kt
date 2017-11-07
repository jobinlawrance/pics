package com.jobinlawrance.pics.ui.home

import android.support.test.espresso.IdlingResource

/**
 * Created by jobinlawrance on 27/9/17.
 */
class NetworkStateIdlingResource(val homeFragment: HomeFragment) : IdlingResource {

    lateinit var resourceCallback: IdlingResource.ResourceCallback

    override fun getName(): String = "NetworkIdlingResource"

    override fun isIdleNow(): Boolean {
        val idle = homeFragment.isNetworkReconnected().get()
        if (idle)
            resourceCallback.onTransitionToIdle()

        return idle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        this.resourceCallback = callback
    }
}