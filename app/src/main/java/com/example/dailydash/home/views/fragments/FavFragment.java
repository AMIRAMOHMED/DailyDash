package com.example.dailydash.home.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailydash.R;
import com.example.dailydash.authentication.data.repo.AuthenticationRepository;
import com.example.dailydash.home.data.database.FavoriteMeal;
import com.example.dailydash.home.data.repo.Repository;
import com.example.dailydash.home.presenter.FavFragmentPresenter;
import com.example.dailydash.home.views.adpoter.FavItemAdapter;
import com.example.dailydash.home.views.interfaces.FavFragmentContract;

import java.util.ArrayList;
import java.util.List;

public class FavFragment extends Fragment implements FavFragmentContract.View {
    RecyclerView recyclerView;
    FavItemAdapter mealItemAdaptor;
    private AuthenticationRepository authReop;
    private FavFragmentContract.Presenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav, container, false);
        Repository repository = Repository.getInstance(getContext());
        presenter = new FavFragmentPresenter(this, repository);
        recyclerView = view.findViewById(R.id.favMealRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mealItemAdaptor = new FavItemAdapter(new ArrayList<>(),presenter);
        recyclerView.setAdapter(mealItemAdaptor);

        authReop = AuthenticationRepository.getInstance(getContext());
        String userId = authReop.readUserIdFromPreferences();



        presenter.fetchFavoriteMeals(userId);

        return view;
    }

    @Override
    public void showFavoriteMeals(List<FavoriteMeal> favoriteMeals) {
        mealItemAdaptor.updateMeals(favoriteMeals);
        Toast.makeText(getContext(), "Favorite list fetched", Toast.LENGTH_SHORT).show();
    }
    public void showMealDeletedMessage(FavoriteMeal meal) {
        Toast.makeText(getContext(), "Meal deleted", Toast.LENGTH_SHORT).show();
        presenter.fetchFavoriteMeals(authReop.readUserIdFromPreferences());
    }
    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCompletionMessage() {
        Toast.makeText(getContext(), "Favorite list fetched", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}