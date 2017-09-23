package com.jobinlawrance.pics

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner

/**
 * Created by jobinlawrance on 23/9/17.
 */
class PicsTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application =
            super.newApplication(cl, MyTestApplication::class.java.name, context)
}