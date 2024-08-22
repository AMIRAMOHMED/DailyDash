package com.example.dailydash.home.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.dailydash.R;
import com.example.dailydash.home.data.models.Meals;
import com.example.dailydash.home.data.repo.Repository;
import com.example.dailydash.home.views.adpoter.CategoryListAdapter;
import com.example.dailydash.home.views.adpoter.MealItemAdaptor;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MealsFragment extends Fragment {

    private RecyclerView  mealsRecyclerView;
    private MealItemAdaptor mealItemAdaptor;
    private Repository repository;
    private CompositeDisposable compositeDisposable;
    private TextView nameOfMeal,cookNow;


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mealsRecyclerView = view.findViewById(R.id.menuRecycle);
        LinearLayoutManager mealsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mealsRecyclerView.setLayoutManager(mealsLayoutManager);
        mealsRecyclerView.setAdapter(mealItemAdaptor);
        repository = Repository.getInstance(this.getContext());
        compositeDisposable = new CompositeDisposable();

        if (getArguments() != null) {
            String choosensearch = getArguments().getString("choosensearch");
            String specifiedType = getArguments().getString("specifiedType");

            Log.d("MealsFragment", "Choosen Search: " + choosensearch);
            Log.d("MealsFragment", "Specified Type: " + specifiedType);
        }

        return view;
    }

    if(choosensearch.equals("search")) {
        fetchMeals(specifiedType);
    }
}