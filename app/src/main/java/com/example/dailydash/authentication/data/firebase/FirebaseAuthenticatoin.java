package com.example.dailydash.authentication.data.firebase;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class FirebaseAuthenticatoin {
    private final FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;

    public FirebaseAuthenticatoin() {
        firebaseAuth = FirebaseAuth.getInstance();

    }

    // Configure Google Sign-In
    public void configureGoogleSignIn(Context context, String clientId) {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientId)
                .requestEmail()
                .build();
        googleSignInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(context, options);
    }

    // Get GoogleSignInClient
    public GoogleSignInClient getGoogleSignInClient() {
        return googleSignInClient;
    }

    // Firebase Authentication with Google
    public Task<AuthResult> firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        return firebaseAuth.signInWithCredential(credential);
    }




    public Task<AuthResult> singUp(String email, String password) {
        return firebaseAuth.createUserWithEmailAndPassword(email, password);
    }


    public Task<AuthResult> login(String email, String password) {
        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }
    public void logOut(){
        firebaseAuth.signOut();
    }


}