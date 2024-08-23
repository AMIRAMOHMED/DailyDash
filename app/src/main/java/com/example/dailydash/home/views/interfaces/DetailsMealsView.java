package com.example.dailydash.home.views.interfaces;

import com.example.dailydash.home.data.models.Ingredient;
import com.example.dailydash.home.data.models.Meals;
import com.example.dailydash.planer.data.database.MealPlan;

import java.util.List;

public interface DetailsMealsView {
    void displayDetails(Meals meal);

    void displayError(String errorMessage);
    void displayIngredient(List<Ingredient> ingredient);
    void updateFavouriteIcon(boolean isFavorite);
    void showError(String errorMessage);
    void showMealDialog(MealPlan meal);
    void showMealsPlanAddedMessage();

    }
