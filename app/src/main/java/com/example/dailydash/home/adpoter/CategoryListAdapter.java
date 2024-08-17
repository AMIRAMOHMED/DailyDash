package com.example.dailydash.home.adpoter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailydash.R;
import com.example.dailydash.home.data.models.Category;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {

    private ArrayList<Category> categories;

    // Constructor that takes an ArrayList of categories
    public CategoryListAdapter(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.categorycard, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        // Set the category data for each card
        Category category = categories.get(position);
        holder.categoryName.setText(category.getStrCategory()); // assuming Category has a getName() method
    }

    @Override
    public int getItemCount() {
        return categories.size(); // Return the size of the categories list
    }

    // ViewHolder class for category card
    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView categoryName;
        CircleImageView categoryImage;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.circularImageView);
            categoryName = itemView.findViewById(R.id.categoryName);
        }
    }

    public void updateCategories(ArrayList<Category> newCategories) {
        categories.addAll(newCategories);
        notifyDataSetChanged();
    }
}
