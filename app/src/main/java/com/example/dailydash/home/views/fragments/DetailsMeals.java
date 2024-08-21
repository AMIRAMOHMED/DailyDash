package com.example.dailydash.home.views.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailydash.R;
import com.example.dailydash.home.data.database.FavoriteMeal;
import com.example.dailydash.home.data.models.Ingredient;
import com.example.dailydash.home.data.models.Meals;
import com.example.dailydash.home.data.repo.Repository;
import com.example.dailydash.home.views.Utility.FavoriteUtils;
import com.example.dailydash.home.views.adpoter.IngredientAdapter;

import java.util.List;


public class DetailsMeals extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_meals, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.ingredientRecyclerView);
        TextView textView = view.findViewById(R.id.mealTitle);
        ImageButton favIcon = view.findViewById(R.id.favIcon);
        Repository repository;

        if (getArguments() != null) {
            DetailsMealsArgs args = DetailsMealsArgs.fromBundle(getArguments());
            Meals meal = args.getMeal();
            Log.i("DetailsMeals", "onCreateView: " + meal.getIdMeal());
            textView.setText(meal.getStrMeal());
            repository = Repository.getInstance(getContext());
            List<Ingredient> ingredients = meal.getIngredients();
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            IngredientAdapter adapter = new IngredientAdapter(getContext(), ingredients);
            recyclerView.setAdapter(adapter);
            FavoriteMeal favMeal = new FavoriteMeal(meal.getStrMeal(), meal.getStrMealThumb(), "hbjh",meal.getIdMeal());

            favIcon.setOnClickListener(
                    v -> {
                        FavoriteUtils.toggleFavorite(favIcon, favMeal, repository, getContext());
                    }
            );

        }

        return view;
    }
}