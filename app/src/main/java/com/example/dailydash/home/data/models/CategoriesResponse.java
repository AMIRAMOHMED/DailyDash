package com.example.dailydash.home.data.models;

import java.util.List;

public class CategoriesResponse {
    private List<Category> categories;

    public CategoriesResponse(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

}
