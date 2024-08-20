package com.example.dailydash.home.views.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.dailydash.R;
import com.example.dailydash.home.data.models.Ingredient;
import com.example.dailydash.home.data.models.Meals;
import com.example.dailydash.home.views.adpoter.IngredientAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailsMeals extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_meals, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.ingredientRecyclerView);
        TextView textView = view.findViewById(R.id.mealTitle);

        if (getArguments() != null) {
            DetailsMealsArgs args = DetailsMealsArgs.fromBundle(getArguments());
            Meals meal = args.getMeal();
            Log.i("DetailsMeals", "onCreateView: " + meal.getIdMeal());
            textView.setText(meal.getStrMeal());

            List<Ingredient> ingredients = meal.getIngredients();
            Log.i("DetailsMeals", "Ingredients size: " + ingredients.size());
            Log.i("Bebo", "onCreateView: "+ingredients.get(0).getName());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            IngredientAdapter adapter = new IngredientAdapter(getContext(), ingredients);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }
}