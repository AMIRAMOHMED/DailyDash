package com.example.dailydash.home.adpoter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailydash.R;
import com.example.dailydash.home.data.models.Category;

import java.util.ArrayList;

public class CategoryItemAdaptor extends RecyclerView.Adapter<CategoryItemAdaptor.CategoryViewHolder> {

    private ArrayList<Category> categories;

    public CategoryItemAdaptor(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_vertical_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);

        // Bind the category data to the views
        holder.categoryName.setText(category.getStrCategory());
        holder.favIcon.setImageResource(R.drawable.chicken); // Change this based on your icon logic
        holder.cookNow.setOnClickListener(v -> {
            // Handle the cook now button click
        });

        // Set the background image for the category
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView categoryName, cookNow;
        ImageView favIcon;
        ImageView background;


        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.textView7);
            cookNow = itemView.findViewById(R.id.cookNow);
            favIcon = itemView.findViewById(R.id.favIcon);

        }
    }
}