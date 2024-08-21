package com.example.dailydash.home.data.models;

import java.util.List;

public class AreaResponse{

    private List<Area> meals;

    public AreaResponse(List<Area> meals) {
        this.meals = meals;
    }

    public List<Area> getMeals() {
        return meals;
    }

    public void setMeals(List<Area> meals) {
        this.meals = meals;
    }
}