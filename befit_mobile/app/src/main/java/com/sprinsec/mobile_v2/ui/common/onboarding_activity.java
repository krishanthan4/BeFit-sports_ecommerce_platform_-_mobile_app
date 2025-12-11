package com.sprinsec.mobile_v2.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.adapter.OnboardingAdapter;
import com.sprinsec.mobile_v2.data.api.CheckUserSessionService;
import com.sprinsec.mobile_v2.data.model.OnboardingItemModel;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sprinsec.mobile_v2.ui.common.auth.SignInActivity;
import com.sprinsec.mobile_v2.ui.user.user_home_interface;
import com.sprinsec.mobile_v2.util.BroadCastReciever.NetworkStatusManager;
import com.sprinsec.mobile_v2.util.BroadCastReciever.SportsApplication;

import java.util.ArrayList;
import java.util.List;

public class onboarding_activity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private Button nextButton;
    private Button skipButton;
    private NetworkStatusManager networkStatusManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        networkStatusManager = new NetworkStatusManager(this);

        viewPager = findViewById(R.id.activity_onboarding_viewPager);
        nextButton = findViewById(R.id.activity_onboarding_nextButton);
        skipButton = findViewById(R.id.activity_onboarding_skipButton);
        TabLayout tabLayout = findViewById(R.id.activity_onboarding_tabLayout);

        List<OnboardingItemModel> pages = new ArrayList<>();
        pages.add(new OnboardingItemModel(true, null, "Experience Sports", "Discover premium sports equipment for your passion"));
        pages.add(new OnboardingItemModel(false, R.drawable.onboarding_image1, "Quality Gear", "Browse through our collection of high-quality sports items"));
        pages.add(new OnboardingItemModel(false, R.drawable.onboarding_image2, "Start Shopping", "Find the perfect gear for your next game"));

        // Check session status on app start
        CheckUserSessionService.checkUserSession(isActive -> runOnUiThread(() -> {
            if (isActive) {
                Intent intent = new Intent(onboarding_activity.this, user_home_interface.class);
                startActivity(intent);
                Log.i("befit_logs", "User session is active.");
            } else {
                Log.i("befit_logs", "No active session.");
            }
        }));

        OnboardingAdapter adapter = new OnboardingAdapter(this, pages);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                }).attach();

        nextButton.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() < pages.size() - 1) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            } else {
                startMainActivity();
            }
        });

        skipButton.setOnClickListener(v -> startMainActivity());

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == pages.size() - 1) {
                    nextButton.setText("Get Started");
                } else {
                    nextButton.setText("Next");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register connectivity listener
        ((SportsApplication) getApplication())
                .addConnectivityListener(networkStatusManager);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister connectivity listener
        ((SportsApplication) getApplication())
                .removeConnectivityListener(networkStatusManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (networkStatusManager != null) {
            networkStatusManager.onDestroy();
        }
    }

    private void startMainActivity() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }
}