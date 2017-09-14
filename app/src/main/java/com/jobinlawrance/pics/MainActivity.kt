package com.jobinlawrance.pics

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jobinlawrance.pics.home.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, HomeFragment())
                    .commit()
        }


    }
}
