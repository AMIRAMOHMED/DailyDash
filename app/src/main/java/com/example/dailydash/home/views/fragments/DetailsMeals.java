package com.example.dailydash.home.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dailydash.R;
import com.example.dailydash.home.data.models.Ingredient;
import com.example.dailydash.home.data.models.Meals;
import com.example.dailydash.home.presenter.DetailsMealsPresenter;
import com.example.dailydash.home.views.adpoter.IngredientAdapter;
import com.example.dailydash.home.views.interfaces.DetailsMealsContract;
import com.example.dailydash.planer.data.Repository.MealPlanRepository;
import com.example.dailydash.planer.data.database.MealPlan;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;


public class DetailsMeals extends Fragment implements DetailsMealsContract.View {

    private DetailsMealsContract.Presenter presenter;
    private ImageButton favIcon;
    private ImageButton planIcon;
    private TextView textView,instructions;
    private ImageView imageView;
    private YouTubePlayerView videoView;
    MealPlanRepository mealPlanRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_meals, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.ingredientRecyclerView);
        textView = view.findViewById(R.id.mealTitle);
        favIcon = view.findViewById(R.id.favIcon);
        planIcon = view.findViewById(R.id.calenderIcon);
        instructions = view.findViewById(R.id.instructions);
        imageView = view.findViewById(R.id.mealImage);
        videoView = view.findViewById(R.id.videoView);
        mealPlanRepository = MealPlanRepository.getInstance(getContext());

        Context context = getContext();
        if (context != null) {
            presenter = new DetailsMealsPresenter(this, context);

            if (getArguments() != null) {
                DetailsMealsArgs args = DetailsMealsArgs.fromBundle(getArguments());
                String mealID = args.getMealId();
                presenter.fetchMealById(mealID); // Fetch the meal details
            }
        }
        return view;
    }

    @Override
    public void showMealDetails(Meals meal) {
        textView.setText(meal.getStrMeal());
        instructions.setText(meal.getStrInstructions());
        Glide.with(getContext()).load(meal.getStrMealThumb()).into(imageView);
        presenter.setupFavoriteIcon(meal, favIcon);

        if (meal.getStrYoutube() != null && !meal.getStrYoutube().isEmpty()) {
            getLifecycle().addObserver(videoView);
            videoView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.loadVideo(extractVideoId(meal.getStrYoutube()), 0);
                }
            });
        }

        List<Ingredient> ingredients = meal.getIngredients();
        RecyclerView recyclerView = getView().findViewById(R.id.ingredientRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        IngredientAdapter adapter = new IngredientAdapter(getContext(), ingredients);
        recyclerView.setAdapter(adapter);

        favIcon.setOnClickListener(v -> presenter.onFavoriteClicked(meal, favIcon));
        planIcon.setOnClickListener(v -> {
            MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select a date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();

            materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                long selectedDate = selection;
                presenter.onPlanIconClicked(meal, selectedDate);
            });
            materialDatePicker.show(getParentFragmentManager(), "DATE_PICKER");
        });
    }
    @Override
    public void showMealDialog(MealPlan existingMealPlan) {
        new AlertDialog.Builder(getContext())
                .setTitle("This Date Already Planned")
                .setMessage("A meal (" + existingMealPlan.getMealName() + ") has already been planned for this date.")
                .setIcon(R.drawable.test_image)
                .setPositiveButton("Delete", (dialog, which) -> presenter.deleteMealPlan(existingMealPlan))
                .setNegativeButton("Keep", null)
                .show();
    }

    @Override
    public void showAddedToPlanMessage() {
        Toast.makeText(getContext(), "Added to plan", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }



    private String extractVideoId(String youtubeUrl) {
        return youtubeUrl != null && youtubeUrl.contains("v=") ? youtubeUrl.split("v=")[1] : "";
    }

}
