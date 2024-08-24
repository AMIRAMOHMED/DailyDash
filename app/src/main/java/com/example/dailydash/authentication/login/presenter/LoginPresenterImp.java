package com.example.dailydash.authentication.login.presenter;

import android.content.Context;

import com.example.dailydash.authentication.BaseView;
import com.example.dailydash.authentication.data.repo.AuthenticationRepository;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenterImp implements  LoginPresenter{
    BaseView baseView;
    AuthenticationRepository repo;
    public LoginPresenterImp(BaseView baseView, Context context ) {
        this.baseView = baseView;
        this.repo = AuthenticationRepository.getInstance(context);
    }
    public void onSignUpClicked(String email, String password) {
        if (
                validateInput(email, password)) {
            baseView.showProgress();
            repo.logIn(email, password, task -> {
                baseView.hideProgress();

                if (task.isSuccessful()) {
                    repo.addLoginToPreferences(true);

                    String uid = FirebaseAuth.getInstance().getUid();
                    repo.addedUserIdToPreferences(uid);
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
