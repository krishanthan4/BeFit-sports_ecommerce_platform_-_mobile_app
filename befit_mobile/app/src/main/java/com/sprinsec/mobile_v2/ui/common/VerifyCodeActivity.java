package com.sprinsec.mobile_v2.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.adapter.User_home_watchlist_recycler_view_adapter;
import com.sprinsec.mobile_v2.data.api.VerifyCodeService;
import com.sprinsec.mobile_v2.data.model.UserHomeProductModel;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.ui.admin.admin_dashboard_interface;
import com.sprinsec.mobile_v2.ui.common.auth.SignInActivity;
import com.sprinsec.mobile_v2.ui.fragments.user.user_watchlist_fragment;
import com.sprinsec.mobile_v2.ui.user.user_home_interface;
import com.sprinsec.mobile_v2.util.Config;

public class VerifyCodeActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify_code);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.verify_code_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ProgressBar progressBar = findViewById(R.id.verify_code_progressbar);

        Button button = findViewById(R.id.verify_code_button);
        EditText verfycode = findViewById(R.id.verify_code_edittext_input);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String code = verfycode.getText().toString();
                if (code.isEmpty()) {
                    Toast.makeText(VerifyCodeActivity.this, "Enter the Verification Code", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    button.setClickable(false);
                    button.setBackgroundColor(getColor(R.color.DisabledButtonColor));

                    VerifyCodeService.Send(code, new GeneralCallBack() {
                        @Override
                        public void onSuccess(JsonObject response) {
                            runOnUiThread(() -> {
                                Log.i("befit_logs", "Verify Data ::" + response.toString());
                                try {
                                    if ("success".equals(response.get("message").getAsString())) {
                                        progressBar.setVisibility(View.GONE);
                                        button.setClickable(true);
                                        button.setBackgroundColor(getColor(R.color.PrimaryButtonColor));

                                        Intent intent = new Intent(VerifyCodeActivity.this, admin_dashboard_interface.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(VerifyCodeActivity.this, "Invalid Code", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(VerifyCodeActivity.this, "Something Went Wrong. Please Try Again Later", Toast.LENGTH_SHORT).show();
                            });
                        }
                    });
                }
            }
        });
    }
}