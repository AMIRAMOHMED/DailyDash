package com.example.dailydash.home.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Meals implements Parcelable {
    private String strMeal;
    private String strMealThumb;
    private String idMeal;

    // Default constructor
    public Meals() {}

    // Getters and setters
    public String getStrMeal() {
        return strMeal;
    }


    public String getStrMealThumb() {
        return strMealThumb;
    }

    public String getIdMeal() {
        return idMeal;
    }


    // Parcelable implementation
    protected Meals(Parcel in) {
        strMeal = in.readString();
        strMealThumb = in.readString();
        idMeal = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(strMeal);
        dest.writeString(strMealThumb);
        dest.writeString(idMeal);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Meals> CREATOR = new Creator<Meals>() {
        @Override
        public Meals createFromParcel(Parcel in) {
            return new Meals(in);
        }

        @Override
        public Meals[] newArray(int size) {
            return new Meals[size];
        }
    };
}