package com.example.dailydash.home.data.repo;

import android.content.Context;

import com.example.dailydash.home.data.api.ApiClient;
import com.example.dailydash.home.data.database.FavoriteMeal;
import com.example.dailydash.home.data.database.MealsDataBase;
import com.example.dailydash.home.data.models.CategoriesResponse;
import com.example.dailydash.home.data.models.MealsResponse;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Repository {
    private final ApiClient apiClient;
    private static Repository repositoryInstance;
    private final MealsDataBase mealsDataBase;

    private Repository(Context context) {
        mealsDataBase = MealsDataBase.getInstance(context);
        apiClient = ApiClient.getInstance();
    }

    public static Repository getInstance(Context context){
        if (repositoryInstance == null){
            repositoryInstance = new Repository(context);
        }
        return repositoryInstance;
    }

    public Observable<CategoriesResponse> getCategories() {
        return apiClient.getCategories()
                .subscribeOn(Schedulers.io());
    }

    public Observable<MealsResponse> getMealsByCategory(String category) {
        return apiClient.getMealsByCategory(category)
                .subscribeOn(Schedulers.io());
    }

    public Observable<MealsResponse> getRandomMeal() {
        return apiClient.getRandomMeal()
                .subscribeOn(Schedulers.io());
    }

    // Database methods
    public Completable addFavoriteMeal(FavoriteMeal favoriteMeal) {
        return mealsDataBase.favoriteMealDao().insert(favoriteMeal);
    }

    public Flowable<List<FavoriteMeal>> getFavoriteMeals(String userId) {
        return mealsDataBase.favoriteMealDao().getAllFavoriteMeals(userId)
                .subscribeOn(Schedulers.io());
    }

    public Completable removeFavoriteMeal(FavoriteMeal favoriteMeal) {
        return mealsDataBase.favoriteMealDao().delete(favoriteMeal)
                .subscribeOn(Schedulers.io());
    }
}