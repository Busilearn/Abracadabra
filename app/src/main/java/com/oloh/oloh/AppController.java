package com.oloh.oloh;

import android.app.Application;

import com.oloh.oloh.domain.helper.NetworkConstants;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;
import io.realm.log.LogLevel;
import io.realm.log.RealmLog;


/**
 * Created by dany on 25/08/2017.
 */

public class AppController extends Application implements SyncUser.Callback {

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
        //RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().directory(getExternalFilesDir(null)).build();
        SyncUser.loginAsync(SyncCredentials.usernamePassword("mail@oloh.fr", "19901990", false), NetworkConstants.AUTH_URL, this);

        // Use the config
        //Realm.setDefaultConfiguration(config);
        //RealmLog.setLevel(LogLevel.TRACE);

        mInstance = this;
    }


    @Override
    public void onSuccess(SyncUser user) {
        SyncConfiguration defaultConfig = new SyncConfiguration.Builder(user, NetworkConstants.REALM_URL).build();
        Realm.setDefaultConfiguration(defaultConfig);
    }

    @Override
    public void onError(ObjectServerError error) {

    }
}
