package com.sprinsec.mobile_v2.ui.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.api.UserLogoutService;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.ui.common.StartingActivity;
import com.sprinsec.mobile_v2.ui.fragments.user.seller.seller_add_products_fragment;

public class user_main_settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_main_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        LinearLayout activity_user_main_settings_profile_settings = findViewById(R.id.activity_user_main_settings_profile_settings);
        activity_user_main_settings_profile_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(user_main_settings.this, update_profile_activity.class);
                startActivity(intent);
            }
        });

        ImageView fragment_user_main_settings_back_icon = findViewById(R.id.fragment_user_main_settings_back_icon);
        fragment_user_main_settings_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(user_main_settings.this, user_home_interface.class);
                startActivity(intent);
            }
        });

        Button activity_user_main_settings_logout_button = findViewById(R.id.activity_user_main_settings_logout_button);

        activity_user_main_settings_logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLogoutService.Send(new GeneralCallBack() {
                    @Override
                    public void onSuccess(JsonObject response) {
                        runOnUiThread(() -> {
                            try {
                                Log.i("befit_logs", "Logout Response: " + response);
                                if (response.has("message") && "success".equals(response.get("message").getAsString())) {
                                    SharedPreferences sharedPreferences = getSharedPreferences("com.sprinsec.mobile_v2.user_prefs",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.clear();
                                    editor.apply();
                                    Intent intent = new Intent(user_main_settings.this, StartingActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(user_main_settings.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(user_main_settings.this, "Something Went Wrong. Please Try Again Later", Toast.LENGTH_SHORT).show();
                        });
                    }
                });

            }
        });
    }
}