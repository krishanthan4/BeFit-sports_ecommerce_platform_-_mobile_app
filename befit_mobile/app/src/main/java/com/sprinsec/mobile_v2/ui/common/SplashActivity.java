package com.sprinsec.mobile_v2.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.util.HttpClientProvider;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        HttpClientProvider.initialize(this); // Initialize with Application Context

        ImageView logoImage = findViewById(R.id.logoImage);
        TextView appName = findViewById(R.id.appName);

        // Load animations
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        logoImage.startAnimation(fadeIn);
        appName.startAnimation(slideUp);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, onboarding_activity.class));
            finish();
        }, SPLASH_DURATION);
    }
}
