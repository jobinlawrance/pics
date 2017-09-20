package com.jobinlawrance.pics.home.dagger

import com.jobinlawrance.pics.home.HomeContract
import com.jobinlawrance.pics.home.HomeFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by jobinlawrance on 7/9/17.
 */

@Subcomponent(modules = arrayOf(HomeModule::class))
interface HomeComponent : AndroidInjector<HomeFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<HomeFragment>()

    fun providePresenter(): HomeContract.Presenter
}