package com.example.dailydash.authentication.data.firebase;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthenticatoin {
    private final FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;

    public FirebaseAuthenticatoin() {
        firebaseAuth = FirebaseAuth.getInstance();
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