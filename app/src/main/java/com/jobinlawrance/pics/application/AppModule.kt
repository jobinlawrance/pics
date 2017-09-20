package com.jobinlawrance.pics.application

import com.jobinlawrance.pics.home.dagger.HomeComponent
import dagger.Module

/**
 * Created by jobinlawrance on 20/9/17.
 */

@Module(subcomponents = arrayOf(HomeComponent::class))
class AppModule