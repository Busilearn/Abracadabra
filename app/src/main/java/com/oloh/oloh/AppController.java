package com.oloh.oloh;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;


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

        // Initialize Realm
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().directory(getExternalFilesDir(null)).build();
        Realm.setDefaultConfiguration(config);

        mInstance = this;
    }

}
