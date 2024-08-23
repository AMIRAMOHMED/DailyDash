package com.example.dailydash.home.views.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailydash.R;
import com.example.dailydash.home.data.models.Ingredient;
import com.example.dailydash.home.data.models.Meals;
import com.example.dailydash.home.presenter.DetailsMealsPresenter;
import com.example.dailydash.home.views.adpoter.IngredientAdapter;
import com.example.dailydash.home.views.interfaces.DetailsMealsContract;
import com.example.dailydash.planer.data.database.MealPlan;
import com.google.android.material.datepicker.MaterialDatePicker;

import androidx.appcompat.app.AlertDialog;
import java.util.List;


public class DetailsMeals extends Fragment implements DetailsMealsContract.View {

    private DetailsMealsContract.Presenter presenter;
    private ImageButton favIcon;
    private ImageButton planIcon;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_meals, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.ingredientRecyclerView);
        textView = view.findViewById(R.id.mealTitle);
        favIcon = view.findViewById(R.id.favIcon);
        planIcon = view.findViewById(R.id.calenderIcon);

        Context context = getContext();
        if (context != null) {
            presenter = new DetailsMealsPresenter(this, context);

            if (getArguments() != null) {
                DetailsMealsArgs args = DetailsMealsArgs.fromBundle(getArguments());
                Meals meal = args.getMeal();
                textView.setText(meal.getStrMeal());

                List<Ingredient> ingredients = meal.getIngredients();
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                IngredientAdapter adapter = new IngredientAdapter(context, ingredients);
                recyclerView.setAdapter(adapter);

                favIcon.setOnClickListener(v -> presenter.onFavoriteClicked(meal, favIcon));
                planIcon.setOnClickListener(v -> {
                    MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                            .setTitleText("Select a date")
                            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                            .build();

                    materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                        long selectedDate = selection;
                        presenter.onPlanIconClicked(meal, selectedDate);
                    });

                    materialDatePicker.show(getParentFragmentManager(), "DATE_PICKER");
                });
            }
        }
        return view;
    }

    @Override
    public void showMealDialog(MealPlan existingMealPlan) {
        new AlertDialog.Builder(getContext())
                .setTitle("This Date Already Planned")
                .setMessage("A meal (" + existingMealPlan.getMealName() + ") has already been planned for this date.")
                .setIcon(R.drawable.test_image)
                .setPositiveButton("Delete", (dialog, which) -> presenter.deleteMealPlan(existingMealPlan))
                .setNegativeButton("Keep", null)
                .show();
    }

    @Override
    public void showAddedToPlanMessage() {
        Toast.makeText(getContext(), "Added to plan", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


}
