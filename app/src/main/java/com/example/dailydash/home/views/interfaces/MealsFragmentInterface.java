package com.example.dailydash.home.views.interfaces;

import com.example.dailydash.home.data.models.Meals;

import java.util.List;

public interface MealsFragmentInterface {


        void showMeals(List<Meals> meals);
        void showError(String errorMessage);

}
