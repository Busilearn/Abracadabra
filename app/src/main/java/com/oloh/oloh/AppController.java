package com.oloh.oloh;

import android.app.Application;

import com.oloh.oloh.util.PreferenceHelper;


/**
 * Created by dany on 25/08/2017.
 */

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private static AppController mInstance;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

}
