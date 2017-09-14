package com.jobinlawrance.pics.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by jobinlawrance on 8/9/17.
 */

fun ViewGroup.inflate(layoutRes: Int): View =
        LayoutInflater.from(context).inflate(layoutRes, this, false)

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}