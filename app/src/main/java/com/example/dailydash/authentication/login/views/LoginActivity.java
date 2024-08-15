package com.example.dailydash.authentication.login.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.dailydash.R;
import com.example.dailydash.authentication.BaseView;
import com.example.dailydash.authentication.data.repo.Respiratory;
import com.example.dailydash.authentication.login.presenter.LoginPresenter;
import com.example.dailydash.authentication.login.presenter.LoginPresenterImp;
import com.example.dailydash.authentication.register.views.SignUp;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements BaseView {

    private TextInputEditText email, password;
    private MaterialButton signIn;
    private TextView signUp;
    private ImageView googleButton;
    private LottieAnimationView lottieAnimationView;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        signIn = findViewById(R.id.materialButton);
        signUp = findViewById(R.id.textButton2);
        googleButton = findViewById(R.id.imageView6);
        lottieAnimationView = findViewById(R.id.lottieAnimationView);

        // Initialize presenter
        loginPresenter = new LoginPresenterImp(this,  new Respiratory(this));  // Assume you have a RepositoryImpl for the repo

        // Sign in click handler
        signIn.setOnClickListener(v -> {
            String userEmail = email.getText().toString().trim();
            String userPassword = password.getText().toString().trim();
            loginPresenter.onSignUpClicked(userEmail, userPassword);  // Trigger login
        });
        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUp.class);
            startActivity(intent);
        });

    }

    @Override
    public void showProgress() {
        lottieAnimationView.setVisibility(View.VISIBLE);
        lottieAnimationView.playAnimation();
    }

    @Override
    public void hideProgress() {
        lottieAnimationView.pauseAnimation();
        lottieAnimationView.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToNextScreen() {
//        startActivity(new Intent(this, HomeActivity.class));
//        finish();
    }
}