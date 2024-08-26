package com.example.dailydash.home.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailydash.R;
import com.example.dailydash.home.data.models.Meals;
import com.example.dailydash.home.presenter.MealsPresenter;
import com.example.dailydash.home.views.adpoter.MealItemAdaptor;
import com.example.dailydash.home.data.repo.Repository;
import com.example.dailydash.home.views.interfaces.MealsFragmentInterface;

import java.util.ArrayList;
import java.util.List;


public class MealsFragment extends Fragment implements MealsFragmentInterface , MealItemAdaptor.OnCookNowClickListener  {

    private MealItemAdaptor mealItemAdaptor;
    private MealsPresenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meals, container, false);

        RecyclerView mealsRecyclerView = view.findViewById(R.id.mealRecycler);

        LinearLayoutManager mealsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mealsRecyclerView.setLayoutManager(mealsLayoutManager);
        mealItemAdaptor = new MealItemAdaptor(new ArrayList<>(), this);
        mealsRecyclerView.setAdapter(mealItemAdaptor);

        Repository repository = Repository.getInstance(this.getContext());
        presenter = new MealsPresenter(this, repository);

        if (getArguments() != null) {
            String choosensearch = getArguments().getString("choosensearch");
            String specifiedType = getArguments().getString("specifiedType");

            Log.d("MealsFragment", "Choosen Search: " + choosensearch);
            Log.d("MealsFragment", "Specified Type: " + specifiedType);

            presenter.fetchMeals(choosensearch, specifiedType);
        }

        return view;
    }

    @Override
    public void showMeals(List<Meals> meals) {
        mealItemAdaptor.updateMeals(meals);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCookNowClicked(Meals meal) {
        MealsFragmentDirections.ActionMealsFragmentToDetailsMeals action =
                MealsFragmentDirections.actionMealsFragmentToDetailsMeals(meal.getIdMeal());
        Navigation.findNavController(requireView()).navigate(action);
    }
}