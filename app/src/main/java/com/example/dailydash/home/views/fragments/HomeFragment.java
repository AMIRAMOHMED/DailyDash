package com.example.dailydash.home.views.fragments;

import static android.content.ContentValues.TAG;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.dailydash.R;
import com.example.dailydash.home.data.models.CategoriesResponse;
import com.example.dailydash.home.data.models.Category;
import com.example.dailydash.home.data.models.MealsResponse;
import com.example.dailydash.home.data.repo.Repository;
import com.example.dailydash.home.views.adpoter.MealItemAdaptor;
import com.example.dailydash.home.views.adpoter.CategoryListAdapter;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;

public class HomeFragment extends Fragment implements CategoryListAdapter.CategoryClickListener {

    private RecyclerView categoryRecyclerView, mealsRecyclerView;
    private CategoryListAdapter categoryListAdapter;
    private MealItemAdaptor mealItemAdaptor;
    private Repository repository;
    private CompositeDisposable compositeDisposable;
    private FrameLayout constraintLayout;
    private TextView nameOfMeal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        categoryRecyclerView = view.findViewById(R.id.categoryRecycle);
        mealsRecyclerView = view.findViewById(R.id.menuRecycle);
        constraintLayout = view.findViewById(R.id.constraintLayoutRandom);
        nameOfMeal = view.findViewById(R.id.nameOfMeal);


        // Initialize adapters with empty data
        categoryListAdapter = new CategoryListAdapter(new ArrayList<>(), this); // 'this' for the click listener
        mealItemAdaptor = new MealItemAdaptor(new ArrayList<>(),this);

        // Set layout managers
        LinearLayoutManager categoryLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager mealsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        categoryRecyclerView.setLayoutManager(categoryLayoutManager);
        mealsRecyclerView.setLayoutManager(mealsLayoutManager);

        // Set adapters
        categoryRecyclerView.setAdapter(categoryListAdapter);
        mealsRecyclerView.setAdapter(mealItemAdaptor);

        // Initialize repository and CompositeDisposable
        repository = Repository.getInstance(this.getContext());
        compositeDisposable = new CompositeDisposable();

        // Fetch categories
        fetchCategories();
        getRandomMeal();
        return view;
    }

    private void fetchMealsByCategory(String category) {
        compositeDisposable.add(repository.getMealsByCategory(category)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MealsResponse>() {
                    @Override
                    public void onNext(MealsResponse mealsResponse) {
                        if (mealsResponse != null && mealsResponse.getMeals() != null) {
                            mealItemAdaptor.updateMeals(mealsResponse.getMeals());
                        } else {
                            mealItemAdaptor.updateMeals(new ArrayList<>()); // Clear the list if no meals are found
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("HomeFragment", "onError: "+ e);
                        Toast.makeText(getContext(), "Error fetching meals", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        // Optional: Handle completion
                    }
                }));
    }

    private void fetchCategories() {
        compositeDisposable.add(repository.getCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CategoriesResponse>() {
                    @Override
                    public void onNext(CategoriesResponse categoriesResponse) {
                        if (categoriesResponse != null && categoriesResponse.getCategories() != null) {
                            categoryListAdapter.updateCategories(categoriesResponse.getCategories());

                            // Set "Beef" as the default category and load meals
                            fetchMealsByCategory("Beef");

                            // Optionally, highlight "Beef" in the category RecyclerView
                            int beefPosition = findCategoryPosition("Beef", categoriesResponse.getCategories());
                            if (beefPosition != -1) {
                                categoryListAdapter.setSelectedPosition(beefPosition);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "Error fetching categories", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        // Optional: Handle completion
                    }
                }));
    }

    // Helper method to find the position of "Beef" in the category list
    private int findCategoryPosition(String category, List<Category> categories) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getStrCategory().equals(category)) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public void onCategoryClick(String category) {
        fetchMealsByCategory(category);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear(); // Avoid memory leaks
    }

    private void getRandomMeal() {
        compositeDisposable.add(repository.getRandomMeal()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MealsResponse>() {
                    @Override
                    public void onNext(MealsResponse mealsResponse) {
                        if (mealsResponse != null && mealsResponse.getMeals() != null) {
                            nameOfMeal.setText(mealsResponse.getMeals().get(0).getStrMeal());
                            constraintLayout.setOnClickListener(v -> {
                                com.example.dailydash.home.views.fragments.HomeFragmentDirections.ActionHomeFragmentToDetailsMeals action =
                                        HomeFragmentDirections.actionHomeFragmentToDetailsMeals(mealsResponse.getMeals().get(0));
                                Navigation.findNavController(requireView()).navigate(action);
                            });

                            Glide.with(requireContext()) // Using requireContext() to get the Fragment's context
                                    .load(mealsResponse.getMeals().get(0).getStrMealThumb()) // Correct image URL loading
                                    .into(new CustomTarget<Drawable>() {
                                        @Override
                                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                            constraintLayout.setBackground(resource); // Set the background of the correct constraint layout
                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {
                                            // Handle placeholder or null cases if needed
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "Error fetching random meal", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        // Optional: Handle completion
                    }
                }));
    }


}