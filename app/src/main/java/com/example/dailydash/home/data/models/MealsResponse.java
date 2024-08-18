package com.example.dailydash.home.data.models;


import java.util.List;

public class MealsResponse {
    private List<Meals> meals;

    public List<Meals> getMeals() {
        return meals;
    }

    public void setMeals(List<Meals> meals) {
        this.meals = meals;
    }
}