package com.jobinlawrance.pics.ui.custom

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.WindowInsets
import android.widget.FrameLayout


/**
 * Created by jobinlawrance on 13/9/17.
 * ref - https://medium.com/@azizbekian/windowinsets-24e241d4afb9
 * ref - https://gist.github.com/cbeyls/ab6903e103475bd4d51b
 */
class WindowInsetsFrameLayout : FrameLayout {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
        val childCount = childCount
        for (index in 0..childCount - 1)
            getChildAt(index).dispatchApplyWindowInsets(insets)

        return insets
    }
}