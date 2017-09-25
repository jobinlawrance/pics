package com.jobinlawrance

import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.jobinlawrance.pics.R
import com.jobinlawrance.pics.home.HomeFragment

/**
 * Created by jobinlawrance on 25/9/17.
 * This is a test activity defined in debug manifest which is used for inflating a fragment for testing
 */
@VisibleForTesting
class TestActivity : AppCompatActivity() {

    lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val frameLayout = FrameLayout(this)
//        frameLayout.id = R.id.fragment_container
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, HomeFragment())
                .commit()
    }
}