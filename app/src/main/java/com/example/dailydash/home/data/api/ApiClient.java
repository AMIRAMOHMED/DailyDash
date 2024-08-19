package com.example.dailydash.home.data.api;


import com.example.dailydash.home.data.models.CategoriesResponse;
import com.example.dailydash.home.data.models.MealsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.rxjava3.core.Observable;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static final String TAG = "API_HANDLER";
    private static ApiClient apiClientInstance = null;
    private ApiInterFace apiService;

    private ApiClient() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // Ensure RxJava3 adapter is added
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiInterFace.class);
    }

    //signleton
    public static ApiClient getInstance() {
        if (apiClientInstance == null) {
            apiClientInstance = new ApiClient();
        }
        return apiClientInstance;
    }

    public Observable<CategoriesResponse> getCategories() {
        return apiService.getCategories();
    }

    public Observable<MealsResponse> getMealsByCategory(String category) {
        return apiService.getMealsByCategory(category);
    }

    public Observable<MealsResponse> getRandomMeal() {
        return apiService.getRandomMeal();
    }

}
