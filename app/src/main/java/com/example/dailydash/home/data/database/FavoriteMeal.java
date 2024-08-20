package com.example.dailydash.home.data.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_meals",primaryKeys = {"mealName","mealId"})
public class FavoriteMeal {


@NonNull
private String mealName;
@NonNull
    private  String mealId;
    private String photoUrl;
    private String userId;

    public FavoriteMeal(String mealName, String photoUrl, String userId, String mealId) {
        this.mealName = mealName;
        this.photoUrl = photoUrl;
        this.userId = userId;
        this.mealId = mealId;
    }



    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }
}
