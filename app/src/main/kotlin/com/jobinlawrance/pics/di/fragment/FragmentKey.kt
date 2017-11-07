package com.jobinlawrance.pics.di.fragment


import android.support.v4.app.Fragment

import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Created by jobinlawrance on 22/9/17.
 */
@MapKey
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class FragmentKey(val value: KClass<out Fragment>)
