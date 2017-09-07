package com.jobinlawrance.pics.extras

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by jobinlawrance on 8/9/17.
 */
class Utils {
    companion object {
        @JvmStatic
        fun ViewGroup.inflate(layoutRes: Int): View =
                LayoutInflater.from(context).inflate(layoutRes, this, false)
    }
}