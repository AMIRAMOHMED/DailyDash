package com.example.dailydash.home.presenter;


import com.example.dailydash.home.data.models.MealsResponse;
import com.example.dailydash.home.data.repo.Repository;
import com.example.dailydash.home.views.interfaces.MealsFragmentInterface;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;

public class MealsPresenter {
    private final MealsFragmentInterface view;
    private final Repository repository;
    private final CompositeDisposable compositeDisposable;

    public MealsPresenter(MealsFragmentInterface view, Repository repository) {
        this.view = view;
        this.repository = repository;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void fetchMeals(String choosensearch, String specifiedType) {
        switch (choosensearch) {
            case "IngredientS":
                fetchIngredients(specifiedType);
                break;
            case "Area":
                fetchAreas(specifiedType);
                break;
            case "Category":
                fetchCategories(specifiedType);
                break;
            default:
                view.showError("Unknown search type: " + choosensearch);
                break;
        }
    }

    private void fetchCategories(String specifiedType) {
        compositeDisposable.add(repository.getMealsByCategory(specifiedType)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MealsResponse>() {
                    @Override
                    public void onNext(MealsResponse mealsResponse) {
                        if (mealsResponse != null && mealsResponse.getMeals() != null) {
                            view.showMeals(mealsResponse.getMeals());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError("Error fetching categories: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        // Optional: Handle completion
                    }
                }));
    }

    private void fetchAreas(String specifiedType) {
        compositeDisposable.add(repository.getMealsByArea(specifiedType)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MealsResponse>() {
                    @Override
                    public void onNext(MealsResponse mealsResponse) {
                        if (mealsResponse != null && mealsResponse.getMeals() != null) {
                            view.showMeals(mealsResponse.getMeals());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError("Error fetching areas: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        // Optional: Handle completion
                    }
                }));
    }

    private void fetchIngredients(String specifiedType) {
        compositeDisposable.add(repository.getMealsByIngredient(specifiedType)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MealsResponse>() {
                    @Override
                    public void onNext(MealsResponse mealsResponse) {
                        if (mealsResponse != null && mealsResponse.getMeals() != null) {
                            view.showMeals(mealsResponse.getMeals());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError("Error fetching ingredients: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        // Optional: Handle completion
                    }
                }));
    }

    public void onDestroy() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
