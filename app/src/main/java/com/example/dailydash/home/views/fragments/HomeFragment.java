package com.example.dailydash.home.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dailydash.R;
import com.example.dailydash.home.data.models.CategoriesResponse;
import com.example.dailydash.home.data.repo.Repository;
import com.example.dailydash.home.views.adpoter.CategoryItemAdaptor;
import com.example.dailydash.home.views.adpoter.CategoryListAdapter;
import com.example.dailydash.home.data.models.Category;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;

public class HomeFragment extends Fragment {

        private RecyclerView recyclerView2, recyclerView;
        private CategoryListAdapter categoryListAdapter;
        private CategoryItemAdaptor categoryItemAdaptor;
        private Repository repository;
        private CompositeDisposable compositeDisposable;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_home, container, false);

            // Initialize RecyclerView
            recyclerView = view.findViewById(R.id.categoryRecycle);
            recyclerView2 = view.findViewById(R.id.menuRecycle);

            // Initialize adapters with empty data
            categoryListAdapter = new CategoryListAdapter(new ArrayList<>());
            categoryItemAdaptor = new CategoryItemAdaptor(new ArrayList<>());

            // Set layout managers
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView2.setLayoutManager(layoutManager2);

            // Set adapters to the RecyclerViews
            recyclerView.setAdapter(categoryListAdapter);
            recyclerView2.setAdapter(categoryItemAdaptor);

            // Initialize repository and CompositeDisposable
            repository = Repository.getInstance();
            compositeDisposable = new CompositeDisposable();

            // Fetch categories from the repository
            fetchCategories();

            return view;
        }

        private void fetchCategories() {
            // Subscribe to getCategories observable and update RecyclerView on success
            compositeDisposable.add(repository.getCategories()
                    .observeOn(AndroidSchedulers.mainThread())  // Observe on main thread to update UI
                    .subscribeWith(new DisposableObserver<CategoriesResponse>() {
                        @Override
                        public void onNext(CategoriesResponse categoriesResponse) {
                            if (categoriesResponse != null && categoriesResponse.getCategories() != null) {
                                // Update adapter with fetched data
                                categoryListAdapter.updateCategories(categoriesResponse.getCategories());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            // Handle any errors, e.g., show a toast
                            Toast.makeText(getContext(), "Error fetching categories", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {
                            // Handle completion (optional)
                        }
                    }));
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            // Dispose of any active subscriptions when fragment is destroyed
            compositeDisposable.clear();
        }
    }