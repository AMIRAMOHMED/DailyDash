package com.example.dailydash.home.presenter;


import android.content.Context;

import com.example.dailydash.authentication.data.repo.AuthenticationRepository;
import com.example.dailydash.home.views.interfaces.ProfileContract;
import com.example.dailydash.planer.data.Repository.MealPlanRepository;
import com.example.dailydash.planer.data.database.MealPlan;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenter implements ProfileContract.Presenter {
    private final ProfileContract.View view;
    private final AuthenticationRepository authRepo;
    private final MealPlanRepository mealPlanRepo;

    public ProfilePresenter(ProfileContract.View view, Context context) {
        this.view = view;
        this.authRepo = AuthenticationRepository.getInstance(context);
        this.mealPlanRepo = MealPlanRepository.getInstance(context);
    }

    @Override
    public void onLogout() {
        authRepo.logOut();
        authRepo.addLoginToPreferences(false);
        authRepo.deleteUserIdFromPreferences();
        view.navigateToLogin();
    }

    @Override
    public void onBackup() {
        mealPlanRepo.getMealPlansByUser(authRepo.readUserIdFromPreferences())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealPlans -> {
                            for (MealPlan mealPlan : mealPlans) {
                                mealPlanRepo.addMealPlan(mealPlan)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                                view::showBackupSuccess,
                                                throwable -> view.showBackupFailure(throwable.getMessage())
                                        );
                            }
                        },
                        throwable -> view.showBackupFailure("Error fetching meal plans: " + throwable.getMessage())
                );
    }

    @Override
    public void onRestore() {
        mealPlanRepo.fetchMealPlans()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealPlans -> {
                            for (MealPlan mealPlan : mealPlans) {
                                mealPlanRepo.insertOrUpdateMealPlan(mealPlan)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                                view::showRestoreSuccess,
                                                throwable -> view.showRestoreFailure(throwable.getMessage())
                                        );
                            }
                        },
                        throwable -> view.showRestoreFailure("Error fetching meal plans from Firebase: " + throwable.getMessage())
                );
    }
}