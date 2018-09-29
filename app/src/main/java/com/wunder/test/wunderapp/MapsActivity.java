package com.wunder.test.wunderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wunder.test.wunderapp.model.Car;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Car selectedCar;
    private ArrayList<Car> carsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();

        if(intent != null){
            if(intent.getParcelableExtra("car") != null) {
                selectedCar = intent.getParcelableExtra("car");
            }
            if(intent.getParcelableArrayListExtra("carsList") != null) {
                carsList = (ArrayList) intent.getParcelableArrayListExtra("carsList");
            }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
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
        System.out.println("onMapReady");
        mMap = googleMap;

        MarkerOptions options = new MarkerOptions();

        if(carsList != null && carsList.size() != 0){
            for(int i = 0; i < carsList.size(); i++){
                Car car = carsList.get(i);
                // Add a marker
                LatLng carLocation = new LatLng(car.getLatitude(), car.getLongitude());
                //System.out.println("car : " + car);
                mMap.addMarker(new MarkerOptions().position(carLocation).title(car.getName()));
            }
        }

        if(selectedCar != null) {
            // Add a marker and move the camera
            LatLng carLocation = new LatLng(selectedCar.getLatitude(), selectedCar.getLongitude());
            System.out.println("selected car : " + selectedCar);
            Marker selectedMarker = mMap.addMarker(new MarkerOptions().position(carLocation).title(selectedCar.getName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(carLocation,15));
            selectedMarker.showInfoWindow();
        }
    }
}
