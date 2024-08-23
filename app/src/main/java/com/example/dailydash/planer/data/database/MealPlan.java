package com.example.dailydash.planer.data.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "meal_plan")
public class MealPlan {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String mealId;
    private String mealImg;
    private String mealName;
    private String date;
    private  String userId;

    public MealPlan(String mealId, String mealImg, String mealName, String date, String userId) {
        this.mealId = mealId;
        this.mealImg = mealImg;
        this.mealName = mealName;
        this.date = date;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getMealImg() {
        return mealImg;
    }

    public void setMealImg(String mealImg) {
        this.mealImg = mealImg;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
