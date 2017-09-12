package com.jobinlawrance.pics.extras

import android.annotation.TargetApi
import android.content.Context
import android.os.Build.VERSION_CODES
import android.util.AttributeSet
import android.widget.ImageView
import timber.log.Timber

/**
 * Created by jobinlawrance on 12/9/17.
 */
class AspectRatioImageView : ImageView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @TargetApi(VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //to keep the aspect ratio at 16:9
        val measuredHeight = (measuredWidth.toDouble() / 1.77777777778).toInt()
        Timber.d("Width = $measuredWidth Height = $measuredHeight")
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

}