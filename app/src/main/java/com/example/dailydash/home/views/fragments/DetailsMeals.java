package com.example.dailydash.home.views.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailydash.R;
import com.example.dailydash.home.data.database.FavoriteMeal;
import com.example.dailydash.home.data.models.Ingredient;
import com.example.dailydash.home.data.models.Meals;
import com.example.dailydash.home.data.repo.Repository;
import com.example.dailydash.home.views.Utility.FavoriteUtils;
import com.example.dailydash.home.views.adpoter.IngredientAdapter;
import com.example.dailydash.planer.data.Repository.MealPlanRepository;
import com.example.dailydash.planer.data.database.MealPlan;
import com.google.android.material.datepicker.MaterialDatePicker;

import androidx.appcompat.app.AlertDialog;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import io.reactivex.rxjava3.observers.DisposableMaybeObserver;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailsMeals extends Fragment {

    private CompositeDisposable compositeDisposable;
    private MealPlanRepository mealPlanRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_meals, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.ingredientRecyclerView);
        TextView textView = view.findViewById(R.id.mealTitle);
        ImageButton favIcon = view.findViewById(R.id.favIcon);
        ImageButton planIcon = view.findViewById(R.id.calenderIcon);

        compositeDisposable = new CompositeDisposable();
       Repository repository = Repository.getInstance(getContext());

        if (getArguments() != null) {
            DetailsMealsArgs args = DetailsMealsArgs.fromBundle(getArguments());
            Meals meal = args.getMeal();
            Log.i("DetailsMeals", "onCreateView: " + meal.getIdMeal());
            textView.setText(meal.getStrMeal());

            Context context = getContext();
            if (context != null) {
                mealPlanRepository = MealPlanRepository.getInstance(context);

                List<Ingredient> ingredients = meal.getIngredients();
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                IngredientAdapter adapter = new IngredientAdapter(context, ingredients);
                recyclerView.setAdapter(adapter);

                String userId = "hbjh";
                FavoriteMeal favMeal = new FavoriteMeal(meal.getStrMeal(), meal.getStrMealThumb(), userId, meal.getIdMeal());
                favIcon.setOnClickListener(v -> FavoriteUtils.toggleFavorite(favIcon, favMeal, repository, context));

                planIcon.setOnClickListener(v -> {
                    MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                            .setTitleText("Select a date")
                            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                            .build();

                    materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                        long selectedDate = selection;
                        String date = new java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault()).format(new java.util.Date(selectedDate));
                        compositeDisposable.add(
                                mealPlanRepository.getMealPlanByDate(date)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribeWith(new DisposableMaybeObserver<MealPlan>() {
                                            @Override
                                            public void onSuccess(MealPlan existingMealPlan) {
                                                // Handle the case where a meal plan exists for the selected date
                                                showMealDialog(existingMealPlan);
                                            }

                                            @Override
                                            public void onComplete() {
                                                // Handle the case where no meal plan exists for the selected date
                                                addMealToPlan(context, meal, date);
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Log.e("DetailsMeals", "Error fetching meal plan", e);
                                            }}  ));

                    });

                    materialDatePicker.show(getParentFragmentManager(), "DATE_PICKER");
                });
            }
        }
        return view;
    }

    private void showMealDialog(MealPlan existingMealPlan) {
        new AlertDialog.Builder(getContext())
                .setTitle("This Date Already Planned")
                .setMessage("A meal (" + existingMealPlan.getMealName() + ") has already been planned for this date.")
                .setIcon(R.drawable.test_image)
                .setPositiveButton("Delete", (dialog, which) -> deleteMealPlan(existingMealPlan))
                .setNegativeButton("Keep", null)
                .show();
    }

    private void deleteMealPlan(MealPlan mealPlan) {
        compositeDisposable.add(
                mealPlanRepository.deleteMealPlan(mealPlan)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                Toast.makeText(getContext(), "Meal plan deleted", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("DetailsMeals", "Error deleting meal plan", e);
                                Toast.makeText(getContext(), "Error deleting meal plan", Toast.LENGTH_SHORT).show();
                            }
                        })
        );
    }

    private void addMealToPlan(Context context, Meals meal, String date) {
        MealPlan mealPlan = new MealPlan(meal.getIdMeal(), meal.getStrMealThumb(), meal.getStrMeal(), date, "hbjh");

        compositeDisposable.add(
                mealPlanRepository.insertMealPlan(mealPlan)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                Toast.makeText(context, "Added to plan", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("DetailsMeals", "Error adding meal to plan", e);
                                Toast.makeText(context, "Error adding meal to plan", Toast.LENGTH_SHORT).show();
                            }
                        })
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }
}
