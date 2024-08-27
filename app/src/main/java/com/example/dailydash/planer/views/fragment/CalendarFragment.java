package com.example.dailydash.planer.views.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailydash.R;
import com.example.dailydash.authentication.data.repo.AuthenticationRepository;
import com.example.dailydash.home.views.Utility.CustomAlertDialogFragment;
import com.example.dailydash.planer.data.Repository.MealPlanRepository;
import com.example.dailydash.planer.data.database.MealPlan;
import com.example.dailydash.planer.presenter.CalendarPresenter;
import com.example.dailydash.planer.views.adpoters.PlanedMealAdapter;
import com.example.dailydash.planer.views.interfaces.CalendarContract;

import java.util.ArrayList;
import java.util.List;

public class CalendarFragment extends Fragment implements CalendarContract.View, PlanedMealAdapter.OnDeleteClickListener, CustomAlertDialogFragment.CustomAlertDialogListener {

    private CalendarContract.Presenter presenter;
    private RecyclerView recyclerView;
    private PlanedMealAdapter planedMealAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        MealPlanRepository mealPlanRepository = MealPlanRepository.getInstance(getContext());
        AuthenticationRepository authRepository = AuthenticationRepository.getInstance(getContext());
        presenter = new CalendarPresenter(mealPlanRepository, authRepository);
        presenter.attachView(this);

        recyclerView = view.findViewById(R.id.planedmealrecycler);
        planedMealAdapter = new PlanedMealAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(planedMealAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void showMealPlans(List<MealPlan> mealPlans) {
        planedMealAdapter.updateData(mealPlans);
    }

    @Override
    public void showLoading() {
        // Show loading indicator (e.g., a ProgressBar)
    }

    @Override
    public void hideLoading() {
        // Hide loading indicator
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToLogin() {
        CustomAlertDialogFragment alertDialog = CustomAlertDialogFragment.newInstance(
                "Please log in to can made your plane",
                R.drawable.login,
                "Login",
                "Explore as Guest"
        );
        alertDialog.show(getChildFragmentManager(), "CustomAlertDialogFragment");
    }

    @Override
    public void showDeleteConfirmation(MealPlan mealPlan) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Meal Plan")
                .setMessage("Are you sure you want to delete this meal plan?")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> presenter.deleteMealPlan(mealPlan))
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    @Override
    public void showMealDeletedMessage() {
        Toast.makeText(getContext(), "Meal plan deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClicked(MealPlan mealPlan) {
        showDeleteConfirmation(mealPlan);
    }

    @Override
    public void onPositiveButtonClick() {
        presenter.onLoginClick();}

    @Override
    public void onNegativeButtonClick() {

    }
}