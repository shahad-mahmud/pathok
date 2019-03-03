package com.exodia.shahad.pathok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

public class ActivitySplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //status bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.splash_back_notification));
    }
}
