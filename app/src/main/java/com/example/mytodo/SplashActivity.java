package com.example.mytodo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth Auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();
        Auth = FirebaseAuth.getInstance();

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                FirebaseUser user = Auth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    // Start home activity
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    // No user is signed in
                    // start login activity
                    startActivity(new Intent(SplashActivity.this, LogIn.class));
                }
                finish();
            }
        }, secondsDelayed * 1000);
    }
}