package com.example.foodtruck;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import android.location.Location;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnSuccessListener;


public class MapFragment extends Fragment implements OnMapReadyCallback,OnRequestPermissionsResultCallback {
    private FoodTruck[] foodTrucks = {
            new FoodTruck("Mikey's Grill", "34th and Market", "American, Sandwiches", 5, 39.955854, -75.191432,null,null
            ),
            new FoodTruck("Lyn's", "36th and Spruce", "Mexican", 4.5,39.950792, -75.195304,null,null
            ),
            new FoodTruck("John's Lunch Cart","33rd and Spruce","American, Sandwiches",4.5,39.950331, -75.191717,null,null),
            new FoodTruck("Rami's","40th and Locust","Middle-Eastern",4.5,39.952977, -75.202820,null,null),
            new FoodTruck("Sandwich Cart at 35th/Market","35th and Market","American, Sandwiches",4.5,39.956213, -75.193475,null,null),
            new FoodTruck("Gul's Breakfast and Lunch Cart","36th and Market","American, Sandwiches",4.5,39.956191, -75.194148,null,null),
            new FoodTruck("Pete's Little Lunch Box","33rd and Lancaaster","American, Sandwiches",4.5,39.956425, -75.189327,"(215) 605-1228","\n6:00am-4:00pm (MF)\n6:00am-4:00pm (Sa)"),
            new FoodTruck("Magic Carpet at 36th/Spruce","36th and Spruce","Vegetarian",4.5,39.950792, -75.195304,"(215) 327-7533","\n11:30am-3:00pm (MF)"),
            new FoodTruck("Troy Mediterranean at 38th/Spruce","38th and Spruce","Middle Eastern",4.5,39.951287, -75.199274,"(510) 659-8855",null),
            new FoodTruck("Fruit Truck at 37th/Spruce","37th and Spruce","Fruit",4.5,39.951020, -75.197285,null,"\n11:30am-6:30pm (MF)"),
            new FoodTruck("Memo's Lunch Truck","33rd and Arch","Middle-Eastern",4.5,39.957611, -75.189076,"(215) 939-4386","\n12:00am-12:00pm (MF)"),
            new FoodTruck("Hanan House of Pita","38th and Walnut","Middle-Eastern",4,39.953640, -75.198767,"(267) 226-5692","\n11:00am-8:00pm (MF)\n11:00am-8:00pm (Sa)"),
            new FoodTruck("Fruit Truck at 35th/Market","35th and Market","Fruit",4,39.956213, -75.193475,null,null),
            new FoodTruck("Troy and Mediterranean at 40th/Spruce","40th and Spruce","Middle-Eastern",4,39.951756, -75.203074,"(610) 659-8855",null),
            new FoodTruck("Sonic's","37th and Spruce","American, Sandwiches",4,39.951030, -75.197268,null,"\n8:30am-5:30pm (MF)"),
            new FoodTruck("Ali Baba","37th and Walnut","Middle-Eastern",4,39.953388, -75.196763,null,"\n8:00am-4:00pm (MF)")
    };
    private GoogleMap googleMap;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        fusedLocationClient = new FusedLocationProviderClient(requireContext());
        return rootView;
    }
    private static final int PERMISSIONS_REQUEST_LOCATION = 1001;

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        // Customize map settings
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        // Add marker for default center
        LatLng defaultLocation = new LatLng(39.9529, -75.197098);
//        googleMap.addMarker(new MarkerOptions().position(defaultLocation).title("Center"));
        for(int i = 0; i < foodTrucks.length; i++)
        {
            LatLng location = new LatLng(foodTrucks[i].getLatitude(),foodTrucks[i].getLongitude());
            googleMap.addMarker(new MarkerOptions().position(location).title(foodTrucks[i].getName()));
        }
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Request location permission if not granted
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_LOCATION);
            return;
        }

        // Get user's last known location
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(requireActivity(), location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Add a marker at the user's location
                        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
                    } else {
                        Toast.makeText(requireContext(), "Location not available", Toast.LENGTH_SHORT).show();
                    }
                });

        // Enable location layer on map
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 14f));

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, refresh the map to get user's location
                if (googleMap != null) {
                    onMapReady(googleMap);
                }
            } else {
                // Permission denied, show a message to the user
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

