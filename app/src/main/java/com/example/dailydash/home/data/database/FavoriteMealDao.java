package com.example.dailydash.home.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Delete;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import java.util.List;
@Dao
public interface FavoriteMealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(FavoriteMeal favoriteMeal);

    @Query("SELECT * FROM favorite_meals WHERE userId = :userId")
    Flowable<List<FavoriteMeal>> getAllFavoriteMeals(String userId);
    @Query("SELECT * FROM favorite_meals WHERE mealId = :mealId LIMIT 1")
    FavoriteMeal getMealById(String mealId);

    @Delete
    Completable delete(FavoriteMeal favoriteMeal);
}