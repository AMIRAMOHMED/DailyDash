package com.example.dailydash.home.views.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.dailydash.R;
import com.example.dailydash.authentication.data.repo.AuthenticationRepository;
import com.example.dailydash.authentication.login.views.LoginActivity;
import com.example.dailydash.planer.data.Repository.MealPlanRepository;
import com.example.dailydash.planer.data.database.MealPlan;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
public class ProfileFragment extends Fragment {
    TextView logout, restore, backup;
    AuthenticationRepository authrepo;
    MealPlanRepository mealPlanRepo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        logout = view.findViewById(R.id.logout);
        restore = view.findViewById(R.id.restore);
        backup = view.findViewById(R.id.backpu);
        authrepo = AuthenticationRepository.getInstance(this.getContext());
        mealPlanRepo = MealPlanRepository.getInstance(this.getContext());

        logout.setOnClickListener(v -> {
            authrepo.logOut();
            authrepo.addLoginToPreferences(false);
            authrepo.deleteUserIdFromPreferences();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        // Backup button click listener
        backup.setOnClickListener(v -> {
            mealPlanRepo.getMealPlansByUser(authrepo.readUserIdFromPreferences())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            mealPlans -> {
                                for (MealPlan mealPlan : mealPlans) {
                                    mealPlanRepo.addMealPlan(mealPlan)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(
                                                    () -> Toast.makeText(getContext(), "Backup successful!", Toast.LENGTH_SHORT).show(),
                                                    throwable -> Toast.makeText(getContext(), "Backup failed: " + throwable.getMessage(), Toast.LENGTH_SHORT).show()
                                            );
                                }
                            },
                            throwable -> Toast.makeText(getContext(), "Error fetching meal plans: " + throwable.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        });

        // Restore button click listener
        restore.setOnClickListener(v -> {
            mealPlanRepo.fetchMealPlans()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            mealPlans -> {
                                for (MealPlan mealPlan : mealPlans) {
                                    mealPlanRepo.insertOrUpdateMealPlan(mealPlan)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(
                                                    () -> Toast.makeText(getContext(), "Restore successful!", Toast.LENGTH_SHORT).show(),
                                                    throwable -> {
                                                        Log.i("profile", ": "+throwable.getMessage());
                                                        Toast.makeText(getContext(), "Restore failed: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                            );
                                }
                            },
                            throwable -> Toast.makeText(getContext(), "Error fetching meal plans from Firebase: " + throwable.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        });

        return view;
    }
}
