package com.example.dailydash.authentication.login.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dailydash.R;
import com.example.dailydash.authentication.register.views.SignUp;
import com.example.dailydash.authentication.data.repo.AuthenticationRepository;
import com.example.dailydash.home.views.HomeActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashScreen extends AppCompatActivity {
    private Handler handler = new Handler();
    private Runnable runnable;
    private AuthenticationRepository repo;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splashzscreen);
        repo = AuthenticationRepository.getInstance(this);


        compositeDisposable.add(
                Observable.fromCallable(() -> {
                            // Simulate some delay like the Handler
                            Thread.sleep(3000);
                            return repo.readUserIdFromPreferences();
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(userId -> {
                            if (userId != null && !userId.isEmpty()) {
                                Log.i("SplashScreen", "User ID: " + userId);
                                Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(SplashScreen.this, SignUp.class);
                                startActivity(intent);
                            }
                            finish();
                        }, throwable -> {
                            // Handle any error here
                            Log.e("SplashScreen", "Error: " + throwable.getMessage());
                        })
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clear the disposable to avoid memory leaks
        compositeDisposable.clear();

    }

}