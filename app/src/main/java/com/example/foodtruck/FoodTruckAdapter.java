package com.example.foodtruck;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FoodTruckAdapter extends RecyclerView.Adapter<FoodTruckAdapter.FoodTruckViewHolder> {

    private FoodTruck[] foodTrucks;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(FoodTruck foodTruck);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public FoodTruckAdapter(FoodTruck[] foodTrucks) {
        this.foodTrucks = foodTrucks;
    }

    @NonNull
    @Override
    public FoodTruckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food_truck_location, parent, false);
        return new FoodTruckViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodTruckViewHolder holder, int position) {
        FoodTruck foodTruck = foodTrucks[position];
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemClick(foodTruck);
            }
        });
        holder.restaurantName.setText(foodTruck.getName());
        holder.streetAddress.setText(foodTruck.getAddress());
        holder.restaurantType.setText(foodTruck.getType());

        // Set star icons
        setStarIcons(holder.stars, foodTruck.getRating());
    }

    // Helper method to set star icons
    private void setStarIcons(ImageView[] stars, double rating) {
        System.out.println("here3");
        int fullStars = ((int) Math.round(rating * 2)) / 2;
        int halfStars = ((int) Math.round(rating * 2)) % 2;

        for (int i = 0; i < 5; i++) {
            if (i < fullStars) {
                stars[i].setImageResource(R.drawable.full_star);
            } else if (i == fullStars && halfStars == 1) {
                stars[i].setImageResource(R.drawable.half_star);
            } else {
                stars[i].setImageResource(R.drawable.empty_star);
            }
        }
    }


    @Override
    public int getItemCount() {
        return foodTrucks.length;
    }

    public static class FoodTruckViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantName;
        TextView streetAddress;
        TextView restaurantType;
        ImageView[] stars = new ImageView[5]; // Array to hold star ImageViews

        public FoodTruckViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.restaurantName);
            streetAddress = itemView.findViewById(R.id.streetAddress);
            restaurantType = itemView.findViewById(R.id.restaurantType);
            stars[0] = itemView.findViewById(R.id.star1);
            stars[1] = itemView.findViewById(R.id.star2);
            stars[2] = itemView.findViewById(R.id.star3);
            stars[3] = itemView.findViewById(R.id.star4);
            stars[4] = itemView.findViewById(R.id.star5);
        }
    }
}

