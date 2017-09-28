package com.jobinlawrance.pics.ui.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jobinlawrance.pics.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, HomeFragment())
                    .commit()
        }
    }
}
