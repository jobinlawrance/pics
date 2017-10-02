package com.jobinlawrance.pics.utils

import android.content.Context
import android.graphics.Outline
import android.util.TypedValue
import android.view.View
import android.view.ViewOutlineProvider

/**
 * Created by jobinlawrance on 13/9/17.
 */

fun getActionBarSize(context: Context): Int {
    val value = TypedValue()
    context.theme.resolveAttribute(android.R.attr.actionBarSize, value, true)
    return TypedValue.complexToDimensionPixelSize(
            value.data, context.resources.displayMetrics)
}

val CIRCULAR_OUTLINE = object : ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {
        outline.setOval(view.paddingLeft,
                view.paddingTop,
                view.width - view.paddingRight,
                view.height - view.paddingBottom)
    }
}