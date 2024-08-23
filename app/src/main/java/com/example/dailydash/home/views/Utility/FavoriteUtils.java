package com.example.dailydash.home.views.Utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.dailydash.R;
import com.example.dailydash.home.data.database.FavoriteMeal;
import com.example.dailydash.home.data.repo.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.atomic.AtomicBoolean;
public class FavoriteUtils {

    @SuppressLint("CheckResult")
    public static void toggleFavorite(ImageButton favIcon, FavoriteMeal favMeal, Repository repository, Context context) {
        repository.isFavoriteMeal(favMeal.getMealId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isFavorite -> {
                    if (isFavorite) {
                        repository.removeFavoriteMeal(favMeal)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    favIcon.setColorFilter(ContextCompat.getColor(context, R.color.LightGreen));
                                    Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        repository.addFavoriteMeal(favMeal)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    favIcon.setColorFilter(ContextCompat.getColor(context, R.color.red));
                                    Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show();
                                });
                    }
                });
    }
}
