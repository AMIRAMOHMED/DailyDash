package com.example.dailydash.authentication.login.presenter;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;


import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public interface LoginPresenter {
    void onSignUpClicked(String email, String password);
    void onGoogleSignInClicked(GoogleSignInAccount account);
    void configureGoogleSignIn(String clientId);
    GoogleSignInClient getGoogleSignInClient();
}
