package com.example.dailydash.home.views.adpoter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.dailydash.R;
import com.example.dailydash.home.data.models.Meals;
import com.example.dailydash.home.views.fragments.HomeFragment;
import com.example.dailydash.home.views.fragments.HomeFragmentDirections;
import com.example.dailydash.home.views.fragments.MealsFragment;
import com.example.dailydash.home.views.fragments.MealsFragmentDirections;

import java.util.ArrayList;
import java.util.List;

public class MealItemAdaptor extends RecyclerView.Adapter<MealItemAdaptor.MealViewHolder> {

    private ArrayList<Meals> meals;
    private Fragment fragment; // List of meals

    public MealItemAdaptor(ArrayList<Meals> meals, Fragment fragment) {
        this.meals = meals;
        // Constructor for initializing the meal list
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_vertical_item, parent, false);
        return new MealViewHolder(view); // Inflate layout and return the view holder
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meals meal = meals.get(position); // Get the meal at the current position

        // Bind the Meal data to the views
        holder.MealName.setText(meal.getStrMeal());
        holder.cookNow.setOnClickListener(v -> {
            if (fragment instanceof HomeFragment) {
                HomeFragmentDirections.ActionHomeFragmentToDetailsMeals action =
                        HomeFragmentDirections.actionHomeFragmentToDetailsMeals(meal);
                Navigation.findNavController(v).navigate(action);
            } else if (fragment instanceof MealsFragment) {
                MealsFragmentDirections.ActionMealsFragmentToDetailsMeals action =
                        MealsFragmentDirections.actionMealsFragmentToDetailsMeals(meal);
                Navigation.findNavController(v).navigate(action);
            }
        });


        Glide.with(holder.itemView.getContext())
                .load(meal.getStrMealThumb())
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.constraintLayout.setBackground(resource); // Set the background
                    }


                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });


    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    // ViewHolder class for meal items
    public static class MealViewHolder extends RecyclerView.ViewHolder {

        TextView MealName;
               CardView cookNow; // Views for meal name and cook now button
        ImageView favIcon; // Favorite icon
        ConstraintLayout constraintLayout ;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            MealName = itemView.findViewById(R.id.textView7); // Initialize views
            cookNow = itemView.findViewById(R.id.cookNow);
            favIcon = itemView.findViewById(R.id.favIcon);
            constraintLayout=itemView.findViewById(R.id.constraintLayoutAll);
        }
    }

    public void updateMeals(List<Meals> newMeals) {
        this.meals.clear();
        this.meals.addAll(newMeals);
        notifyDataSetChanged();
    }
}