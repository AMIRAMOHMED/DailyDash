package com.example.dailydash.home.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.dailydash.R;
import com.example.dailydash.authentication.login.views.LoginActivity;
import com.example.dailydash.home.presenter.ProfilePresenter;
import com.example.dailydash.home.views.interfaces.ProfileContract;

public class ProfileFragment extends Fragment implements ProfileContract.View {
    private TextView logout, restore, backup;
    private ProfileContract.Presenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        logout = view.findViewById(R.id.logout);
        restore = view.findViewById(R.id.restore);
        backup = view.findViewById(R.id.backpu);

        presenter = new ProfilePresenter(this, getContext());

        logout.setOnClickListener(v -> presenter.onLogout());
        backup.setOnClickListener(v -> presenter.onBackup());
        restore.setOnClickListener(v -> presenter.onRestore());

        return view;
    }

    @Override
    public void showBackupSuccess() {
        Toast.makeText(getContext(), "Backup successful!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showBackupFailure(String message) {
        Toast.makeText(getContext(), "Backup failed: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRestoreSuccess() {
//        Toast.makeText(getContext(), "Restore successful!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRestoreFailure(String message) {
        Toast.makeText(getContext(), "Restore failed: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}