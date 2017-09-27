package com.jobinlawrance.pics

import android.content.Intent
import android.support.test.rule.ActivityTestRule
import android.support.v4.app.Fragment
import com.jobinlawrance.TestActivity

/**
 * Created by jobinlawrance on 25/9/17.
 */
class FragmentTestRule<F : Fragment>(val fragmentClass: Class<F>) : ActivityTestRule<TestActivity>(TestActivity::class.java, true, false) {


//    override fun afterActivityLaunched() {
//        activity.runOnUiThread({
//            try {
////                activity.fragment = fragment
//                activity.supportFragmentManager.beginTransaction()
//                        .add(R.id.fragment_container, fragment)
//                        .commit()
//
//            } catch (e: Exception) {
//                when (e) {
//                    is IllegalAccessException,
//                    is InstantiationException,
//                    is IllegalArgumentException -> {
//                        Assert.fail("${this.javaClass.simpleName}: Could not insert ${fragmentClass.simpleName} into TestActivity: ${e.message}")
//                    }
//                    else -> throw e
//                }
//            }
//        })
//    }

    override fun launchActivity(startIntent: Intent?): TestActivity {

        val intent = if (startIntent == null) Intent() else startIntent
        intent.putExtra("FragmentClass", fragmentClass)

        return super.launchActivity(intent)
    }

    fun getFragment(): F = activity.fragment as F

}