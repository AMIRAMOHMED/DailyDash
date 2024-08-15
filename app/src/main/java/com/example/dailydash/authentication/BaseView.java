package com.example.dailydash.authentication;

public interface BaseView {
    void showProgress();
    void hideProgress();
    void showError(String message);
    void navigateToNextScreen();
}