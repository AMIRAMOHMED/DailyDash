package com.example.dailydash.home.views.adpoter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.dailydash.R;
import com.example.dailydash.home.data.database.FavoriteMeal;
import com.example.dailydash.home.views.interfaces.FavFragmentContract;

import java.util.ArrayList;
import java.util.List;

public class FavItemAdapter extends RecyclerView.Adapter<FavItemAdapter.FavItemViewHolder>{

    private ArrayList<FavoriteMeal> favoriteMeals;
    private FavFragmentContract.Presenter presenter;

    public FavItemAdapter(ArrayList<FavoriteMeal> favoriteMeals, FavFragmentContract.Presenter presenter) {
        this.favoriteMeals = favoriteMeals;
        this.presenter = presenter;

    }
    @NonNull
    @Override
    public FavItemAdapter.FavItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favmealcrad, parent, false);
        return new FavItemAdapter.FavItemViewHolder(view); //
    }

    @Override
    public void onBindViewHolder(@NonNull FavItemAdapter.FavItemViewHolder holder, int position) {
        FavoriteMeal meal = favoriteMeals.get(position);

        holder.MealName.setText(meal.getMealName());

        Glide.with(holder.itemView.getContext())
                .load(meal.getPhotoUrl())
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.constraintLayout.setBackground(resource); // Set the background
                    }


                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });


        holder.delete.setOnClickListener(v -> {
            if (meal != null) {
                presenter.deleteMeal(meal);
            }
        });

    }@Override
    public int getItemCount() {
        return favoriteMeals.size();
    }

    public class FavItemViewHolder extends RecyclerView.ViewHolder {

        TextView MealName,delete;
        ConstraintLayout constraintLayout ;

        public FavItemViewHolder(@NonNull View itemView) {
            super(itemView);

            MealName = itemView.findViewById(R.id.textView7);
            delete = itemView.findViewById(R.id.delet);
            constraintLayout=itemView.findViewById(R.id.constraintLayoutAll);
        }
    }
    public void updateMeals(List<FavoriteMeal> newMeals) {
        this.favoriteMeals.clear();
        this.favoriteMeals.addAll(newMeals);
        notifyDataSetChanged();
}
}
