package com.example.crewmanagement;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int INITIAL_REQUEST=1337;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in the college campus's and move the camera
        LatLng Campus1 = new LatLng(43.479174, -80.518390);//waterloo
        LatLng Campus2 = new LatLng(43.392288, -80.404592);//doon
        LatLng Campus3 = new LatLng(43.385939, -80.398361);//cambridge
        LatLng Campus4 = new LatLng(43.538646, -80.293921);//guelph
        LatLng Campus5 = new LatLng(43.368362, -80.994703);//stradford
        LatLng Campus6 = new LatLng(43.026162, -80.896884);//inersoll training center
        LatLng Campus7 = new LatLng(43.139883, -80.259489);//brantford

        mMap.addMarker(new MarkerOptions().position(Campus1).title("Waterloo Campus"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Campus1));
        mMap.addMarker(new MarkerOptions().position(Campus2).title("Doon Campus"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Campus2));
        mMap.addMarker(new MarkerOptions().position(Campus3).title("Cambridge Campus"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Campus3));
        mMap.addMarker(new MarkerOptions().position(Campus4).title("Guelph Campus"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Campus4));
        mMap.addMarker(new MarkerOptions().position(Campus5).title("Stradford Campus"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Campus5));
        mMap.addMarker(new MarkerOptions().position(Campus6).title("Inersoll Training center Campus"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Campus6));
        mMap.addMarker(new MarkerOptions().position(Campus7).title("Brantford Campus"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Campus7));
        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, 1337);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
    }
}
