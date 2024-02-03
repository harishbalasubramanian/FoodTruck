package com.example.foodtruck;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

public class FoodTruckListFragment extends Fragment {
    private FoodTruckAdapter adapter;
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
        return view;

    }
}




