package com.jobinlawrance.pics.di.fragment

import android.support.v4.app.Fragment
import dagger.Module
import dagger.Provides

/**
 * Created by jobinlawrance on 22/9/17.
 */
@Module
abstract class FragmentModule<out T : Fragment>(protected val fragment: T) {

    @Provides
    @FragmentScope
    fun provideFragment(): T = fragment

}