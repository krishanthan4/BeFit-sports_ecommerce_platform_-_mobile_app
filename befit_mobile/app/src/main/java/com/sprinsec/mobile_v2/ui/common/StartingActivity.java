package com.sprinsec.mobile_v2.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sprinsec.mobile_v2.data.api.CheckUserSessionService;
import com.sprinsec.mobile_v2.ui.user.user_home_interface;
import com.sprinsec.mobile_v2.util.Config;

import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.ui.common.auth.SignInActivity;
import com.sprinsec.mobile_v2.util.HttpClientProvider;

public class StartingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_starting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.starting_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        HttpClientProvider.initialize(this); // Initialize with Application Context

        // Check session status on app start
        CheckUserSessionService.checkUserSession(isActive -> runOnUiThread(() -> {
            if (isActive) {
                Intent intent = new Intent(StartingActivity.this, user_home_interface.class);
                startActivity(intent);
                Log.i("befit_logs", "User session is active.");
            } else {
                Log.i("befit_logs", "No active session.");
            }
        }));
        Button continueButton = findViewById(R.id.activity_starting_1_continue_button);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartingActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}