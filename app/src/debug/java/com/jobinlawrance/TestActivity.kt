package com.jobinlawrance

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout

/**
 * Created by jobinlawrance on 25/9/17.
 * This is a test activity defined in debug manifest which is used for inflating a fragment for testing
 */
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
class TestActivity : AppCompatActivity() {

    lateinit var fragment: Fragment

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val frameLayout = FrameLayout(this)
        frameLayout.id = 6542 //some random id
        setContentView(frameLayout)

        val fragmentClass = intent.getSerializableExtra("FragmentClass") as Class<Fragment>

        fragment = fragmentClass.newInstance()

        supportFragmentManager.beginTransaction()
                .add(frameLayout.id, fragment)
                .commit()
    }


}