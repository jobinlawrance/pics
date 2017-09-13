package com.jobinlawrance.pics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jobinlawrance.pics.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
    }
}
