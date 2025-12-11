package com.sprinsec.mobile_v2.ui.common.auth.signUp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.adapter.SelectCountryAdapter;
import com.sprinsec.mobile_v2.data.api.SignUpService;
import com.sprinsec.mobile_v2.data.model.CountrySelectorModel;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.ui.common.auth.SignInActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SelectCountryActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private ArrayList<CountrySelectorModel> countryList;
    private Spinner spinner;
    private Button nextButton;
    private FusedLocationProviderClient fusedLocationClient;
    private String email, password;
    private View loadingOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_country);
        initializeViews();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        setupCountryList();
        setupSpinner();
        checkLocationPermissionAndGetCountry();
    }

    private void initializeViews() {
        spinner = findViewById(R.id.select_country__spinner);
        nextButton = findViewById(R.id.select_country__continue_button);
        loadingOverlay = findViewById(R.id.loading_overlay); // Make sure to add this to your layout
    }

    private void showLoading(String message) {
        loadingOverlay.setVisibility(View.VISIBLE);
        spinner.setEnabled(false);
        nextButton.setEnabled(false);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void hideLoading() {
        loadingOverlay.setVisibility(View.GONE);
        spinner.setEnabled(true);
        nextButton.setEnabled(true);
    }

    private void getUserCountryFromLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            showLoading("Detecting your country...");

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(
                                        location.getLatitude(), location.getLongitude(), 1);
                                if (addresses != null && !addresses.isEmpty()) {
                                    String countryName = addresses.get(0).getCountryName();
                                    runOnUiThread(() -> {
                                        setCountryInSpinner(countryName);
                                        hideLoading();
                                        Toast.makeText(this, "Country detected: " + countryName,
                                                Toast.LENGTH_SHORT).show();
                                    });
                                } else {
                                    runOnUiThread(() -> {
                                        hideLoading();
                                        Toast.makeText(this, "Couldn't detect country. Please select manually",
                                                Toast.LENGTH_LONG).show();
                                    });
                                }
                            } catch (IOException e) {
                                Log.e("SelectCountry", "Error getting country from location", e);
                                runOnUiThread(() -> {
                                    hideLoading();
                                    Toast.makeText(this, "Error detecting country. Please select manually",
                                            Toast.LENGTH_LONG).show();
                                });
                            }
                        } else {
                            runOnUiThread(() -> {
                                hideLoading();
                                Toast.makeText(this, "Location not available. Please select country manually",
                                        Toast.LENGTH_LONG).show();
                            });
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("SelectCountry", "Error getting location", e);
                        runOnUiThread(() -> {
                            hideLoading();
                            Toast.makeText(this, "Error detecting location. Please select country manually",
                                    Toast.LENGTH_LONG).show();
                        });
                    });
        }
    }

    private void setupCountryList() {
        countryList = new ArrayList<>();
        countryList.add(new CountrySelectorModel("Select Country", R.drawable.select_country));
        countryList.add(new CountrySelectorModel("India", R.drawable.country_india));
        countryList.add(new CountrySelectorModel("Afghanistan", R.drawable.country_afghanistan));
        countryList.add(new CountrySelectorModel("Sri Lanka", R.drawable.country_sri_lanka));
        countryList.add(new CountrySelectorModel("Bangladesh", R.drawable.country_bangladesh));
        countryList.add(new CountrySelectorModel("Bhutan", R.drawable.country_bhutan));
        countryList.add(new CountrySelectorModel("Maldives", R.drawable.country_maldives));
        countryList.add(new CountrySelectorModel("Nepal", R.drawable.country_nepal));
        countryList.add(new CountrySelectorModel("Pakistan", R.drawable.country_pakistan));
    }

    private void setupSpinner() {
        SelectCountryAdapter adapter = new SelectCountryAdapter(this, R.layout.spinner_country_inner_item, countryList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                handleCountrySelection((CountrySelectorModel) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void checkLocationPermissionAndGetCountry() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        } else {
            getUserCountryFromLocation();
        }
    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            new AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("We need location permission to automatically detect your country. " +
                            "You can still select your country manually if you deny this permission.")
                    .setPositiveButton("OK", (dialog, which) -> {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                LOCATION_PERMISSION_REQUEST_CODE);
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserCountryFromLocation();
            } else {
                Toast.makeText(this, "Please select your country manually",
                        Toast.LENGTH_LONG).show();
            }
        }
    }


    private void setCountryInSpinner(String countryName) {
        for (int i = 0; i < countryList.size(); i++) {
            if (countryList.get(i).getCountryName().equalsIgnoreCase(countryName)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void handleCountrySelection(CountrySelectorModel selectedCountry) {
        String countryName = selectedCountry.getCountryName();

        if (countryName.equals("Select Country")) {
            Toast.makeText(this, "Please Select Your Country", Toast.LENGTH_SHORT).show();
            nextButton.setClickable(false);
            nextButton.setBackgroundColor(getColor(R.color.DisabledButtonColor));
            return;
        }

        nextButton.setClickable(true);
        nextButton.setBackgroundColor(getColor(R.color.PrimaryButtonColor));
        nextButton.setOnClickListener(v -> {
            Log.i("SelectCountryActivity", "Next button clicked");
            nextButton.setClickable(false);
            nextButton.setBackgroundColor(getColor(R.color.DisabledButtonColor));

            SignUpService.Send(email, password, new GeneralCallBack() {
                @Override
                public void onSuccess(JsonObject response) {
                    runOnUiThread(() -> {
                        nextButton.setClickable(true);
                        nextButton.setBackgroundColor(getColor(R.color.PrimaryButtonColor));

                        if (!response.has("status")) {
                            Log.e("SelectCountryActivity", "Response does not contain status");
                            return;
                        }
                        Log.i("SelectCountryActivity", "SignUp Response: " + response);
                        String status = response.get("status").getAsString();
                        switch (status) {
                            case "success":
                                saveCountryAndNavigate(countryName, SignInActivity.class);
                                break;
                            case "already":
                                Toast.makeText(SelectCountryActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SelectCountryActivity.this, SignUpActivity.class));
                                break;
                            default:
                                Toast.makeText(SelectCountryActivity.this, status, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    });
                }

                @Override
                public void onFailure(String errorMessage) {
                    runOnUiThread(() -> {
                        nextButton.setClickable(true);
                        nextButton.setBackgroundColor(getColor(R.color.PrimaryButtonColor));
                        Toast.makeText(SelectCountryActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });
    }

    private void saveCountryAndNavigate(String countryName, Class<?> destinationActivity) {
        SharedPreferences.Editor editor =
                getSharedPreferences("com.sprinsec.mobile_v2.user_prefs",MODE_PRIVATE).edit();
        Log.i("SelectCountryActivityCountry", "Saving country: " + countryName);
        editor.putString("country", countryName);
        editor.apply();

        startActivity(new Intent(this, destinationActivity));
        finish();
    }
}


