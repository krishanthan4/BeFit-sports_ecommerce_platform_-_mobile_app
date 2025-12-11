package com.sprinsec.mobile_v2.ui.common.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.api.SigninService;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.ui.common.VerifyCodeActivity;
import com.sprinsec.mobile_v2.ui.common.auth.signUp.SignUpActivity;
import com.sprinsec.mobile_v2.ui.user.user_home_interface;
import com.sprinsec.mobile_v2.util.BroadCastReciever.NetworkStatusManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignInActivity extends AppCompatActivity implements SensorEventListener {
    private static final float SHAKE_THRESHOLD = 12.0f;
    private static final int SHAKE_WAIT_TIME_MS = 250;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private EditText email, password;
    private Button button;
    private TextView dontHaveAccount;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long mShakeTime = 0;
    private ProgressBar progressBar;
    private NetworkStatusManager networkStatusManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sign_in_main), (v, insets) -> {
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
        networkStatusManager = new NetworkStatusManager(SignInActivity.this);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        password = findViewById(R.id.sign_in_password_input_field);
        email = findViewById(R.id.sign_in_email_input_field);
        button = findViewById(R.id.sign_in_button);
        TextView dont_have_account = findViewById(R.id.sign_in_already_have_click);
        progressBar = findViewById(R.id.sign_in_progress_bar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                if (emailText.isEmpty() || passwordText.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (checkSelfPermission(android.Manifest.permission.INTERNET) ==
                        PackageManager.PERMISSION_GRANTED) {
                    attemptSignIn();
                } else {
                    Toast.makeText(SignInActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });


        dont_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void attemptSignIn() {
        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        if (emailText.isEmpty() || passwordText.isEmpty()) {
            Toast.makeText(SignInActivity.this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Disable button and show progress bar
        progressBar.setVisibility(View.VISIBLE);
        button.setClickable(false);
        button.setBackgroundColor(getColor(R.color.DisabledButtonColor));

        // Call signin with a callback
        try {
            SigninService.signin(emailText, passwordText, new GeneralCallBack() {
                @Override
                public void onSuccess(JsonObject response) {
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        button.setClickable(true);
                        button.setBackgroundColor(getColor(R.color.PrimaryButtonColor));

                        // Log the entire response
                        Log.d("SignInResponse", "Full response: " + response.toString());

                        if (response.has("message")) {
                            String message = response.get("message").getAsString();
                            Log.d("SignInResponse", "Message: " + message);

                            if ("success".equals(message) || "already".equals(message)) {
                                try {
                                    setSharedPreference(response);
                                    Intent intent = new Intent(SignInActivity.this, user_home_interface.class);
                                    startActivity(intent);
                                } catch (Exception e) {
                                    Log.e("SignInResponse", "Error in setSharedPreference", e);
                                    Toast.makeText(SignInActivity.this, "Error saving user data", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(SignInActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                            }
                        } else if (response.has("adminMessage") && "success".equals(response.get("adminMessage").getAsString())) {
                            Intent intent = new Intent(SignInActivity.this, VerifyCodeActivity.class);
                            startActivity(intent);
                        } else if (response.has("adminMessage") && "failed".equals(response.get("adminMessage").getAsString())) {
                            Toast.makeText(SignInActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignInActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                public void setSharedPreference(JsonObject response) {
                    SharedPreferences sharedPreferences = getSharedPreferences("com.sprinsec.mobile_v2.user_prefs",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (response.has("profile_details")) {
                        JsonObject details = response.getAsJsonArray("profile_details").get(0).getAsJsonObject();
                        if (details.has("email") && !details.get("email").isJsonNull()) {
                            editor.putString("email", details.get("email").getAsString());
                            Log.i("email", details.get("email").getAsString());
                        }
                        if (details.has("fname") && !details.get("fname").isJsonNull()) {
                            editor.putString("firstName", details.get("fname").getAsString());
                        }
                        if (details.has("lname") && !details.get("lname").isJsonNull()) {
                            editor.putString("lastName", details.get("lname").getAsString());
                        }
                        if (details.has("mobile") && !details.get("mobile").isJsonNull()) {
                            editor.putString("phone", details.get("mobile").getAsString());
                        }
                        if (details.has("gender_id") && !details.get("gender_id").isJsonNull()) {
                            editor.putString("gender_id", String.valueOf(details.get("gender_id").getAsInt()));
                            Log.i("gender_id", String.valueOf(details.get("gender_id").getAsInt()));
                        }
                        if (details.has("sellerStatus") && !details.get("sellerStatus").isJsonNull()) {
                            editor.putString("sellerStatus", String.valueOf(details.get("sellerStatus").getAsBoolean()));
                            Log.i("sellerStatus", String.valueOf(details.get("sellerStatus").getAsBoolean()));
                        }
                    }

                    if (response.has("address_details") && !response.get("address_details").isJsonNull()) {
                        JsonObject address = response.getAsJsonArray("address_details").get(0).getAsJsonObject();
                        if (address != null && address.has("line1") && address.has("line2") && address.has("city") && address.has("country")) {
                            editor.putString("line1", address.get("line1").getAsString());
                            editor.putString("line2", address.get("line2").getAsString());
                            editor.putString("city", address.get("city").getAsString());
                            editor.putString("province", address.get("province").getAsString());
                            editor.putString("district", address.get("district").getAsString());
                            editor.putString("country", address.get("country").getAsString());
                        }
                    }
                    if (response.has("profile_img") && !response.get("profile_img").isJsonNull()) {
                        editor.putString("profile_img", response.get("profile_img").getAsString());
                    }

                    editor.apply();

                    Log.i("checkshared_peference", sharedPreferences.getAll().toString());
                }

                @Override
                public void onFailure(String errorMessage) {
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        button.setClickable(true);
                        button.setBackgroundColor(getColor(R.color.PrimaryButtonColor));
                        Toast.makeText(SignInActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                    });
                }
            });

        } catch (Exception e) {
            Log.i("Error", "Error in attemptSignIn: " + e.getMessage());
            Toast.makeText(this, "Something Went Wrong. Please Try Again Later", Toast.LENGTH_SHORT).show();
        }
    }

    //    making empty if the device is shaked
    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            if ((curTime - mShakeTime) > SHAKE_WAIT_TIME_MS) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                double acceleration = Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;

                if (acceleration > SHAKE_THRESHOLD) {
                    mShakeTime = curTime;
                    clearInputFields();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void clearInputFields() {
        runOnUiThread(() -> {
            email.setText("");
            password.setText("");
            Toast.makeText(SignInActivity.this, "Fields cleared!", Toast.LENGTH_SHORT).show();
        });
    }
}