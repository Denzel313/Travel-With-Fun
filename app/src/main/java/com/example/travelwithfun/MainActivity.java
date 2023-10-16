package com.example.travelwithfun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import com.example.travelwithfun.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    boolean isAndroidReady = false;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //handle splash screen transition
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        //keep splashscreen for a long time
        View content = findViewById(android.R.id.content);
        //add the pre draw listener to the tree view observer
        content.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (isAndroidReady) {
                    content.getViewTreeObserver().removeOnPreDrawListener(this);
                }

                //define a method to alter isAndroidReady boolean
                dismissSplashScreen();
                return false;
            }
        });

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }

        //Bottom Nav
        replaceFragment(new FirstFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int menuItemId = item.getItemId();

                if (menuItemId == R.id.person) {
                    replaceFragment(new FirstFragment());
                    return true;
                } else if (menuItemId == R.id.home) {
                    replaceFragment(new SecondFragment());
                    return true;
                } else if (menuItemId == R.id.settings) {
                    replaceFragment(new ThirdFragment());
                    return true;
                }
                return true;
            }
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.flFragment, fragment);
        transaction.commit();
    }

    private void dismissSplashScreen() {
        //use handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //change the boolean
                isAndroidReady = true;
            }
        }, 2000);
    }
}