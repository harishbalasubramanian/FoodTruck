package com.example.foodtruck;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Arrays;
import java.util.Comparator;

public class FoodTruckListFragment extends Fragment {
    private FoodTruckAdapter adapter;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private static final int PERMISSIONS_REQUEST_LOCATION = 1001;
    public FoodTruckListFragment()
    {

    }
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
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("here");
        View view =  inflater.inflate(R.layout.fragment_food_truck_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FoodTruckAdapter(foodTrucks);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(foodTruck -> {
            Intent intent = new Intent(getContext(), FoodDetailsActivity.class);
            // Pass relevant food truck details to the new activity
            intent.putExtra("name", foodTruck.getName());
            intent.putExtra("address", foodTruck.getAddress());
            intent.putExtra("type", foodTruck.getType());
            intent.putExtra("rating", foodTruck.getRating());
            intent.putExtra("phoneNumber",foodTruck.getPhoneNumber());
            intent.putExtra("hours",foodTruck.getHours());
            // Add more details as needed
            startActivity(intent);
        });
        adapter.notifyDataSetChanged();
        fusedLocationClient = new FusedLocationProviderClient(requireContext());
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Request location permission if not granted
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_LOCATION);
            return view;
        }

        // Get user's last known location
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(requireActivity(), location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Add a marker at the user's location
                        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        foodTruckSort(userLocation);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(requireContext(), "Location not available", Toast.LENGTH_SHORT).show();
                    }
                });
        return view;

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                // Permission denied, show a message to the user
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void foodTruckSort(LatLng userLocation)
    {
        Arrays.sort(foodTrucks,new DistanceComparator(userLocation));
        System.out.println("Sorted");
    }
    public static class DistanceComparator implements Comparator<FoodTruck> {
        public final LatLng userLocation;
        public DistanceComparator(LatLng userLocation)
        {
            this.userLocation = userLocation;
        }
        @Override
        public int compare(FoodTruck foodTruck1, FoodTruck foodTruck2)
        {
            LatLng latLng1 = new LatLng(foodTruck1.getLatitude(),foodTruck1.getLongitude());
            LatLng latLng2 = new LatLng(foodTruck2.getLatitude(),foodTruck2.getLongitude());
            double distance1 = distanceBetweenLatLng(userLocation,latLng1);
            double distance2 = distanceBetweenLatLng(userLocation,latLng2);
            int distanceComparison = Double.compare(distance1,distance2);
            if (distanceComparison != 0)
            {
                return distanceComparison;
            }
            // If the distances are the same, then we compare by rating
            return Double.compare(foodTruck2.getRating(),foodTruck1.getRating());
        }

    }
    public static final double RADIUS_OF_EARTH_KM = 6371;
    public static double distanceBetweenLatLng(LatLng latLng1, LatLng latLng2)
    {
        double lat1 = Math.toRadians(latLng1.latitude);
        double lon1 = Math.toRadians(latLng1.longitude);
        double lat2 = Math.toRadians(latLng2.latitude);
        double lon2 = Math.toRadians(latLng2.longitude);
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;

        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon/2),2);

        double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));

        return RADIUS_OF_EARTH_KM * c;


    }
}




