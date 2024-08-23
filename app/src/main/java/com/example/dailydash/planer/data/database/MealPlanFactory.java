//package com.example.dailydash.planer.data.database;
//
//
//import com.google.firebase.auth.FirebaseAuth;
//
//import java.util.Objects;
//
//public class MealPlanFactory {
//    private final String userId;
//
//    public MealPlanFactory() {
//        this.userId = Objects.requireNonNull(
//
//
//          FirebaseAuth.getInstance().getCurrentUser()).getUid();
//    }
//
//    public MealPlan createMealPlan(String mealId, String mealImg, String mealName, String date) {
//        return new MealPlan(mealId, mealImg, mealName, date, userId);
//    }
//}
