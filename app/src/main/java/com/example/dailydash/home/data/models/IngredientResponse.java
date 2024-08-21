package com.example.dailydash.home.data.models;

import java.util.List;

public class IngredientResponse {

    List <IngredientS> meals;

    public IngredientResponse(List<IngredientS> ingredients) {
        this.meals = ingredients;
    }

    public List<IngredientS> getIngredients() {
        return meals;
    }
}
