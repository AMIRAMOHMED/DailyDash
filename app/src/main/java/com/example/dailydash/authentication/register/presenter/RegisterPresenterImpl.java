package com.example.dailydash.authentication.register.presenter;

import com.example.dailydash.authentication.data.repo.Respiratory;
import com.example.dailydash.authentication.BaseView;

public class RegisterPresenterImpl implements RegisterPresenter {
    BaseView baseView;
    Respiratory repo;

    public RegisterPresenterImpl(BaseView baseView, Respiratory repo) {
        this.baseView = baseView;
        this.repo = repo;
    }




    @Override
    public void onSignUpClicked(String email, String password, String confirmPassword) {
        if (validateInput(email, password, confirmPassword)) {
            baseView.showProgress();
            repo.singUp(email, password, task -> {
                baseView.hideProgress();
                if (task.isSuccessful()) {
                    baseView.navigateToNextScreen();
                } else {
                    baseView.showError(task.getException().getMessage());
                }
            });
        }
    }

    private boolean validateInput(String email, String password, String confirmPassword) {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            baseView.showError("Fields cannot be empty");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            baseView.showError("Passwords do not match");
            return false;
        }
        return true;
    }

    @Override
    public void onSignInClicked() {
        baseView.navigateToNextScreen();

    }

    @Override
    public void onSignUpClicked() {
        baseView.navigateToNextScreen();


    }
}