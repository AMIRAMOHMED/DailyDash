package com.example.dailydash.authentication.data.repo;

import android.content.Context;

import com.example.dailydash.R;
import com.example.dailydash.authentication.data.sharedprefernce.SharedPreferences;
import com.example.dailydash.authentication.data.firebase.FirebaseAuthenticatoin;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;

public class AuthenticationRepository {
    SharedPreferences sharedPreferences;
    FirebaseAuthenticatoin firebaseAuthenticatoin;
   private static AuthenticationRepository repoInstance;



    public AuthenticationRepository(Context context) {
        this.sharedPreferences = new SharedPreferences(context);
        this.firebaseAuthenticatoin = new FirebaseAuthenticatoin();
        this.firebaseAuthenticatoin = new FirebaseAuthenticatoin();
        firebaseAuthenticatoin.configureGoogleSignIn(context, context.getString(R.string.client_id));
    }
    public static AuthenticationRepository getInstance(Context context) {
        if (repoInstance == null) {
            repoInstance = new AuthenticationRepository(context);
        }
        return repoInstance;
    }

    public void signInWithGoogle(GoogleSignInAccount account, OnCompleteListener<AuthResult> listener) {
        firebaseAuthenticatoin.firebaseAuthWithGoogle(account)
                .addOnCompleteListener(listener);
    }


    public void singUp(String email, String password, OnCompleteListener<AuthResult> listener) {
        firebaseAuthenticatoin.singUp(email, password)
                .addOnCompleteListener(listener);
    }

    public void logIn(String email, String password, OnCompleteListener<AuthResult> listener) {
        firebaseAuthenticatoin.login(email, password)
                .addOnCompleteListener(listener);
    }


    public  void logOut() {
        firebaseAuthenticatoin.logOut();
    }


    //shared preferences
    public void addLoginToPreferences(boolean isLogin) {
        sharedPreferences.setLoginState(isLogin);
    }

    public boolean readLoginFromPreferences() {
        return sharedPreferences.isLoggedIn();
    }
    public  void addedUserIdToPreferences(String userId){
        sharedPreferences.setUserId(userId);
    }

    public String readUserIdFromPreferences(){
        return sharedPreferences.getUserId();
    }
    public void deleteUserIdFromPreferences(){
        sharedPreferences.setUserId("");
    }

}