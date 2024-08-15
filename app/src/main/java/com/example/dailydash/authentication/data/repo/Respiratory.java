package com.example.dailydash.authentication.data.repo;

import android.content.Context;
import com.example.dailydash.authentication.data.sharedprefernce.SharedPreferences;
import com.example.dailydash.authentication.data.firebase.FirebaseAuthenticatoin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;

public class Respiratory {
    SharedPreferences sharedPreferences;
    FirebaseAuthenticatoin firebaseAuthenticatoin;

    public Respiratory(Context context) {
        this.sharedPreferences = new SharedPreferences(context);
        this.firebaseAuthenticatoin = new FirebaseAuthenticatoin();
    }

    public void singUp(String email, String password, OnCompleteListener<AuthResult> listener) {
        firebaseAuthenticatoin.singUp(email, password)
                .addOnCompleteListener(listener);
    }

    public void logIn(String email, String password, OnCompleteListener<AuthResult> listener) {
        firebaseAuthenticatoin.login(email, password)
                .addOnCompleteListener(listener);
    }

    public void addToPreferences(boolean isLogin) {
        sharedPreferences.setLoginState(isLogin);
    }

    public boolean readFromPreferences() {
        return sharedPreferences.isLoggedIn();
    }
}