package com.jobinlawrance.pics.di.fragment

import android.support.v4.app.Fragment
import dagger.MembersInjector

/**
 * Created by jobinlawrance on 22/9/17.
 */
interface FragmentComponent<F : Fragment> : MembersInjector<F>