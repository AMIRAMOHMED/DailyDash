package com.example.dailydash.planer.data.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {MealPlan.class}, version = 1)
public abstract class MealPlanDatabase extends RoomDatabase {

    private static MealPlanDatabase instance;

    public abstract MealPlanDao mealPlanDao();

    public static synchronized MealPlanDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MealPlanDatabase.class, "meal_plan_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
