//===============================================
//Title: MapDemoActivity.java
//Author: Trish Valeri
//Contributors: CIS 3334 & techotopia.com
//Date: 4/14/16
//Purpose: to use the google maps api and place markers on the map in certain locations.
//         (get acquainted with google maps.)
//===============================================

package ebookfrenzy.com.mapdemo;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.widget.Toast;
import android.content.pm.PackageManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapDemoActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int LOCATION_REQUEST_CODE = 101;
    private String TAG = "MapDemo";
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_demo);
        //call to the request permission() method
        requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST_CODE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        if (mMap != null) {
//            //mMap.setMyLocationEnabled(true);
            //set the map style to satellite.
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//        }
        //add markers to the map!
        LatLng MUSEUM = new LatLng(38.8874245, -77.0200729);
        Marker museum = mMap.addMarker(new MarkerOptions().position(MUSEUM).title("Museum")
                .snippet("National Air and Space Museum"));

        LatLng GHS = new LatLng(47.287798, -93.425558);
        Marker ghs = mMap.addMarker(new MarkerOptions().position(GHS).title("School")
                .snippet("Greenway High School"));

        mMap.addMarker(new MarkerOptions().position(mapAddress("1200 Kenwood Ave., Duluth, MN"))
                .title("College").snippet("College of St.Scholastica"));
        //move the camera to hover over Greenway High school
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(GHS, 5));
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    protected void requestPermission(String permissionType, int requestCode) {
        int permission = ContextCompat.checkSelfPermission(this,
                permissionType);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permissionType}, requestCode
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {

                if (grantResults.length == 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Unable to show location - permission required", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    //converts a string address to latlng.
    private LatLng mapAddress(String address) {
        double latitude;
        double longitude;
        List<Address> geocodeMatches = null;
        try {
            geocodeMatches = new Geocoder(this).getFromLocationName(address, 1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (!geocodeMatches.isEmpty()) {
            latitude = geocodeMatches.get(0).getLatitude();
            longitude = geocodeMatches.get(0).getLongitude();
            return (new LatLng(latitude, longitude));
        }
        else {
            return null;
        }
    }
}
