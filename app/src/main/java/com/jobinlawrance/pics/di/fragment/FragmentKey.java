package com.jobinlawrance.pics.di.fragment;


import android.support.v4.app.Fragment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import dagger.MapKey;

/**
 * Created by jobinlawrance on 22/9/17.
 */
@MapKey
@Target(ElementType.METHOD)
public @interface FragmentKey {
    Class<? extends Fragment> value();
}
