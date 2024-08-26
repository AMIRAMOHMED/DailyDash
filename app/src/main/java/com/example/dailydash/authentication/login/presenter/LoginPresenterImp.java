package com.example.dailydash.authentication.login.presenter;

import android.content.Context;

import com.example.dailydash.authentication.BaseView;
import com.example.dailydash.authentication.data.repo.AuthenticationRepository;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenterImp implements LoginPresenter {
    private BaseView baseView;
    private AuthenticationRepository repo;
    private GoogleSignInClient googleSignInClient;

    public LoginPresenterImp(BaseView baseView, Context context) {
        this.baseView = baseView;
        this.repo = AuthenticationRepository.getInstance(context);
    }

    @Override
    public void configureGoogleSignIn(String clientId) {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientId)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient((Context) baseView, options);
    }

    @Override
    public GoogleSignInClient getGoogleSignInClient() {
        return googleSignInClient;
    }

    @Override
    public void onSignUpClicked(String email, String password) {
        if (validateInput(email, password)) {
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

    @Override
    public void onGoogleSignInClicked(GoogleSignInAccount account) {
        baseView.showProgress();
        repo.signInWithGoogle(account, task -> {
            baseView.hideProgress();

            if (task.isSuccessful()) {
                baseView.navigateToNextScreen();
            } else {
                baseView.showError(task.getException().getMessage());
            }
        });
    }

    private boolean validateInput(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            baseView.showError("Fields cannot be empty");
            return false;
        }
        return true;
    }
}
