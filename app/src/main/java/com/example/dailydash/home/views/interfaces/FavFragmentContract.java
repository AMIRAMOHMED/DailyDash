package com.example.dailydash.home.views.interfaces;


import com.example.dailydash.home.data.database.FavoriteMeal;

import java.util.List;

public interface FavFragmentContract {
    interface View {
        void showFavoriteMeals(List<FavoriteMeal> favoriteMeals);
        void showError(String message);
        void showCompletionMessage();
        void showMealDeletedMessage(FavoriteMeal meal);

    }

    interface Presenter {
        void deleteMeal(FavoriteMeal meal);
        void fetchFavoriteMeals(String userId);
        void onDestroy();
    }
}
