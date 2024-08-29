package com.example.dailydash.home.views.adpoter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dailydash.R;
import com.example.dailydash.home.data.models.Area;
import com.example.dailydash.home.data.models.Category;
import com.example.dailydash.home.data.models.IngredientS;
import com.example.dailydash.home.views.fragments.SearchFragmentDirections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private final List<Object> searchResults;
    private final Map<String, String> areaFlagMap;

    public SearchAdapter(List<Object> searchResults) {
        this.searchResults = searchResults;
        this.areaFlagMap = createAreaFlagMap();
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
            bindCategory(holder, (Category) item);
        } else if (item instanceof Area) {
            bindArea(holder, (Area) item);
        } else if (item instanceof IngredientS) {
            bindIngredient(holder, (IngredientS) item);
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
        ImageView searchCategoryImage;
        CardView cardView;

        SearchViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.searchname);
            cardView =itemView.findViewById(R.id.searchrecyler);
            searchCategoryImage = itemView.findViewById(R.id.searchcategoryImage);
        }
    }

    private void bindCategory(SearchViewHolder holder, Category category) {
        holder.title.setText(category.getStrCategory());
        holder.cardView.setOnClickListener(v -> {

            SearchFragmentDirections.ActionNavSearchToMealsFragment action =
                    SearchFragmentDirections.actionNavSearchToMealsFragment("Category", category.getStrCategory());
            Navigation.findNavController(v).navigate(action);


        });
        // Load the image using Glide
        Glide.with(holder.itemView.getContext())
                .load(category.getStrCategoryThumb())
                .placeholder(R.drawable.placeholderimage)
                .error(R.drawable.onerorr)
                .into(holder.searchCategoryImage);

    }

    private void bindArea(SearchViewHolder holder, Area area) {
        holder.title.setText(area.getStrArea());
        holder.cardView.setOnClickListener(v -> {

            SearchFragmentDirections.ActionNavSearchToMealsFragment action =
                    SearchFragmentDirections.actionNavSearchToMealsFragment("Area", area.getStrArea());
            Navigation.findNavController(v).navigate(action);


        });
        // Load the flag image based on the area
        String flagUrl = areaFlagMap.get(area.getStrArea());
        if (flagUrl != null) {
            Glide.with(holder.itemView.getContext())

                    .load(flagUrl)
                    .placeholder(R.drawable.placeholderimage)
                    .error(R.drawable.onerorr)
                    .into(holder.searchCategoryImage);
        } else {
            // Set a default image if the flag is not found
            holder.searchCategoryImage.setImageResource(R.drawable.chicken);
        }
    }

    private void bindIngredient(SearchViewHolder holder, IngredientS ingredient) {
        holder.title.setText(ingredient.getStrIngredient());
holder.cardView.setOnClickListener(v -> {

    SearchFragmentDirections.ActionNavSearchToMealsFragment action =
            SearchFragmentDirections.actionNavSearchToMealsFragment("IngredientS", ingredient.getStrIngredient());
    Navigation.findNavController(v).navigate(action);


});
        Glide.with(holder.itemView.getContext())

                .load("https://www.themealdb.com/images/ingredients/" + ingredient.getStrIngredient() + ".png")
                .placeholder(R.drawable.placeholderimage)
                .error(R.drawable.onerorr)
                .into(holder.searchCategoryImage);
    }

    private Map<String, String> createAreaFlagMap() {
        Map<String, String> map = new HashMap<>();
        map.put("American", "https://www.worldometers.info/img/flags/us-flag.gif");
        map.put("British", "https://www.worldometers.info/img/flags/uk-flag.gif");
        map.put("Canadian", "https://www.worldometers.info/img/flags/ca-flag.gif");
        map.put("Chinese", "https://www.worldometers.info/img/flags/ch-flag.gif");
        map.put("Croatian", "https://www.worldometers.info/img/flags/hr-flag.gif");
        map.put("Dutch", "https://www.worldometers.info/img/flags/gm-flag.gif");
        map.put("Egyptian", "https://www.worldometers.info/img/flags/eg-flag.gif");
        map.put("Filipino", "https://www.worldometers.info/img/flags/rp-flag.gif");
        map.put("French", "https://www.worldometers.info/img/flags/fr-flag.gif");
        map.put("Greek", "https://www.worldometers.info/img/flags/gr-flag.gif");
        map.put("Indian", "https://www.worldometers.info/img/flags/in-flag.gif");
        map.put("Irish", "https://www.worldometers.info/img/flags/iz-flag.gif");
        map.put("Italian", "https://www.worldometers.info/img/flags/it-flag.gif");
        map.put("Jamaican", "https://www.worldometers.info/img/flags/jm-flag.gif");
        map.put("Japanese", "https://www.worldometers.info/img/flags/ja-flag.gif");
        map.put("Kenyan", "https://www.worldometers.info/img/flags/ke-flag.gif");
        map.put("Malaysian", "https://www.worldometers.info/img/flags/my-flag.gif");
        map.put("Mexican", "https://www.worldometers.info/img/flags/mx-flag.gif");
        map.put("Moroccan", "https://www.worldometers.info/img/flags/mo-flag.gif");
        map.put("Polish", "https://www.worldometers.info/img/flags/pl-flag.gif");
        map.put("Portuguese", "https://www.worldometers.info/img/flags/po-flag.gif");
        map.put("Russian", "https://www.worldometers.info/img/flags/rs-flag.gif");
        map.put("Spanish", "https://www.worldometers.info/img/flags/sp-flag.gif");
        map.put("Thai", "https://www.worldometers.info/img/flags/th-flag.gif");
        map.put("Tunisian", "https://www.worldometers.info/img/flags/ts-flag.gif");
        map.put("Turkish", "https://www.worldometers.info/img/flags/tu-flag.gif");
        map.put("Ukrainian", "https://www.worldometers.info/img/flags/up-flag.gif");
        map.put("Vietnamese", "https://www.worldometers.info/img/flags/vm-flag.gif");
        return map;
    }
}
