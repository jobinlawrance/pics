package com.jobinlawrance.pics.application

import dagger.Component
import dagger.android.AndroidInjectionModule
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by jobinlawrance on 5/9/17.
 */
@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class,
        AppModule::class,
        NetModule::class,
        PicsFragmentInjectorModule::class))
interface AppComponent {

    fun inject(myApplication: MyApplication)

    fun getRetrofit(): Retrofit
}