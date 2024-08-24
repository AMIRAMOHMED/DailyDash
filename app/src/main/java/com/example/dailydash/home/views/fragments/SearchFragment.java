package com.example.dailydash.home.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailydash.R;
import com.example.dailydash.home.data.models.Area;
import com.example.dailydash.home.data.models.AreaResponse;
import com.example.dailydash.home.data.models.CategoriesResponse;
import com.example.dailydash.home.data.models.Category;
import com.example.dailydash.home.data.models.IngredientResponse;
import com.example.dailydash.home.data.models.IngredientS;
import com.example.dailydash.home.data.repo.Repository;
import com.example.dailydash.home.views.adpoter.SearchAdapter;
import com.google.android.material.chip.Chip;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;

public class SearchFragment extends Fragment {

    private RecyclerView searchRecyclerView;
    private Chip categories, country, ingredient;
    private Repository repo;
    private SearchAdapter adapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    EditText search;

    // Keep these as lists of the correct response type
    private List<Category> categoriesResponseList = new ArrayList<>();
    private List<Area> areaResponseList = new ArrayList<>();
    private List<IngredientS> ingredientResponseList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchRecyclerView = view.findViewById(R.id.recyclerView);
        categories = view.findViewById(R.id.categories);
        country = view.findViewById(R.id.countary);
        ingredient = view.findViewById(R.id.ingredient);

        search = view.findViewById(R.id.searchBar);
        repo = Repository.getInstance(getContext());

        // Initialize adapter
        adapter = new SearchAdapter(new ArrayList<>());
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchRecyclerView.setAdapter(adapter);

        setupChipListeners();
        observeSearchText();
        fetchIngredients();
        fetchAreas();
        fetchCategories();

        return view;
    }
    private void observeSearchText() {
        compositeDisposable.add(
                RxTextView.textChanges(search)
                        .debounce(300, TimeUnit.MILLISECONDS) // Add a debounce to avoid too many updates
                        .map(CharSequence::toString)
                        .distinctUntilChanged()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::filterResults)
        );
    }

    private void filterResults(String query) {
        List<Object> filteredList = new ArrayList<>();

        if (categories.isChecked()) {
            filteredList.addAll(filterCategories(query));
        } else if (country.isChecked()) {
            filteredList.addAll(filterAreas(query));
        } else if (ingredient.isChecked()) {
            filteredList.addAll(filterIngredients(query));
        }

        adapter.updateData(filteredList);
    }

    private List<Category> filterCategories(String query) {
        List<Category> filteredCategories = new ArrayList<>();
        for (Category category : categoriesResponseList) {
            if (category.getStrCategory().toLowerCase().startsWith(query.toLowerCase())) {
                filteredCategories.add(category);
            }
        }
        return filteredCategories;
    }

    private List<Area> filterAreas(String query) {
        List<Area> filteredAreas = new ArrayList<>();
        for (Area area : areaResponseList) {
            if (area.getStrArea().toLowerCase().startsWith(query.toLowerCase())) {
                filteredAreas.add(area);
            }
        }
        return filteredAreas;
    }

    private List<IngredientS> filterIngredients(String query) {
        List<IngredientS> filteredIngredients = new ArrayList<>();
        for (IngredientS ingredient : ingredientResponseList) {
            if (ingredient.getStrIngredient().toLowerCase().startsWith(query.toLowerCase())) {
                filteredIngredients.add(ingredient);
            }
        }
        return filteredIngredients;
    }

    private void fetchIngredients() {
        compositeDisposable.add(
                repo.getIngredients()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<IngredientResponse>() {

                            @Override
                            public void onNext(@NonNull IngredientResponse ingredientResponse) {
                                if (ingredientResponse != null && ingredientResponse.getIngredients() != null) {
                                    ingredientResponseList = ingredientResponse.getIngredients();

                                    adapter.updateData(new ArrayList<>(ingredientResponseList));
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(getContext(), "Error fetching ingredients", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {

                            }
                        })
        );
    }

    private void fetchAreas() {
        compositeDisposable.add(
                repo.getAreas()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<AreaResponse>() {


                            @Override
                            public void onNext(@NonNull AreaResponse areaResponse) {
                                if (areaResponse != null && areaResponse.getMeals() != null) {
                                    areaResponseList = areaResponse.getMeals();
                                    adapter.updateData(new ArrayList<>(areaResponseList));
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(getContext(), "Error fetching areas", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {

                            }
                        })
        );
    }

    private void fetchCategories() {
        compositeDisposable.add(
                repo.getCategories()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<CategoriesResponse>() {

                            @Override
                            public void onNext(@NonNull CategoriesResponse categoriesResponse) {

                                if (categoriesResponse != null && categoriesResponse.getCategories() != null) {
                                    categoriesResponseList = categoriesResponse.getCategories();
                                    adapter.updateData(new ArrayList<>(categoriesResponseList));
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(getContext(), "Error fetching categories", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {

                            }
                        })
        );
    }

    private void setupChipListeners() {
        categories.setOnClickListener(v -> {
            if (categories.isChecked()) {
                adapter.updateData(new ArrayList<>(categoriesResponseList));
            }
        });

        country.setOnClickListener(v -> {
            if (country.isChecked()) {
                adapter.updateData(new ArrayList<>(areaResponseList));
            }
        });

        ingredient.setOnClickListener(v -> {
            if (ingredient.isChecked()) {
                adapter.updateData(new ArrayList<>(ingredientResponseList));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear(); // Clear disposables when view is destroyed
    }
}