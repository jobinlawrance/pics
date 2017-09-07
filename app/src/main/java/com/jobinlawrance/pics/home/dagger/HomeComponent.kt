package com.jobinlawrance.pics.home.dagger

import com.jobinlawrance.pics.application.AppComponent
import com.jobinlawrance.pics.extras.ActivityScope
import com.jobinlawrance.pics.home.HomeContract
import dagger.Component

/**
 * Created by jobinlawrance on 7/9/17.
 */

@ActivityScope
@Component(modules = arrayOf(HomeModule::class), dependencies = arrayOf(AppComponent::class))
interface HomeComponent {
    fun providePresenter(): HomeContract.Presenter
}