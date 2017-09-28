package com.jobinlawrance.pics.di.fragment

import com.jobinlawrance.pics.ui.home.HomeFragment
import com.jobinlawrance.pics.ui.home.dagger.HomeComponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by jobinlawrance on 20/9/17.
 */

@Module(subcomponents = arrayOf(HomeComponent::class))
abstract class FragmentBindingModule {

    @Binds
    @IntoMap
    @FragmentKey(HomeFragment::class)
    abstract fun homeFragmentComponentBuilder(builder: HomeComponent.Builder): FragmentComponentBuilder<*, *>

}