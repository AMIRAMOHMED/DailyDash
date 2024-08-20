package com.example.dailydash.home.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dailydash.home.data.models.Meals;

@Database(entities = { FavoriteMeal.class}, version = 1)
public abstract class MealsDataBase extends RoomDatabase {
    private static MealsDataBase instance;

    public abstract FavoriteMealDao favoriteMealDao();

    public static synchronized MealsDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MealsDataBase.class, "meals_database")
                    .build();
        }
        return instance;
    }
}
