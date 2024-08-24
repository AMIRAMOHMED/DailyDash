package com.example.dailydash.planer.data.Repository;

import android.content.Context;

import com.example.dailydash.planer.data.database.MealPlan;
import com.example.dailydash.planer.data.database.MealPlanDatabase;
import com.example.dailydash.planer.data.firebase.FirebaseManager;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public class MealPlanRepository {
    private static MealPlanRepository mealPlanRepository;
    private final MealPlanDatabase mealPlanDatabase;
    FirebaseManager firebaseManager;

    // Private constructor to enforce singleton pattern
    private MealPlanRepository(Context context) {
        mealPlanDatabase = MealPlanDatabase.getInstance(context);
        firebaseManager = new FirebaseManager();

    }

    // Singleton instance getter
    public static synchronized MealPlanRepository getInstance(Context context) {
        if (mealPlanRepository == null) {
            mealPlanRepository = new MealPlanRepository(context);
        }
        return mealPlanRepository;
    }

    // Fetch a MealPlan by date
    public Maybe<MealPlan> getMealPlanByDate(String date) {
        return mealPlanDatabase.mealPlanDao().getMealPlanByDate(date);
    }

    // Insert a new MealPlan
    public Completable insertMealPlan(MealPlan mealPlan) {
        return mealPlanDatabase.mealPlanDao().insertMealPlan(mealPlan);
    }

    // Fetch all MealPlans
    public Flowable<List<MealPlan>> getAllMealPlans() {
        return mealPlanDatabase.mealPlanDao().getAllMealPlans();
    }

    // Delete a specific MealPlan
    public Completable deleteMealPlan(MealPlan mealPlan) {
        return mealPlanDatabase.mealPlanDao().deleteMealPlan(mealPlan);
    }

    // Delete a MealPlan by date
    public Completable deleteMealPlanByDate(String date) {
        return mealPlanDatabase.mealPlanDao().deleteMealPlanByDate(date);
    }

    // Fetch all MealPlans for a specific user
    public Flowable<List<MealPlan>> getMealPlansByUser(String userId) {
        return mealPlanDatabase.mealPlanDao().getMealPlansByUser(userId);
    }


    //firebase
    //add meal plan
    public Completable addMealPlan(MealPlan mealPlan) {
        return firebaseManager.addMealPlan(mealPlan);
    }
    //fetch meal plans

    public Single<List<MealPlan>> fetchMealPlans() {
        return firebaseManager.fetchMealPlans();
    }
//remove meal plan
    public Completable removeMealPlan(MealPlan mealPlan) {
        return firebaseManager.removeMealPlan(mealPlan);
    }
//clear meal plans
    public Completable clearMealPlans() {
        return firebaseManager.clearMealPlans();
    }
}
