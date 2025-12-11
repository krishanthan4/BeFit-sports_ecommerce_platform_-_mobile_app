package com.sprinsec.mobile_v2.ui.user;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.api.GetHomeProductsService;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.ui.fragments.admin.admin_dashboard_overview_fragment;
import com.sprinsec.mobile_v2.ui.fragments.admin.admin_dashboard_product_management_fragment;
import com.sprinsec.mobile_v2.ui.fragments.user.user_cart_fragment;
import com.sprinsec.mobile_v2.ui.fragments.user.user_home_fragment;
import com.sprinsec.mobile_v2.ui.fragments.user.user_profile_fragment;
import com.sprinsec.mobile_v2.ui.fragments.user.user_search_fragment;
import com.sprinsec.mobile_v2.ui.fragments.user.user_watchlist_fragment;
import com.sprinsec.mobile_v2.util.CommonFunctions;
import com.sprinsec.mobile_v2.util.HttpClientProvider;

public class user_home_interface extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_home_interface);
        HttpClientProvider.initialize(this); // Initialize with Application Context
        FragmentManager supportFragmentManager = getSupportFragmentManager();
// Initial fragment setup
        supportFragmentManager.beginTransaction()
                .replace(R.id.user_home_interface_fragment_container_view, user_home_fragment.class, null)
                .setReorderingAllowed(true)
                .commit();
        NavigationBarView navigationBarView = findViewById(R.id.user_home_interface_bottom_navigation);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        if (screenWidthDp > 600) {
            navigationBarView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
        }

        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

                if (item.getItemId() == R.id.user_tab_bar_menu_home) {
                    fragmentTransaction.replace(R.id.user_home_interface_fragment_container_view, user_home_fragment.class, null).commit();
                    return true;
                } else if (item.getItemId() == R.id.user_home_tab_bar_menu_search) {
                    fragmentTransaction.replace(R.id.user_home_interface_fragment_container_view, user_search_fragment.class, null).commit();
                    return true;
                } else if (item.getItemId() == R.id.user_home_tab_bar_menu_watchlist) {
                    fragmentTransaction.replace(R.id.user_home_interface_fragment_container_view, user_watchlist_fragment.class, null).commit();
                    return true;
                } else if (item.getItemId() == R.id.user_home_tab_bar_menu_cart) {
                    fragmentTransaction.replace(R.id.user_home_interface_fragment_container_view, user_cart_fragment.class, null).commit();
                    return true;
                } else if (item.getItemId() == R.id.user_home_tab_bar_menu_profile) {
                    fragmentTransaction.replace(R.id.user_home_interface_fragment_container_view, user_profile_fragment.class, null).commit();
                    return true;
                }

                return true;
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Do nothing to block back press
            }
        });

//
    }
}