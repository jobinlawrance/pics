package com.jobinlawrance.pics.application

import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by jobinlawrance on 5/9/17.
 */
@Singleton
@Component(modules = arrayOf(NetModule::class))
interface AppComponent {
    fun getRetrofit(): Retrofit
}