package com.example.dailydash.authentication.login.presenter;

import com.example.dailydash.authentication.BaseView;
import com.example.dailydash.authentication.data.repo.Respiratory;

public class LoginPresenterImp implements  LoginPresenter{
    BaseView baseView;
    Respiratory repo;
    public LoginPresenterImp(BaseView baseView, Respiratory repo) {
        this.baseView = baseView;
        this.repo = repo;
    }
    public void onSignUpClicked(String email, String password) {
        if (
                validateInput(email, password)) {
            baseView.showProgress();
            repo.logIn(email, password, task -> {
                repo.addToPreferences(true);
                baseView.hideProgress();

                if (task.isSuccessful()) {
                    baseView.navigateToNextScreen();
                } else {
                    baseView.showError(task.getException().getMessage());
                }
            });
        }
    }

    private boolean validateInput(String email, String password) {
        if (email.isEmpty() || password.isEmpty() ) {
            baseView.showError("Fields cannot be empty");
            return false;
        }
        return true;
    }




}
