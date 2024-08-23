package com.example.dailydash.home.views.interfaces;

import android.widget.ImageButton;

import com.example.dailydash.home.data.models.Meals;
import com.example.dailydash.planer.data.database.MealPlan;

public interface DetailsMealsContract {
    interface View {
        void showMealDialog(MealPlan existingMealPlan);
        void showAddedToPlanMessage();
        void showError(String message);
    }

    interface Presenter {
        void onFavoriteClicked(Meals meal, ImageButton favIcon);
        void onPlanIconClicked(Meals meal, long selectedDate);
        void deleteMealPlan(MealPlan mealPlan);

    }
}
