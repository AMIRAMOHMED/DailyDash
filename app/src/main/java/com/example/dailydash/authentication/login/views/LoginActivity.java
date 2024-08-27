package com.example.dailydash.authentication.login.views;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dailydash.authentication.data.repo.AuthenticationRepository;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.example.dailydash.R;
import com.example.dailydash.authentication.BaseView;
import com.example.dailydash.authentication.login.presenter.LoginPresenter;
import com.example.dailydash.authentication.login.presenter.LoginPresenterImp;
import com.example.dailydash.authentication.register.views.SignUp;
import com.example.dailydash.home.views.HomeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
public class LoginActivity extends AppCompatActivity implements BaseView {

    private TextInputEditText email, password;
    private MaterialButton signIn;
    private TextView signUp,guest;
    private ImageView googleButton;
    private LottieAnimationView lottieAnimationView;

    private LoginPresenter loginPresenter;
    private AuthenticationRepository repo;

    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        signIn = findViewById(R.id.materialButton);
        signUp = findViewById(R.id.textButton2);
        googleButton = findViewById(R.id.imageView6);
        lottieAnimationView = findViewById(R.id.lottieAnimationView);
        guest = findViewById(R.id.guest);
        this.repo = AuthenticationRepository.getInstance(this);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        // Initialize presenter
        loginPresenter = new LoginPresenterImp(this, this);  // Passing 'this' as Context

        // Configure Google Sign-In through the presenter
        loginPresenter.configureGoogleSignIn(getString(R.string.client_id));

        // Set click listeners
        googleButton.setOnClickListener(v -> signInWithGoogle());
        signIn.setOnClickListener(v -> {
            String userEmail = email.getText().toString().trim();
            String userPassword = password.getText().toString().trim();
            loginPresenter.onSignUpClicked(userEmail, userPassword);
        });
        signUp.setOnClickListener(v -> navigateToSignUp());
        guest.setOnClickListener(v -> navigateToHome());
    }

    private void signInWithGoogle() {
        Intent signInIntent = loginPresenter.getGoogleSignInClient().getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    loginPresenter.onGoogleSignInClicked(account);
                    if (task.isSuccessful()) {
                        String uid = FirebaseAuth.getInstance().getUid();
                        repo.addedUserIdToPreferences(uid);
                        Log.i("LoginActivity", "onActivityResult: " + uid);
                        navigateToHome();
                    }
                }
            } catch (ApiException e) {
                Log.w("LoginActivity", "Google sign in failed", e);
                showError("Google sign-in failed: " + e.getMessage());
            }
        }
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
        navigateToHome();
    }

    private void navigateToHome() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }

    private void navigateToSignUp() {
        startActivity(new Intent(LoginActivity.this, SignUp.class));
    }
}
