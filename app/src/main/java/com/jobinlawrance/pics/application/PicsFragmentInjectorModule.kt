package com.jobinlawrance.pics.application

import android.support.v4.app.Fragment
import com.jobinlawrance.pics.home.HomeFragment
import com.jobinlawrance.pics.home.dagger.HomeComponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

/**
 * Created by jobinlawrance on 20/9/17.
 */

@Module
abstract class PicsFragmentInjectorModule {

    @Binds
    @IntoMap
    @FragmentKey(HomeFragment::class)
    abstract fun bindHomeFragmentInjectorFactory(builder: HomeComponent.Builder): AndroidInjector.Factory<out Fragment>

}