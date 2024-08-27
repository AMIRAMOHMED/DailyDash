package com.example.dailydash.planer.presenter;

import android.util.Log;

import com.example.dailydash.authentication.data.repo.AuthenticationRepository;
import com.example.dailydash.planer.data.Repository.MealPlanRepository;
import com.example.dailydash.planer.data.database.MealPlan;
import com.example.dailydash.planer.views.interfaces.CalendarContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CalendarPresenter implements CalendarContract.Presenter {

    private CalendarContract.View view;
    private MealPlanRepository mealPlanRepository;
    private AuthenticationRepository authRepository;
    private CompositeDisposable compositeDisposable;
    private Calendar calendar;

    public CalendarPresenter(MealPlanRepository mealPlanRepository, AuthenticationRepository authRepository) {
        this.mealPlanRepository = mealPlanRepository;
        this.authRepository = authRepository;
        this.compositeDisposable = new CompositeDisposable();
        this.calendar = Calendar.getInstance();
    }

    @Override
    public void attachView(CalendarContract.View view) {
        this.view = view;
        loadMealPlans();
    }

    @Override
    public void detachView() {
        view = null;
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    @Override
    public void loadMealPlans() {
        String userId = authRepository.readUserIdFromPreferences();
        if (userId == null || userId.isEmpty()) {
            view.navigateToLogin();
            return;
        }

        view.showLoading();

        compositeDisposable.add(
                mealPlanRepository.getMealPlansByUser(userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(mealPlans -> {
                            // Filter and sort the meal plans
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            String todayAsString = dateFormat.format(calendar.getTime());

                            List<MealPlan> futurePlans = new ArrayList<>();
                            List<MealPlan> pastPlans = new ArrayList<>();

                            for (MealPlan mealPlan : mealPlans) {
                                if (mealPlan.getDate().compareTo(todayAsString) >= 0) {
                                    futurePlans.add(mealPlan);
                                } else {
                                    pastPlans.add(mealPlan);
                                }
                            }

                            // Delete past meal plans
                            for (MealPlan pastPlan : pastPlans) {
                                compositeDisposable.add(
                                        mealPlanRepository.deleteMealPlan(pastPlan)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribeWith(new DisposableCompletableObserver() {
                                                    @Override
                                                    public void onComplete() {
                                                        Log.i("CalendarPresenter", "Deleted past meal plan: " + pastPlan);
                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {
                                                        Log.e("CalendarPresenter", "Error deleting past meal plan", e);
                                                    }
                                                })
                                );
                            }

                            Collections.sort(futurePlans, (meal1, meal2) -> meal1.getDate().compareTo(meal2.getDate()));
                            return futurePlans;
                        })
                        .subscribe(
                                mealPlans -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.showMealPlans(mealPlans);
                                    }
                                },
                                throwable -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.showError("Error fetching meal plans");
                                    }
                                    Log.e("CalendarPresenter", "Error fetching meal plans", throwable);
                                }
                        )
        );
    }

    @Override
    public void deleteMealPlan(MealPlan mealPlan) {
        compositeDisposable.add(
                mealPlanRepository.deleteMealPlan(mealPlan)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                if (view != null) {
                                    view.showMealDeletedMessage();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("CalendarPresenter", "Error deleting meal plan", e);
                                if (view != null) {
                                    view.showError("Error deleting meal plan");
                                }
                            }
                        })
        );
    }

    @Override
    public void onLoginClick() {
        if (view != null) {
            view.navigateToLogin();
        }
    }
}
