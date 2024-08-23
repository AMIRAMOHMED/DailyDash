package com.example.dailydash.home.presenter;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.widget.ImageButton;

import com.example.dailydash.home.data.database.FavoriteMeal;
import com.example.dailydash.home.data.models.Meals;
import com.example.dailydash.home.data.repo.Repository;
import com.example.dailydash.home.views.Utility.FavoriteUtils;
import com.example.dailydash.home.views.interfaces.DetailsMealsView;
import com.example.dailydash.planer.data.Repository.MealPlanRepository;
import com.example.dailydash.planer.data.database.MealPlan;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class DetailsMealsPresenter {

    private final DetailsMealsView detailsMealsView;
    private final Repository repository;
    private final MealPlanRepository mealPlanRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public DetailsMealsPresenter(DetailsMealsView detailsMealsView, Context context) {
        this.detailsMealsView = detailsMealsView;
        this.repository = Repository.getInstance(context.getApplicationContext());
        this.mealPlanRepository = MealPlanRepository.getInstance(context.getApplicationContext());
    }

    public void toggleFavorite(Meals meal, ImageButton favIcon, Context context) {
        FavoriteMeal favMeal = new FavoriteMeal(meal.getStrMeal(), meal.getStrMealThumb(), "hbjh", meal.getIdMeal());
        FavoriteUtils.toggleFavorite(favIcon, favMeal, repository, context.getApplicationContext());
    }

    public void addMealToPlan(Meals meal, long selectedDate) {
        String date = new java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault()).format(new java.util.Date(selectedDate));

        Disposable disposable = mealPlanRepository.getMealPlanByDate(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        existingMealPlan -> {
                            if (existingMealPlan != null) {
                                detailsMealsView.showMealDialog(existingMealPlan);
                            } else {
                                MealPlan mealPlan = new MealPlan(meal.getIdMeal(), meal.getStrMealThumb(), meal.getStrMeal(), date, "hbjh");
                                mealPlanRepository.insertMealPlan(mealPlan)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                                () -> detailsMealsView.showMealsPlanAddedMessage(),
                                                throwable -> detailsMealsView.showError(throwable.getMessage())
                                        );
                            }
                        },
                        throwable -> detailsMealsView.showError(throwable.getMessage())
                );

        compositeDisposable.add(disposable);
    }

    public void cleanup() {
        // Dispose of all subscriptions when the presenter is no longer needed
        compositeDisposable.clear();
    }
}
