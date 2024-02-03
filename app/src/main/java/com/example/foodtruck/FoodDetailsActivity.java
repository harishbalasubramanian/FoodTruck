package com.example.foodtruck;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.widget.TextView;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.view.View;


public class FoodDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_truck_details);

        // Retrieve food truck details from intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String address = "Location: "+intent.getStringExtra("address");
        String type = "Type: "+intent.getStringExtra("type");
        String rating = "Rating: "+String.valueOf(intent.getDoubleExtra("rating", 0.0));
        String phoneNumber = intent.getStringExtra("phoneNumber");
        String hours = intent.getStringExtra("hours");

        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView addressTextView = findViewById(R.id.addressTextView);
        TextView typeTextView = findViewById(R.id.typeTextView);
        TextView ratingTextView = findViewById(R.id.ratingTextView);
        TextView phoneTextView = findViewById(R.id.phoneTextView);
        TextView hoursTextView = findViewById(R.id.hoursTextView);


        nameTextView.setText(name);
        addressTextView.setText(address);
        typeTextView.setText(type);
        ratingTextView.setText(rating);
        if (phoneNumber != null && !phoneNumber.isEmpty())
        {
            phoneTextView.setText("Phone Number: "+phoneNumber);
            phoneTextView.setVisibility(View.VISIBLE);
        }
        else
        {
            phoneTextView.setVisibility(View.INVISIBLE);
        }
        if (hours != null && !hours.isEmpty())
        {
            hoursTextView.setText("Hours: "+hours);
            hoursTextView.setVisibility(View.VISIBLE);
        }
        else
        {
            hoursTextView.setVisibility(View.INVISIBLE);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.light_blue)));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // This is the ID of the back arrow icon
                // Handle the back arrow click event here
                finish(); // Close the current activity and return to the previous activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

