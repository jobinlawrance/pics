package com.jobinlawrance.pics.di.fragment

import android.support.v4.app.Fragment

/**
 * Created by jobinlawrance on 22/9/17.
 */
interface HasFragmentSubcomponentBuilders {
    fun getFragmentComponentBuilder(fragmentClass: Class<out Fragment>): FragmentComponentBuilder<*, *>
}