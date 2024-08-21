package com.example.dailydash.home.views.adpoter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.dailydash.R;
import com.example.dailydash.home.data.models.Area;
import com.example.dailydash.home.data.models.Category;
import com.example.dailydash.home.data.models.IngredientS;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<Object> searchResults;

    public SearchAdapter(List<Object> searchResults) {
        this.searchResults = searchResults;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchrecyler, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Object item = searchResults.get(position);

        if (item instanceof Category) {
            Category category = (Category) item;
            holder.title.setText(category.getStrCategory());

            // Load the image using Glide
            Glide.with(holder.itemView.getContext())
                    .load(category.getStrCategoryThumb())
                    .into(holder.searchcategoryImage); // Load directly into the ImageView

        } else if (item instanceof Area) {
            Area area = (Area) item;
            holder.title.setText(area.getStrArea());

            // Optionally, you can set a placeholder or a default image for areas
            holder.searchcategoryImage.setImageResource(R.drawable.chicken);

        } else if (item instanceof IngredientS) {
            IngredientS ingredient = (IngredientS) item;

            holder.title.setText(ingredient.getStrIngredient());
//            https://www.themealdb.com/images/ingredients/Avocado.png
            Glide.with(holder.itemView.getContext())
                    .load("https://www.themealdb.com/images/ingredients/"+ingredient.getStrIngredient()+".png")
                    .into(holder.searchcategoryImage); // Load directly into the ImageView
        }
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public void updateData(List<Object> newResults) {
        searchResults.clear();
        searchResults.addAll(newResults);
        notifyDataSetChanged();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView searchcategoryImage;

        SearchViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.searchname);
            searchcategoryImage = itemView.findViewById(R.id.searchcategoryImage);
        }
    }
}
