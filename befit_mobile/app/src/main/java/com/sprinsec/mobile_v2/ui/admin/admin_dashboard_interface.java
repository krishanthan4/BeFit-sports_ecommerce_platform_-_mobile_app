package com.sprinsec.mobile_v2.ui.admin;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigationrail.NavigationRailView;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.api.admin.AdminSignoutService;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.ui.common.StartingActivity;
import com.sprinsec.mobile_v2.ui.common.VerifyCodeActivity;
import com.sprinsec.mobile_v2.ui.fragments.admin.admin_dashboard_category_management_fragment;
import com.sprinsec.mobile_v2.ui.fragments.admin.admin_dashboard_feedback_and_ratings_management_fragment;
import com.sprinsec.mobile_v2.ui.fragments.admin.admin_dashboard_overview_fragment;
import com.sprinsec.mobile_v2.ui.fragments.admin.admin_dashboard_product_management_fragment;
import com.sprinsec.mobile_v2.ui.fragments.admin.admin_dashboard_promotions_and_offers;
import com.sprinsec.mobile_v2.ui.fragments.admin.admin_dashboard_seller_management_fragment;
import com.sprinsec.mobile_v2.ui.fragments.admin.admin_dashboard_user_management_fragment;
import com.sprinsec.mobile_v2.util.HttpClientProvider;

public class admin_dashboard_interface extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard_interface);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.user_main_interface_vertical_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Do nothing to block back press
            }
        });
        HttpClientProvider.initialize(this); // Initialize with Application Context
        FragmentManager supportFragmentManager = getSupportFragmentManager();
// Initial fragment setup
        supportFragmentManager.beginTransaction()
                .replace(R.id.admin_dashboard_interface_fragment_container, admin_dashboard_overview_fragment.class, null)
                .setReorderingAllowed(true)
                .commit();

        DrawerLayout drawerLayout = findViewById(R.id.admin_dashboard_interface_navigation_drawer_layout);
        NavigationView navigationView = findViewById(R.id.admin_dashboard_interface_navigation_view);
        NavigationRailView navigationrail = findViewById(R.id.admin_dashboard_interface_navigation_rail);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Create new transaction for each navigation
                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

                if (item.getItemId() == R.id.admin_dashboard_menu_dashboard_overview) {
                    fragmentTransaction.replace(R.id.admin_dashboard_interface_fragment_container, admin_dashboard_overview_fragment.class, null)
                            .commit();
                    drawerLayout.closeDrawers();
                    return true;
                } else if (item.getItemId() == R.id.admin_dashboard_menu_user_management) {
                    fragmentTransaction.replace(R.id.admin_dashboard_interface_fragment_container, admin_dashboard_user_management_fragment.class, null)
                            .commit();
                    drawerLayout.closeDrawers();
                    return true;
                } else if (item.getItemId() == R.id.admin_dashboard_menu_seller_management) {
                    fragmentTransaction.replace(R.id.admin_dashboard_interface_fragment_container, admin_dashboard_seller_management_fragment.class, null)
                            .commit();
                    drawerLayout.closeDrawers();
                    return true;
                } else if (item.getItemId() == R.id.admin_dashboard_menu_product_management) {
                    fragmentTransaction.replace(R.id.admin_dashboard_interface_fragment_container, admin_dashboard_product_management_fragment.class, null)
                            .commit();
                    drawerLayout.closeDrawers();
                    return true;
                } else if (item.getItemId() == R.id.admin_dashboard_menu_category_management) {
                    fragmentTransaction.replace(R.id.admin_dashboard_interface_fragment_container, admin_dashboard_category_management_fragment.class, null)
                            .commit();
                    drawerLayout.closeDrawers();
                    return true;
                } else if (item.getItemId() == R.id.admin_dashboard_menu_feedbacks_and_reviews) {
                    fragmentTransaction.replace(R.id.admin_dashboard_interface_fragment_container, admin_dashboard_feedback_and_ratings_management_fragment.class, null)
                            .commit();
                    drawerLayout.closeDrawers();
                    return true;
                } else if (item.getItemId() == R.id.admin_dashboard_menu_promotions_and_offers) {
                    fragmentTransaction.replace(R.id.admin_dashboard_interface_fragment_container, admin_dashboard_promotions_and_offers.class, null)
                            .commit();
                    drawerLayout.closeDrawers();
                    return true;
                } else if (item.getItemId() == R.id.admin_dashboard_menu_logout) {
                    new AlertDialog.Builder(admin_dashboard_interface.this)
                            .setTitle("Logout Confirmation")
                            .setMessage("Are you sure you want to logout?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                AdminSignoutService.Send(new GeneralCallBack() {
                                    @Override
                                    public void onSuccess(JsonObject response) {
                                        runOnUiThread(() -> {
                                            try {
                                                if ("success".equals(response.get("message").getAsString())) {
                                                    Intent intent = new Intent(admin_dashboard_interface.this, StartingActivity.class);
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(admin_dashboard_interface.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (Exception e) {
                                                Log.e("befit_logs", "VerifyCodeActivity error: ", e);
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(String errorMessage) {
                                        runOnUiThread(() -> {
                                            Log.e("befit_logs", "Error: " + errorMessage);
                                            Toast.makeText(admin_dashboard_interface.this, "Something Went Wrong. Please Try Again Later", Toast.LENGTH_SHORT).show();
                                        });
                                    }
                                });
                            })
                            .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                            .show();
                    drawerLayout.closeDrawers();
                    return true;
                }
                return true;
            }
        });

        navigationrail.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Create new transaction for each navigation
                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

                if (item.getItemId() == R.id.admin_dashboard_menu_rail_dashboard_overview) {
                    fragmentTransaction.replace(R.id.admin_dashboard_interface_fragment_container, admin_dashboard_overview_fragment.class, null)
                            .setReorderingAllowed(true)
                            .commit();
                    return true;
                } else if (item.getItemId() == R.id.admin_dashboard_menu_rail_user_management) {
                    fragmentTransaction.replace(R.id.admin_dashboard_interface_fragment_container, admin_dashboard_user_management_fragment.class, null)
                            .setReorderingAllowed(true)
                            .commit();
                    return true;
                } else if (item.getItemId() == R.id.admin_dashboard_menu_rail_seller_management) {
                    fragmentTransaction.replace(R.id.admin_dashboard_interface_fragment_container, admin_dashboard_seller_management_fragment.class, null)
                            .setReorderingAllowed(true)
                            .commit();
                    return true;
                } else if (item.getItemId() == R.id.admin_dashboard_menu_rail_product_management) {
                    fragmentTransaction.replace(R.id.admin_dashboard_interface_fragment_container, admin_dashboard_product_management_fragment.class, null)
                            .setReorderingAllowed(true)
                            .commit();
                    return true;
                } else if (item.getItemId() == R.id.admin_dashboard_menu_rail_category_management) {
                    fragmentTransaction.replace(R.id.admin_dashboard_interface_fragment_container, admin_dashboard_category_management_fragment.class, null)
                            .setReorderingAllowed(true)
                            .commit();
                    return true;
                } else if (item.getItemId() == R.id.admin_dashboard_menu_rail_feedbacks_and_reviews) {
                    fragmentTransaction.replace(R.id.admin_dashboard_interface_fragment_container, admin_dashboard_feedback_and_ratings_management_fragment.class, null)
                            .setReorderingAllowed(true)
                            .commit();
                    return true;
                } else if (item.getItemId() == R.id.admin_dashboard_menu_rail_promotions_and_offers) {
                    fragmentTransaction.replace(R.id.admin_dashboard_interface_fragment_container, admin_dashboard_promotions_and_offers.class, null)
                            .setReorderingAllowed(true)
                            .commit();
                    return true;
                }
                return false;
            }
        });
        View headerView = navigationrail.getHeaderView();
        FloatingActionButton burgerButton = headerView.findViewById(R.id.admin_dashboard_navigation_header_rail_burger_button);
        burgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
                Log.d("Burger Button", "onClick: Burger Button Clicked");
            }
        });

    }
}