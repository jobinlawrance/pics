package com.jobinlawrance.pics.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.jobinlawrance.pics.utils.CIRCULAR_OUTLINE

/**
 * Created by jobinlawrance on 1/10/17.
 * ref - https://github.com/nickbutcher/plaid/blob/88814ff322041031286e333d64f12e8c33d34650/app/src/main/java/io/plaidapp/ui/widget/CircularImageView.java
 */

class CircularImageView : ImageView {
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        outlineProvider = CIRCULAR_OUTLINE
        clipToOutline = true
    }
}