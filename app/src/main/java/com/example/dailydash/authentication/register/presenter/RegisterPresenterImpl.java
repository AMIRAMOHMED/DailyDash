package com.example.dailydash.authentication.register.presenter;

import android.content.Context;
import android.util.Log;

import com.example.dailydash.authentication.data.repo.AuthenticationRepository;
import com.example.dailydash.authentication.BaseView;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPresenterImpl implements RegisterPresenter {
    BaseView baseView;
    AuthenticationRepository repo;

    public RegisterPresenterImpl(BaseView baseView, Context context) {
        this.baseView = baseView;
        this.repo = AuthenticationRepository.getInstance(context);
    }




    @Override
    public void onSignUpClicked(String email, String password, String confirmPassword) {
        if (validateInput(email, password, confirmPassword)) {
            baseView.showProgress();
            repo.singUp(email, password, task -> {
                baseView.hideProgress();
                if (task.isSuccessful()) {
                    baseView.navigateToNextScreen();
                    repo.addLoginToPreferences(true);
                    String uid = FirebaseAuth.getInstance().getUid();
                    repo.addedUserIdToPreferences(uid);
                    Log.i("Firebaseuid", "onSignUpClicked: "+uid);
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