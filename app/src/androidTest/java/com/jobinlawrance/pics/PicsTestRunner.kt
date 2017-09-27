package com.jobinlawrance.pics

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnitRunner
import com.linkedin.android.testbutler.TestButler

/**
 * Created by jobinlawrance on 23/9/17.
 */
class PicsTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application =
            super.newApplication(cl, MyTestApplication::class.java.name, context)

    override fun onStart() {
        TestButler.setup(InstrumentationRegistry.getTargetContext())
        super.onStart()
    }

    override fun finish(resultCode: Int, results: Bundle?) {
        TestButler.teardown(InstrumentationRegistry.getTargetContext())
        super.finish(resultCode, results)
    }
}