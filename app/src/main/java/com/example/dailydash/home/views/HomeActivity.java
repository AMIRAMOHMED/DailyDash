package com.example.dailydash.home.views;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dailydash.R;
import com.example.dailydash.home.views.fragments.CalendarFragment;
import com.example.dailydash.home.views.fragments.HomeFragment;
import com.example.dailydash.home.views.fragments.ProfileFragment;
import com.example.dailydash.home.views.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        frameLayout=findViewById(R.id.framLayout);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId=item.getItemId();
                if (itemId==R.id.navHome){
                    loadFragment(new HomeFragment(),false);

                } else if (itemId==R.id.navSearch) {
                    loadFragment(new SearchFragment(),false);

                } else if (itemId==R.id.navCalendar) {
                    loadFragment(new CalendarFragment(),false);

                }else {//nav profile
                    loadFragment(new ProfileFragment(),false);

                }


                return true;
            }
        });
        loadFragment(new HomeFragment(),true);


    }

    private  void loadFragment(Fragment fragment,boolean isAppInitialized){

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        if(isAppInitialized){
            fragmentTransaction.add(R.id.framLayout, fragment);

        }else {
            fragmentTransaction.replace(R.id.framLayout, fragment);

        }
        fragmentTransaction.commit();
    }
}