package com.jobinlawrance.pics.utils

import android.content.Context
import android.util.TypedValue

/**
 * Created by jobinlawrance on 13/9/17.
 */

fun getActionBarSize(context: Context): Int {
    val value = TypedValue()
    context.theme.resolveAttribute(android.R.attr.actionBarSize, value, true)
    return TypedValue.complexToDimensionPixelSize(
            value.data, context.resources.displayMetrics)
}