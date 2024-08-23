package com.example.dailydash.planer.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

@Dao
public interface MealPlanDao {

    @Insert
    Completable insertMealPlan(MealPlan mealPlan);

    @Query("SELECT * FROM meal_plan WHERE date = :date LIMIT 1")
    Maybe<MealPlan> getMealPlanByDate(String date);

    @Query("SELECT * FROM meal_plan")
    Flowable<List<MealPlan>> getAllMealPlans();

    @Delete
    Completable deleteMealPlan(MealPlan mealPlan);

    @Query("DELETE FROM meal_plan WHERE date = :date")
    Completable deleteMealPlanByDate(String date);

    @Query("SELECT * FROM meal_plan WHERE userId = :userId")
    Flowable<List<MealPlan>> getMealPlansByUser(String userId);
}
