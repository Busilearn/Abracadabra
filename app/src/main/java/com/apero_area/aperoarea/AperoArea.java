package com.apero_area.aperoarea;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by micka on 09-Aug-17.
 */

public class AperoArea extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
