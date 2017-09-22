package com.jobinlawrance.pics.di.fragment

/**
 * Created by jobinlawrance on 22/9/17.
 */
interface FragmentComponentBuilder<M : FragmentModule<*>, C : FragmentComponent<*>> {
    fun fragmentModule(fragmentModule: M): FragmentComponentBuilder<M, C>
    fun build(): C
}