package com.example.dailydash.authentication.register.presenter;

public interface RegisterPresenter {
    void onSignUpClicked(String email, String password, String confirmPassword);

    void onSignInClicked();
    void onSignUpClicked();
}
