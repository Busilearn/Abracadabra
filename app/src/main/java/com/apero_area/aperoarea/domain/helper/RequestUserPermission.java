package com.apero_area.aperoarea.domain.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

/**
 * Created by stran on 11/09/2017.
 */

public class RequestUserPermission {
    private Activity activity;


    // Storage Permissions
    private static final int REQUEST_PERMISSION = 1;
    private static String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };

    public RequestUserPermission(Activity activity) {
        this.activity = activity;
    }

    public  boolean verifyStoragePermissions() {
        // Check if we have write permission
        int permissionWrite = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionRead = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if ((permissionWrite != PackageManager.PERMISSION_GRANTED) ||
                (permissionRead != PackageManager.PERMISSION_GRANTED)) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS,
                    REQUEST_PERMISSION
            );
            return false;
        }else{
            return true;
        }
    }
    public  boolean verifyPositionPermissions() {
        // Check if we have write permission
        int permissionWrite = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionRead = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);

        if ((permissionWrite != PackageManager.PERMISSION_GRANTED) ||
                (permissionRead != PackageManager.PERMISSION_GRANTED)) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS,
                    REQUEST_PERMISSION
            );
            return false;
        }else{
            return true;
        }
    }

    public void providePermission(){
        verifyStoragePermissions();
        verifyPositionPermissions();
    }

    public void forceFullOpenPermission(){
        if(!verifyPositionPermissions()){
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
            intent.setData(uri);
            activity.startActivityForResult(intent, REQUEST_PERMISSION);
        }
    }


}
