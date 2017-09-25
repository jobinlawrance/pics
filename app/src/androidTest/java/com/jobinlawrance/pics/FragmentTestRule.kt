package com.jobinlawrance.pics

import android.support.test.rule.ActivityTestRule
import android.support.v4.app.Fragment
import com.jobinlawrance.TestActivity
import junit.framework.Assert
import java.lang.IllegalArgumentException

/**
 * Created by jobinlawrance on 25/9/17.
 */
class FragmentTestRule<F : Fragment>(val fragmentClass: Class<F>) : ActivityTestRule<TestActivity>(TestActivity::class.java, true, false) {

    lateinit var fragment: F

    override fun afterActivityLaunched() {
        super.afterActivityLaunched()

        activity.runOnUiThread({
            try {
                //Instantiate and insert the fragment into the container layout
                val manager = activity.supportFragmentManager
                fragment = fragmentClass.newInstance()
                activity.fragment = fragment

            } catch (e: Exception) {
                when (e) {
                    is IllegalAccessException,
                    is InstantiationException,
                    is IllegalArgumentException -> {
                        Assert.fail("${this.javaClass.simpleName}: Could not insert ${fragmentClass.simpleName} into TestActivity: ${e.message}")
                    }
                    else -> throw e
                }
            }
        })
    }

}