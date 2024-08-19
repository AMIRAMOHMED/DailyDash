package com.example.dailydash.home.data.repo;

import com.example.dailydash.home.data.api.ApiClient;
import com.example.dailydash.home.data.models.CategoriesResponse;
import com.example.dailydash.home.data.models.MealsResponse;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Repository {
    private final ApiClient apiClient;
    private static Repository    repositoryInstance;

  private   Repository(){
        apiClient = ApiClient.getInstance();
    }

    public static Repository getInstance(){
        if (repositoryInstance == null){
            repositoryInstance = new Repository();
        }
        return repositoryInstance;
    }


    public Observable<CategoriesResponse> getCategories() {
        return apiClient.getCategories()
                .subscribeOn(Schedulers.io());
    }

    public Observable<MealsResponse> geMealsByCategory(String String) {
        return apiClient.getMealsByCategory(String)
                .subscribeOn(Schedulers.io());
    }

    public Observable<MealsResponse> getRandomMeal() {
        return apiClient.getRandomMeal()
                .subscribeOn(Schedulers.io());
    }
}
