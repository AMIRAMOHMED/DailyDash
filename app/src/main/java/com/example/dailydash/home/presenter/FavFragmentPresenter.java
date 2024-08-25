package com.example.dailydash.home.presenter;

import com.example.dailydash.home.data.database.FavoriteMeal;
import com.example.dailydash.home.data.repo.Repository;
import com.example.dailydash.home.views.interfaces.FavFragmentContract;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;

public class FavFragmentPresenter implements FavFragmentContract.Presenter {
    private FavFragmentContract.View view;
    private Repository repository;
    private CompositeDisposable compositeDisposable;

    public FavFragmentPresenter(FavFragmentContract.View view, Repository repository) {
        this.view = view;
        this.repository = repository;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void fetchFavoriteMeals(String userId) {
        compositeDisposable.add(
                repository.getFavoriteMeals(userId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSubscriber<List<FavoriteMeal>>() {
                            @Override
                            public void onNext(List<FavoriteMeal> favoriteMeals) {
                                if (favoriteMeals != null && !favoriteMeals.isEmpty()) {
                                    view.showFavoriteMeals(favoriteMeals);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.showError("Error fetching favorite meals: " + e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                view.showCompletionMessage();
                            }
                        })
        );
    }

    @Override
    public void deleteMeal(FavoriteMeal meal) {
        compositeDisposable.add(
                repository.removeFavoriteMeal(meal)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> view.showMealDeletedMessage(meal),
                                throwable -> view.showError("Error deleting meal: " + throwable.getMessage()))
        );
    }


    @Override
    public void onDestroy() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}