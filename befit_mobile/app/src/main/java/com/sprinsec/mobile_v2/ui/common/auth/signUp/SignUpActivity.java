package com.sprinsec.mobile_v2.ui.common.auth.signUp;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.ui.common.auth.SignInActivity;
import com.sprinsec.mobile_v2.util.Validations;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignUpActivity extends AppCompatActivity implements SensorEventListener {
    private static final float SHAKE_THRESHOLD = 12.0f;
    private static final int SHAKE_WAIT_TIME_MS = 250;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private EditText email, password, retypePassword;
    private Button button;
    private TextView dontHaveAccount;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long mShakeTime = 0;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sign_up_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        //        gettingIDs
        TextView already_have_account_Text = findViewById(R.id.sign_up_already_have_click);
        Button saveButton = findViewById(R.id.sign_up_button);
        email = findViewById(R.id.sign_up_email_input_field);
        password = findViewById(R.id.sign_up_password_input_field);
        retypePassword = findViewById(R.id.sign_up_retype_password_input_field);


        already_have_account_Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString().trim();
                String passwordText = password.getText().toString().trim();
                String retypePasswordText = retypePassword.getText().toString().trim();

                Log.i("befit_logs", emailText);
                Log.i("befit_logs", passwordText);
                Log.i("befit_logs", retypePasswordText);
                if (!Validations.isValidEmail(emailText)) {
                    Toast.makeText(SignUpActivity.this, "Please Enter a valid Email!", Toast.LENGTH_SHORT).show();
                } else if (!Validations.isValidPassword(passwordText)) {
                    Toast.makeText(SignUpActivity.this, "Password must be at least 8 characters long!", Toast.LENGTH_SHORT).show();
                } else if (retypePasswordText.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please Enter Password Again!", Toast.LENGTH_SHORT).show();
                } else if (!passwordText.equals(retypePasswordText)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SignUpActivity.this, SelectCountryActivity.class);
                    intent.putExtra("email", emailText);
                    intent.putExtra("password", passwordText);
                    startActivity(intent);
                }
            }
        });
    }

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
            retypePassword.setText("");
            Toast.makeText(SignUpActivity.this, "Fields cleared!", Toast.LENGTH_SHORT).show();
        });
    }

}