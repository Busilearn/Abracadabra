package com.apero_area.aperoarea.view.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apero_area.aperoarea.R;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by stran on 11/09/2017.
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback  {


    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mLocationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private Location mLastKnownLocation;
    private static final int DEFAULT_ZOOM = 15;
    private final LatLng mDefaultLocation = new LatLng(48.855830, 2.346324);
    private ProgressDialog progress;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    Marker mCurrLocationMarker;
    private LatLng latLng;
    private Location loc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        getLocationPermission();

        updateLocationUI();

        if (mLocationPermissionGranted) {
            Log.e("test", "in nmLocationPermissionGranted: ");

            getDeviceLocation();}

    }

    /**
     * Saves the state of the map when the activity is paused.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    private void getDeviceLocation() {


        try {

                //Toast.makeText(getBaseContext(), "after", Toast.LENGTH_LONG).show();
                final Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();

                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            shippingArea(mLastKnownLocation);
                            if (mCurrLocationMarker != null) {
                                mCurrLocationMarker.remove(); //show the old location then of the old /= than the current location, do the code bellow
                            }
                            LatLng newLatLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(newLatLng);
                            markerOptions.title("Votre position");
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                            mCurrLocationMarker = mMap.addMarker(markerOptions);


                        } else {

                            Log.e("test", "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                            shippingArea(mLastKnownLocation);
                        }
                    }
                });


        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            //TODO progress = ProgressDialog.show(this, "Géolocalisation", "En cours de chargement, veuillez patienter", true);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // TODO getDeviceLocation();
                    mLocationPermissionGranted = true;
                    //TODO progress = ProgressDialog.show(this, "Géolocalisation", "En cours de chargement, veuillez patienter", true);

                }
            }
        }
        //updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);

            } else {

                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                //TODO getLocationPermission();


            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    public void shippingArea(Location location) {

        mLastKnownLocation = location;


        //Polygon polygon = Polygon.Builder() with only .addVertex and build() at the end
        List<LatLng> area = new ArrayList<>();
        area.add(new LatLng(48.879179, 2.278419));//200m outside the Palais des Congrès
        area.add(new LatLng(48.886697, 2.283214));//200m outside Porte de Champerret
        area.add(new LatLng(48.899486, 2.306624));//200m outside Porte de Clichy
        area.add(new LatLng(48.904734, 2.344236));//200m outside Porte de Clignancourt
        area.add(new LatLng(48.904466, 2.393116));//200m outside Porte de la Villette
        area.add(new LatLng(48.896420, 2.419133));//Premier point extrémité de Pantin (proche de BETC)
        area.add(new LatLng(48.887362, 2.423654));//Deuxième point extrémité de Pantin (proche de Collège Marie Curie)
        area.add(new LatLng(48.879142, 2.412828));//200m outside Porte des Lilas
        area.add(new LatLng(48.846136, 2.421416));//200m outside Porte de Vincennes
        area.add(new LatLng(48.827322, 2.405320));//200m outside Porte de Charenton
        area.add(new LatLng(48.812033, 2.361685));//200m outside Porte d'Italie
        area.add(new LatLng(48.832119, 2.253060));//200m outside Porte de Saint Cloud
        area.add(new LatLng(48.872508, 2.268112));//200m outside Porte de Dauphine


        // Get back the mutable Polygon
        //Polygon polygon = mMap.addPolygon(rectOptions);
        //boolean shipping = polygon.contains(point);

        if (location != null) {



            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            loc = location;

            boolean shipping = PolyUtil.containsLocation(latLng, area, true);
            Log.e("test", String.valueOf(latLng));

            Log.e("test", "in location ok shipping : " + shipping);


            if (shipping) {
                //TODO progress.dismiss();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(),
                                MainActivity.class);
                        intent.putExtra("CheckoutDisable", false);
                        intent.putExtra("loc", loc);
                        startActivity(intent);
                        Toast.makeText(getBaseContext(), "Vous êtes bien dans la zone de livraison", Toast.LENGTH_LONG).show();
                    }
                }, 2000);

                Log.i("Test", "location" + latLng.toString());


            } else {
                Log.e("test", "in not shiping : ");

                //TODO progress.dismiss();
                View v = getSupportFragmentManager().findFragmentById(R.id.map).getView();
                v.setAlpha(0.2f);

                TextView question = (TextView) findViewById(R.id.question);
                question.setVisibility(View.VISIBLE);

                Button oui = (Button) findViewById(R.id.checkBoxOui);
                oui.setVisibility(View.VISIBLE);
                oui.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getBaseContext(), "Vous ne pourrez pas commander", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("CheckoutDisable", true);
                        intent.putExtra("loc", loc);
                        startActivity(intent);
                    }
                });


                Button non = (Button) findViewById(R.id.checkBoxNon);
                non.setVisibility(View.VISIBLE);
                non.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        System.exit(0);
                    }
                });

            }
        }


    }

    @Override
    public void onResume() {

        super.onResume();

        getLocationPermission();

        updateLocationUI();

        if (mLocationPermissionGranted) {getDeviceLocation();}
    }


}
