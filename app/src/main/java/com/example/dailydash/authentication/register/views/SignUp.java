package com.example.dailydash.authentication.register.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.dailydash.R;
import com.example.dailydash.authentication.BaseView;
import com.example.dailydash.authentication.login.views.LoginActivity;
import com.example.dailydash.authentication.register.presenter.RegisterPresenterImpl;
import com.example.dailydash.authentication.data.repo.Respiratory;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SignUp extends AppCompatActivity implements BaseView {

    RegisterPresenterImpl presenter;
    private TextInputEditText email, password, confirmPassword;
    TextView signIn;
    MaterialButton signUp;
    ImageView signUpWithGoogle;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        lottieAnimationView = findViewById(R.id.lottieAnimationView);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        confirmPassword = findViewById(R.id.editTextTextConfirmPassword);
        signUp = findViewById(R.id.materialButton);
        signIn = findViewById(R.id.textButton2);
        signUpWithGoogle = findViewById(R.id.imageView6);

        presenter = new RegisterPresenterImpl(this, new Respiratory(this));

        signUp.setOnClickListener(v -> {
            presenter.onSignUpClicked(getEmail(), getPassword(), getConfirmPassword());
        });

        signIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    public String getEmail() {
        return email.getText().toString();
    }

    public String getPassword() {
        return password.getText().toString();
    }

    public String getConfirmPassword() {
        return confirmPassword.getText().toString();
    }

    @Override
    public void showProgress() {
        lottieAnimationView.setVisibility(View.VISIBLE);
        lottieAnimationView.playAnimation();
    }

    @Override
    public void hideProgress() {
        lottieAnimationView.setVisibility(View.GONE); // Make it visible

        lottieAnimationView.playAnimation();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToNextScreen() {
        Intent intent = new Intent(SignUp.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}