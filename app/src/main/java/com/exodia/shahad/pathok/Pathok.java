package com.exodia.shahad.pathok;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Pathok extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
