package com.example.dailydash.home.data.api;

import com.example.dailydash.home.data.models.CategoriesResponse;
import com.example.dailydash.home.data.models.MealsResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterFace {

    @GET("categories.php")
    public Observable<CategoriesResponse> getCategories();
    @GET("filter.php")
    public Observable<MealsResponse> getMealsByCategory(@Query("c") String category);

@GET("random.php")
public Observable<MealsResponse> getRandomMeal();
@GET("lookup.php")
public Observable<MealsResponse> getMealById(@Query("i") String id);

}
