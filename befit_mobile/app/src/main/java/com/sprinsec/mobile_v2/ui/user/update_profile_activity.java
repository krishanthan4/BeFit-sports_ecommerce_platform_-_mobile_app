package com.sprinsec.mobile_v2.ui.user;

import static org.apache.commons.io.FilenameUtils.getPath;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.LinearGradient;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.adapter.spinners.SelectBrandAdapter;
import com.sprinsec.mobile_v2.adapter.spinners.SelectCategoryAdapter;
import com.sprinsec.mobile_v2.adapter.spinners.SelectColorAdapter;
import com.sprinsec.mobile_v2.adapter.spinners.SelectModelAdapter;
import com.sprinsec.mobile_v2.adapter.spinners.update_profile_country_adapter;
import com.sprinsec.mobile_v2.adapter.spinners.update_profile_district_adapter;
import com.sprinsec.mobile_v2.adapter.spinners.update_profile_province_adapter;
import com.sprinsec.mobile_v2.adapter.spinners.update_proflie_city_adapter;
import com.sprinsec.mobile_v2.data.api.GetUserProfileDataService;
import com.sprinsec.mobile_v2.data.api.UpdateProfileDataService;
import com.sprinsec.mobile_v2.data.api.user.update_profile.GetCities_ForDistrictService;
import com.sprinsec.mobile_v2.data.api.user.update_profile.GetDistricts_ForProvinceService;
import com.sprinsec.mobile_v2.data.api.user.update_profile.GetProvinces_ForCountryService;
import com.sprinsec.mobile_v2.data.model.CountrySelectorModel;
import com.sprinsec.mobile_v2.data.model.SelectBrandModel;
import com.sprinsec.mobile_v2.data.model.SelectColorModel;
import com.sprinsec.mobile_v2.data.model.SelectModelModel;
import com.sprinsec.mobile_v2.data.model.UserHomeCategoryModel;
import com.sprinsec.mobile_v2.data.model.spinners.update_profile_city_model;
import com.sprinsec.mobile_v2.data.model.spinners.update_profile_country_model;
import com.sprinsec.mobile_v2.data.model.spinners.update_profile_district_model;
import com.sprinsec.mobile_v2.data.model.spinners.update_profile_province_model;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.util.Config;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class update_profile_activity extends AppCompatActivity {
    ArrayList<update_profile_province_model> provinceList;
    ArrayList<update_profile_district_model> districtList;
    ArrayList<update_profile_city_model> cityList;
    ArrayList<update_profile_country_model> countryList;
    private Uri selectedImageUri;
    private ImageView profileImageView;
    private TextInputEditText firstNameEditText, lastNameEditText, mobileEditText, addressLine1EditText, addressLine2EditText;
    private Spinner provinceSpinner, districtSpinner, citySpinner, countrySpinner;
    private RadioGroup genderRadioGroup;
    private MaterialButton saveButton;
    private String selectedGender;
    private String selectedProvince_id, selectedDistrict_id, selectedCity_id, selectedCountry_id, selectedProvince_name, selectedDistrict_name, selectedCity_name, selectedCountry_name;
    private SharedPreferences sharedPreferences;
    // Helper methods for progress dialog
    private TextView emailEditText;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_update_profile_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sharedPreferences = getSharedPreferences("com.sprinsec.mobile_v2.user_prefs",MODE_PRIVATE);
      try {
          setInitialArrayETC();
          getInitialExtraData();
          loadProfileData();
//        Load One by one spinner data
          LoadProvincesAccordingToCountry();
          LoadDistrictAccordingToProvince();
          LoadCityAccordingToDistrict();
          SetSelectedCity();

          backButtonFunction();
          saveButtonOnclickFunction();
          ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                  registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), selectedImage -> {
                      if (selectedImage != null) {
                          ImageView avatar = findViewById(R.id.activity_update_profile_image);
                          Glide.with(this)
                                  .load(selectedImage)
                                  .apply(RequestOptions.circleCropTransform())
                                  .into(avatar);
                          avatar.setImageURI(selectedImage);
                          selectedImageUri = selectedImage;
                      } else {
                          Log.d("PhotoPicker", "No media selected");
                      }
                  });
          findViewById(R.id.activity_update_profile_image).setOnClickListener(v -> pickMedia.launch(new PickVisualMediaRequest.Builder()
                  .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                  .build()));
      } catch (RuntimeException e) {
          Log.i("Error", e.getMessage());
      }catch (Exception e) {
          Log.i("Error", e.getMessage());
      }

    }

    //    getting extra data from backend
    private void getInitialExtraData() {
        GetUserProfileDataService.getData(new GeneralCallBack() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                runOnUiThread(() -> {
                    Log.i("befit_logs", "Update Profile All Data ::" + jsonObject.toString());
                    try {

                        if (jsonObject.has("countries") && jsonObject.get("countries").isJsonArray()) {
                            JsonArray countries = jsonObject.getAsJsonArray("countries");
                            for (JsonElement countryElement : countries) {
                                JsonObject country = countryElement.getAsJsonObject();
                                String country_id = String.valueOf(country.get("country_id").getAsInt());
                                String country_name = country.get("country_name").getAsString();
                                countryList.add(new update_profile_country_model(country_id, country_name));
                            }
                        }

                        update_profile_country_adapter countryAdapter = new update_profile_country_adapter(update_profile_activity.this, R.layout.spinner_user_profile_country_inner_item, countryList);
                        countrySpinner.setAdapter(countryAdapter);

                    } catch (Exception e) {
                        Log.e("befit_logs", "Error parsing data", e);
                    }
                });

            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("befit_logs", "Error fetching data: " + errorMessage);

            }
        });
    }

    private void saveButtonOnclickFunction() {
        saveButton.setOnClickListener(v -> {
            saveProfileData();
        });
    }

//    handle image functions
    //    others
    private void setInitialArrayETC() {
        profileImageView = findViewById(R.id.activity_update_profile_image);
        firstNameEditText = findViewById(R.id.activity_update_profile_first_name);
        lastNameEditText = findViewById(R.id.activity_update_profile_last_name);
        mobileEditText = findViewById(R.id.activity_update_profile_mobile);
        emailEditText = findViewById(R.id.activity_update_profile_email);
        addressLine1EditText = findViewById(R.id.activity_update_profile_address_line1);
        addressLine2EditText = findViewById(R.id.activity_update_profile_address_line2);
        genderRadioGroup = findViewById(R.id.activity_updadte_profile_gender_radio_group);
        provinceSpinner = findViewById(R.id.activity_update_profile_province);
        districtSpinner = findViewById(R.id.activity_update_profile_district);
        citySpinner = findViewById(R.id.activity_update_profile_city);
        countrySpinner = findViewById(R.id.activity_update_profile_country);
        saveButton = findViewById(R.id.activity_update_profile_save_button);
        TextView provinceText = findViewById(R.id.activity_update_profile_selected_province);
        TextView districtText = findViewById(R.id.activity_update_profile_selected_district);
        TextView cityText = findViewById(R.id.activity_update_profile_selected_city);

        provinceList = new ArrayList<>();
        districtList = new ArrayList<>();
        cityList = new ArrayList<>();
        countryList = new ArrayList<>();
        if (!sharedPreferences.getString("country", "").isEmpty()) {
            Log.d("ProfileUpdate", "Country: " + sharedPreferences.getString("country", ""));
            countryList.add(new update_profile_country_model("0", sharedPreferences.getString("country", "")));
        }

        if (!sharedPreferences.getString("province", "").isEmpty()) {
            Log.d("ProfileUpdate", "Province: " + sharedPreferences.getString("province", ""));
            provinceText.setText(sharedPreferences.getString("province", "") + " / State");
            provinceText.setVisibility(View.VISIBLE);
            provinceSpinner.setEnabled(true);
            provinceList.add(new update_profile_province_model("0", sharedPreferences.getString("province", "")));
        } else {
            provinceText.setVisibility(View.GONE);
            provinceSpinner.setEnabled(false);
            provinceList.add(new update_profile_province_model("0", "Select Province"));
        }

        if (!sharedPreferences.getString("district", "").isEmpty()) {
            Log.d("ProfileUpdate", "District: " + sharedPreferences.getString("district", ""));
            districtText.setText(sharedPreferences.getString("district", "") + " District");
            districtText.setVisibility(View.VISIBLE);
            districtSpinner.setEnabled(true);
            districtList.add(new update_profile_district_model("0", sharedPreferences.getString("district", "")));
        } else {
            districtText.setVisibility(View.GONE);
            districtSpinner.setEnabled(false);
            districtList.add(new update_profile_district_model("0", "Select District"));
        }

        if (!sharedPreferences.getString("city", "").isEmpty()) {
            cityText.setText(sharedPreferences.getString("city", "") + " City/ Village");
            cityText.setVisibility(View.VISIBLE);
            Log.d("ProfileUpdate", "City: " + sharedPreferences.getString("city", ""));
            citySpinner.setEnabled(true);
            cityList.add(new update_profile_city_model("0", sharedPreferences.getString("city", "")));
        } else {
            cityText.setVisibility(View.GONE);
            citySpinner.setEnabled(false);
            cityList.add(new update_profile_city_model("0", "Select City"));
        }
    }

    private void LoadProvincesAccordingToCountry() {
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (!((update_profile_country_model) parent.getItemAtPosition(position)).getCountry_name().equals("0") && !((update_profile_country_model) parent.getItemAtPosition(position)).getCountry_name().equals("Select Country")) {
                if (countryList.get(0).getCountry_name().equals(countryList.get(1).getCountry_name())) {
                    countryList.remove(0);
                }

                selectedCountry_id = ((update_profile_country_model) parent.getItemAtPosition(position)).getCountry_id();
                selectedCountry_name = ((update_profile_country_model) parent.getItemAtPosition(position)).getCountry_name();
                GetProvinces_ForCountryService.getData(selectedCountry_id, new GeneralCallBack() {
                    @Override
                    public void onSuccess(JsonObject response) {
                        runOnUiThread(() -> {
                            try {
                                if (response.has("provinces") && response.get("provinces").isJsonArray()) {
                                    JsonArray provinces = response.getAsJsonArray("provinces");
                                    provinceList.clear();
                                    provinceList.add(new update_profile_province_model("0", "Select Province"));
                                    for (JsonElement provinceElement : provinces) {
                                        JsonObject province = provinceElement.getAsJsonObject();
                                        String province_id = String.valueOf(province.get("province_id").getAsInt());
                                        String province_name = province.get("province_name").getAsString();
                                        provinceList.add(new update_profile_province_model(province_id, province_name));
                                    }
                                    update_profile_province_adapter provinceAdapter = new update_profile_province_adapter(update_profile_activity.this, R.layout.spinner_province_inner_item, provinceList);
                                    provinceSpinner.setAdapter(provinceAdapter);
                                    provinceSpinner.setEnabled(true);
                                }
                            } catch (Exception e) {
                                Log.e("befit_logs", "Error parsing data", e);
                            }
                        });
                    }

                    @Override
                    public void onFailure(String errorMessage) {

                    }
                });
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void LoadDistrictAccordingToProvince() {
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!((update_profile_province_model) parent.getItemAtPosition(position)).getProvince_id().equals("0") && !((update_profile_province_model) parent.getItemAtPosition(position)).getProvince_name().equals("Select Province")) {

                }
                selectedProvince_id = ((update_profile_province_model) parent.getItemAtPosition(position)).getProvince_id();
                selectedProvince_name = ((update_profile_province_model) parent.getItemAtPosition(position)).getProvince_name();

                GetDistricts_ForProvinceService.getDataData(selectedProvince_id, new GeneralCallBack() {
                    @Override
                    public void onSuccess(JsonObject response) {
                        runOnUiThread(() -> {
                            try {
                                if (response.has("districts") && response.get("districts").isJsonArray()) {
                                    JsonArray districts = response.getAsJsonArray("districts");
                                    districtList.clear();
                                    districtList.add(new update_profile_district_model("0", "Select District"));
                                    for (JsonElement districtElement : districts) {
                                        JsonObject district = districtElement.getAsJsonObject();
                                        String district_id = String.valueOf(district.get("district_id").getAsInt());
                                        String district_name = district.get("district_name").getAsString();
                                        districtList.add(new update_profile_district_model(district_id, district_name));
                                    }
                                    update_profile_district_adapter districtAdapter = new update_profile_district_adapter(update_profile_activity.this, R.layout.spinner_district_inner_item, districtList);
                                    districtSpinner.setAdapter(districtAdapter);
                                    districtSpinner.setEnabled(true);
                                }
                            } catch (Exception e) {
                                Log.e("befit_logs", "Error parsing data", e);
                            }
                        });
                    }

                    @Override
                    public void onFailure(String errorMessage) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void LoadCityAccordingToDistrict() {
        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDistrict_id = ((update_profile_district_model) parent.getItemAtPosition(position)).getDistrict_id();
                selectedDistrict_name = ((update_profile_district_model) parent.getItemAtPosition(position)).getDistrict_name();

                GetCities_ForDistrictService.getData(selectedDistrict_id, new GeneralCallBack() {
                    @Override
                    public void onSuccess(JsonObject response) {
                        runOnUiThread(() -> {
                            try {
                                if (response.has("cities") && response.get("cities").isJsonArray()) {
                                    JsonArray cities = response.getAsJsonArray("cities");
                                    cityList.clear();
                                    cityList.add(new update_profile_city_model("0", "Select City"));
                                    for (JsonElement cityElement : cities) {
                                        JsonObject city = cityElement.getAsJsonObject();
                                        String city_id = String.valueOf(city.get("city_id").getAsInt());
                                        String city_name = city.get("city_name").getAsString();
                                        cityList.add(new update_profile_city_model(city_id, city_name));
                                    }
                                    update_proflie_city_adapter cityAdapter = new update_proflie_city_adapter(update_profile_activity.this, R.layout.spinner_city_inner_item, cityList);
                                    citySpinner.setAdapter(cityAdapter);
                                    citySpinner.setEnabled(true);
                                }
                            } catch (Exception e) {
                                Log.e("befit_logs", "Error parsing data", e);
                            }
                        });
                    }

                    @Override
                    public void onFailure(String errorMessage) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void SetSelectedCity() {
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity_id = ((update_profile_city_model) parent.getItemAtPosition(position)).getCity_id();
                selectedCity_name = ((update_profile_city_model) parent.getItemAtPosition(position)).getCity_name();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadProfileData() {
        firstNameEditText.setText(sharedPreferences.getString("firstName", "") != null ? sharedPreferences.getString("firstName", "") : "");
        lastNameEditText.setText(sharedPreferences.getString("lastName", "") != null ? sharedPreferences.getString("lastName", "") : "");
        mobileEditText.setText(sharedPreferences.getString("phone", "") != null ? sharedPreferences.getString("phone", "") : "");
        emailEditText.setText(sharedPreferences.getString("email", "") != null ? sharedPreferences.getString("email", "") : "");
        addressLine1EditText.setText(sharedPreferences.getString("line1", "") != null ? sharedPreferences.getString("line1", "") : "");
        addressLine2EditText.setText(sharedPreferences.getString("line2", "") != null ? sharedPreferences.getString("line2", "") : "");
        RadioButton male_radio_buton = findViewById(R.id.activity_update_profile_gender_male);
        RadioButton female_radio_button = findViewById(R.id.activity_update_profile_gender_female);

        if (sharedPreferences.getString("gender_id", "") != null && sharedPreferences.getString("gender_id", "").equals("1")) {
            male_radio_buton.setChecked(true);
        }
        if (sharedPreferences.getString("gender_id", "") != null && sharedPreferences.getString("gender_id", "").equals("2")) {
            female_radio_button.setSelected(true);
        }

        String profileImageUri = sharedPreferences.getString("profile_img", "") != null ? sharedPreferences.getString("profile_img", "") : "";
        Glide.with(this).load(Config.BACKEND_URL + profileImageUri).placeholder(R.drawable.profile_icon3).error(R.drawable.profile_icon3).into(profileImageView);

    }

    private void saveProfileData() {
        if (validateFields()) {
            String genderId;
            if (selectedGender.equals("Male")) {
                genderId = "1";
            } else {
                genderId = "2";
            }
            sharedPreferences = getSharedPreferences("com.sprinsec.mobile_v2.user_prefs",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("firstName", firstNameEditText.getText().toString());
            editor.putString("lastName", lastNameEditText.getText().toString());
            editor.putString("phone", mobileEditText.getText().toString());
            editor.putString("email", emailEditText.getText().toString());
            editor.putString("line1", addressLine1EditText.getText().toString());
            editor.putString("line2", addressLine2EditText.getText().toString());
            editor.putString("gender_id", genderId);
            if (selectedProvince_name != null && !selectedProvince_name.isEmpty()) {
                editor.putString("province", selectedProvince_name);
            }
            if (selectedDistrict_name != null && !selectedDistrict_name.isEmpty()) {
                editor.putString("district", selectedDistrict_name);
            }
            if (selectedCity_name != null && !selectedCity_name.isEmpty()) {
                editor.putString("city", selectedCity_name);
            }
            if (selectedCountry_name != null && !selectedCountry_name.isEmpty()) {
                Log.i("ProfileUpdate", "Country: " + selectedCountry_name);
                editor.putString("country", selectedCountry_name);
            }
            editor.apply();

            // Show loading indicator
            showProgressDialog("Updating profile...");

            byte[] imageBytes = null;
            try {
                if (selectedImageUri != null) {
                    InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                    imageBytes = new byte[inputStream.available()];
                    inputStream.read(imageBytes);
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            UpdateProfileDataService.Send(genderId, firstNameEditText.getText().toString(),
                    lastNameEditText.getText().toString(), mobileEditText.getText().toString(),
                    addressLine1EditText.getText().toString(), addressLine2EditText.getText().toString(),
                    selectedProvince_id, selectedDistrict_id, selectedCity_id, selectedCountry_id,
                    imageBytes, new GeneralCallBack() {
                        @Override
                        public void onSuccess(JsonObject jsonObject) {
                            // Hide loading indicator
                            hideProgressDialog();

                            runOnUiThread(() -> {
                                Log.i("befit_logs", "Update Profile Data Response ::" + jsonObject.toString());
                                try {
                                    if (jsonObject.has("status") && jsonObject.get("status").getAsString().equals("success")) {
                                        Toast.makeText(update_profile_activity.this, "Profile Updated successfully", Toast.LENGTH_SHORT).show();

                                        if (jsonObject.has("profile_img_url") && jsonObject.get("profile_img_url").getAsString() != null && !jsonObject.get("profile_img_url").getAsString().isEmpty()) {
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("profile_img", jsonObject.get("profile_img_url").getAsString().substring(3));
                                            editor.apply();
                                        }
                                        setResult(RESULT_OK); // Set result for parent activity if needed
                                        finish(); // Optionally finish the activity after successful update
                                    } else if (jsonObject.has("message")) {
                                        Toast.makeText(update_profile_activity.this, jsonObject.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(update_profile_activity.this, "Something Went Wrong, Please try again later", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Log.e("befit_logs", "Error parsing data", e);
                                    Toast.makeText(update_profile_activity.this, "Error processing response", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            // Hide loading indicator
                            hideProgressDialog();

                            runOnUiThread(() -> {
                                Log.e("ProfileUpdate", "Profile update failed. Error: " + errorMessage);
                                Toast.makeText(update_profile_activity.this, "Update failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                            });
                        }
                    });
        } else {
            Log.d("ProfileUpdate", "Validation failed. Please check the input fields.");
        }
    }

    private boolean validateFields() {
        boolean isValid = true;

        // First name validation
        if (firstNameEditText.getText().toString().trim().isEmpty()) {
            firstNameEditText.setError("First name is required");
            isValid = false;
        } else if (firstNameEditText.getText().toString().trim().length() < 2) {
            firstNameEditText.setError("First name must be at least 2 characters");
            isValid = false;
        } else {
            firstNameEditText.setError(null);
        }

        // Last name validation
        if (lastNameEditText.getText().toString().trim().isEmpty()) {
            lastNameEditText.setError("Last name is required");
            isValid = false;
        } else {
            lastNameEditText.setError(null);
        }

        // Mobile validation
        String mobile = mobileEditText.getText().toString().trim();
        if (mobile.isEmpty()) {
            mobileEditText.setError("Mobile number is required");
            isValid = false;
        } else if (!mobile.matches("\\d{10}")) {
            mobileEditText.setError("Enter a valid 10-digit mobile number");
            isValid = false;
        } else {
            mobileEditText.setError(null);
        }

        // Email validation
        String email = emailEditText.getText().toString().trim();
        if (email.isEmpty()) {
            emailEditText.setError("Email is required");
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email address");
            isValid = false;
        } else {
            emailEditText.setError(null);
        }

        // Address validation
        if (addressLine1EditText.getText().toString().trim().isEmpty()) {
            addressLine1EditText.setError("Address Line 1 is required");
            isValid = false;
        } else {
            addressLine1EditText.setError(null);
        }

        // Location validations
        if (provinceSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a province", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (districtSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a district", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (citySpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a city", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        // Gender validation
        int selectedId = genderRadioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            selectedGender = selectedRadioButton.getText().toString();
        } else {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    private void showProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    //    :: Not that much important
    private void backButtonFunction() {
        findViewById(R.id.fragment_user_update_profile_back_icon).setOnClickListener(v -> {
            Intent intent = new Intent(update_profile_activity.this, user_main_settings.class);
            startActivity(intent);
        });
    }
}