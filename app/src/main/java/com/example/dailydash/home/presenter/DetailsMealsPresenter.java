package com.example.dailydash.home.presenter;


import android.content.Context;
import android.util.Log;
import android.widget.ImageButton;

import androidx.core.content.ContextCompat;

import com.example.dailydash.R;
import com.example.dailydash.authentication.data.repo.AuthenticationRepository;
import com.example.dailydash.home.data.database.FavoriteMeal;
import com.example.dailydash.home.data.models.Meals;
import com.example.dailydash.home.data.repo.Repository;
import com.example.dailydash.home.views.Utility.FavoriteUtils;
import com.example.dailydash.home.views.interfaces.DetailsMealsContract;
import com.example.dailydash.planer.data.Repository.MealPlanRepository;
import com.example.dailydash.planer.data.database.MealPlan;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import io.reactivex.rxjava3.observers.DisposableMaybeObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailsMealsPresenter implements DetailsMealsContract.Presenter {
private  Context context;
    private DetailsMealsContract.View view;
    private MealPlanRepository mealPlanRepository;
    private Repository repository;
    private CompositeDisposable compositeDisposable;
    private AuthenticationRepository authReop;


    public DetailsMealsPresenter(DetailsMealsContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.mealPlanRepository = MealPlanRepository.getInstance(context);
        this.repository = Repository.getInstance(context);
        this.compositeDisposable = new CompositeDisposable();
        this.authReop = AuthenticationRepository.getInstance(context);
    }

    @Override
    public void onFavoriteClicked(Meals meal, ImageButton favIcon) {
        String userId = authReop.readUserIdFromPreferences();

        FavoriteMeal favMeal = new FavoriteMeal(meal.getStrMeal(), meal.getStrMealThumb(), userId, meal.getIdMeal());
        FavoriteUtils.toggleFavorite(favIcon, favMeal, repository, context); // Use context here

    }





    @Override
public void onPlanIconClicked(Meals meal, long selectedDate) {
    String date = new java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault()).format(new java.util.Date(selectedDate));
    compositeDisposable.add(
            mealPlanRepository.getMealPlanByDate(date)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableMaybeObserver<MealPlan>() {
                        @Override
                        public void onSuccess(MealPlan existingMealPlan) {
                            view.showMealDialog(existingMealPlan);
                        }

                        @Override
                        public void onComplete() {
                            addMealToPlan(meal, date);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("DetailsMealsPresenter", "Error fetching meal plan", e);
                            view.showError("Error fetching meal plan");
                        }
                    })
    );
}
@Override
    public void setupFavoriteIcon(Meals meal, ImageButton favIcon) {
        String userId = authReop.readUserIdFromPreferences();
        FavoriteMeal favMeal = new FavoriteMeal(meal.getStrMeal(), meal.getStrMealThumb(), userId, meal.getIdMeal());

        repository.isFavoriteMeal(favMeal.getMealId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isFavorite -> {
                    if (isFavorite) {
                        favIcon.setColorFilter(ContextCompat.getColor(context, R.color.red));
                    } else {
                        favIcon.setColorFilter(ContextCompat.getColor(context, R.color.LightGreen));
                    }
                });
    }

private void addMealToPlan(Meals meal, String date) {
    String userId = authReop.readUserIdFromPreferences();

    MealPlan mealPlan = new MealPlan(meal.getIdMeal(), meal.getStrMealThumb(), meal.getStrMeal(), date, userId);

    compositeDisposable.add(
            mealPlanRepository.insertMealPlan(mealPlan)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableCompletableObserver() {
                                @Override
                                public void onComplete() {
                                    view.showAddedToPlanMessage();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e("DetailsMealsPresenter", "Error adding meal to plan", e);
                                    view.showError("Error adding meal to plan");
                                }
                            })
            );
}

public void deleteMealPlan(MealPlan mealPlan) {
    compositeDisposable.add(
            mealPlanRepository.deleteMealPlan(mealPlan)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableCompletableObserver() {
                        @Override
                        public void onComplete() {
                            view.showAddedToPlanMessage();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("DetailsMealsPresenter", "Error deleting meal plan", e);
                            view.showError("Error deleting meal plan");
                        }
                    })
    );
}

public void clear() {
    if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
        compositeDisposable.clear();
    }
}


}