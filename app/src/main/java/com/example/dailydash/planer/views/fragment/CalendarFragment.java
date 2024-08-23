package com.example.dailydash.planer.views.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.dailydash.R;
import com.example.dailydash.planer.data.Repository.MealPlanRepository;
import com.example.dailydash.planer.data.database.MealPlan;
import com.example.dailydash.planer.views.adpoters.PlanedMealAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
public class CalendarFragment extends Fragment implements PlanedMealAdapter.OnDeleteClickListener {

    private CalendarView calendarView;
    private List<MealPlan> myPlans;
    private MealPlanRepository mealPlanRepository;
    private CompositeDisposable compositeDisposable;
    private RecyclerView recyclerView;
    private PlanedMealAdapter planedMealAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarView = view.findViewById(R.id.calenderView); // Corrected spelling
        recyclerView = view.findViewById(R.id.planedmealrecycler);

        mealPlanRepository = MealPlanRepository.getInstance(getContext());
        compositeDisposable = new CompositeDisposable();

        planedMealAdapter = new PlanedMealAdapter(new ArrayList<>(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(planedMealAdapter);

        compositeDisposable.add(
                mealPlanRepository.getAllMealPlans()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(mealPlans -> {
                            Collections.sort(mealPlans, (meal1, meal2) -> meal1.getDate().compareTo(meal2.getDate()));
                            return mealPlans;
                        })
                        .subscribe(
                                mealPlans -> {
                                    myPlans = mealPlans;
                                    planedMealAdapter.updateData(myPlans);
                                    Log.i("calendar", "Meal plans fetched and sorted: " + myPlans);
                                },
                                throwable -> {
                                    // Handle error here
                                    Log.e("calendar", "Error fetching meal plans", throwable);
                                }
                        )
        );


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    @Override
    public void onDeleteClicked(MealPlan mealPlan) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Meal Plan")
                .setMessage("Are you sure you want to delete this meal plan?")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    compositeDisposable.add(
                            mealPlanRepository.deleteMealPlan(mealPlan)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeWith(new DisposableCompletableObserver() {
                                        @Override
                                        public void onComplete() {
                                            // Handle successful deletion
                                            Toast.makeText(getContext(), "Meal plan deleted", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            Log.e("CalendarFragment", "Error deleting meal plan", e);
                                            Toast.makeText(getContext(), "Error deleting meal plan", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                    );
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

}
