package com.jobinlawrance.pics.home.dagger

import com.jobinlawrance.pics.di.fragment.FragmentModule
import com.jobinlawrance.pics.home.HomeContract
import com.jobinlawrance.pics.home.HomeFragment
import com.jobinlawrance.pics.home.HomeInteractorImpl
import com.jobinlawrance.pics.home.HomePresenterImpl
import com.jobinlawrance.pics.retrofit.services.PhotoService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by jobinlawrance on 7/9/17.
 */

@Module
class HomeModule(homeFragment: HomeFragment) : FragmentModule<HomeFragment>(homeFragment) {

    @Provides
    fun provideInteractor(retrofit: Retrofit): HomeContract.Interactor
            = HomeInteractorImpl(retrofit.create(PhotoService::class.java))

    @Provides
    fun providePresenter(interactor: HomeContract.Interactor): HomeContract.Presenter
            = HomePresenterImpl(interactor)
}