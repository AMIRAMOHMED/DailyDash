package com.example.dailydash.planer.views.interfaces;

import com.example.dailydash.planer.data.database.MealPlan;

import java.util.List;

public interface CalendarContract {
    interface View {
        void showMealPlans(List<MealPlan> mealPlans);
        void showLoading();
        void hideLoading();
        void showError(String message);
        void navigateToLogin();
        void showDeleteConfirmation(MealPlan mealPlan);
        void showMealDeletedMessage();
    }

    interface Presenter {
        void attachView(View view);
        void detachView();
        void loadMealPlans();
        void deleteMealPlan(MealPlan mealPlan);
        void onLoginClick();
    }
}
