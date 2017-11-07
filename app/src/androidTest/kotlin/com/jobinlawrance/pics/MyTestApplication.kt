package com.jobinlawrance.pics

import android.support.v4.app.Fragment
import com.jobinlawrance.pics.application.MyApplication
import com.jobinlawrance.pics.di.fragment.FragmentComponentBuilder
import javax.inject.Provider

/**
 * Created by jobinlawrance on 20/9/17.
 */
class MyTestApplication : MyApplication() {

    override fun onCreate() {
        super.onCreate()
        println("Using Test application -------------------------------------------")
    }

    fun putFragmentComponentBuilder(builder: FragmentComponentBuilder<*, *>, cls: Class<out Fragment>) {

        val customFragmentComponentBuilders = HashMap<Class<out Fragment>, Provider<FragmentComponentBuilder<*, *>>>(this.fragmentComponentBuilders)

        customFragmentComponentBuilders.put(cls, object : Provider<FragmentComponentBuilder<*, *>> {
            override fun get(): FragmentComponentBuilder<*, *> = builder
        })

        this.fragmentComponentBuilders = customFragmentComponentBuilders
    }

}