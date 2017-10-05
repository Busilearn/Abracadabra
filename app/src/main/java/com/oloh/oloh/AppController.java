package com.oloh.oloh;

import android.app.Application;

import com.oloh.oloh.domain.helper.NetworkConstants;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;


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

        /*SyncCredentials myCredentials = SyncCredentials.usernamePassword("micado94@msn.com", "19901990", true);

        SyncUser user = SyncUser.login(myCredentials, NetworkConstants.AUTH_URL);
        SyncConfiguration config = new SyncConfiguration.Builder(user, NetworkConstants.REALM_URL).build();
        */
        // Use the config
          Realm.setDefaultConfiguration(config);

        mInstance = this;
    }



}
