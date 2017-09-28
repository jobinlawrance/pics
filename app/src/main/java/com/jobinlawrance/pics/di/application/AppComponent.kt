package com.jobinlawrance.pics.di.application

import com.jobinlawrance.pics.application.MyApplication
import com.jobinlawrance.pics.di.fragment.FragmentBindingModule
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by jobinlawrance on 5/9/17.
 */
@Singleton
@Component(modules = arrayOf(NetModule::class, FragmentBindingModule::class))
interface AppComponent {

    fun inject(myApplication: MyApplication)

    fun getRetrofit(): Retrofit
}