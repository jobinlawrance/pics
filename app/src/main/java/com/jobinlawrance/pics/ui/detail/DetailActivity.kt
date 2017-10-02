package com.jobinlawrance.pics.ui.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jobinlawrance.pics.R
import com.jobinlawrance.pics.data.retrofit.model.PhotoResponse

class DetailActivity : AppCompatActivity() {

    companion object {
        @JvmField
        val PHOTO_REPSONSE = "PhotoResponse"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val photoResponse = intent.getParcelableExtra<PhotoResponse>(PHOTO_REPSONSE)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, DetailFragment.newInstance(photoResponse))
                    .commit()
        }

    }
}
