package com.example.dailydash.home.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dailydash.R;
import com.example.dailydash.home.adpoter.CategoryListAdapter;
import com.example.dailydash.home.data.models.Category;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private CategoryListAdapter categoryListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.categoryRecycle); // Assuming your RecyclerView has the ID "recyclerView" in fragment_home.xml

        // Initialize test data
        ArrayList<Category> testCategories = new ArrayList<>();
        testCategories.add(new Category("Food", "R.drawable.chicken","",""));  // Example data
        testCategories.add(new Category("Travel","",""," R.drawable.chicken"));
        testCategories.add(new Category("","","Fitness", "R.drawable.chicke"));

        // Initialize the adapter with the test data
        categoryListAdapter = new CategoryListAdapter(testCategories);

        // Set up the RecyclerView with horizontal layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(categoryListAdapter);
        return view;
    }
}