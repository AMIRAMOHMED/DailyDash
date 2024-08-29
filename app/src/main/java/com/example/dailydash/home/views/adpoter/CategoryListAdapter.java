package com.example.dailydash.home.views.adpoter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dailydash.R;
import com.example.dailydash.home.data.models.Category;
import com.example.dailydash.home.data.models.Meals;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {

    private List<Category> categories;
    private CategoryClickListener categoryClickListener;
    private int selectedPosition = -1;  // To keep track of the selected item

    public interface CategoryClickListener {
        void onCategoryClick(String category);
    }

    public CategoryListAdapter(List<Category> categories, CategoryClickListener categoryClickListener) {
        this.categories = categories;
        this.categoryClickListener = categoryClickListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categorycard, parent, false));
    }
    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Category category = categories.get(position);
        holder.categoryName.setText(category.getStrCategory());

        Glide.with(holder.itemView.getContext())
                .load(category.getStrCategoryThumb())
                .placeholder(R.drawable.placeholderimage)
                .error(R.drawable.onerorr)
                .into(holder.categoryImage);


        holder.cardView.setOnClickListener(v -> {
            selectedPosition = position;
            categoryClickListener.onCategoryClick(category.getStrCategory());
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        CircleImageView categoryImage;
        CardView cardView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.circularImageView);
            categoryName = itemView.findViewById(R.id.categoryName);
            cardView = itemView.findViewById(R.id.home_horizotal_item);
        }



    }
    public void updateCategories(List<Category> newCategories) {
        this.categories.clear();
        this.categories.addAll(newCategories);
        notifyDataSetChanged();
    }
}
