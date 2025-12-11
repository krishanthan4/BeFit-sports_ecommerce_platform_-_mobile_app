package com.sprinsec.mobile_v2.ui.fragments.user.seller;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.adapter.spinners.SelectBrandAdapter;
import com.sprinsec.mobile_v2.adapter.spinners.SelectCategoryAdapter;
import com.sprinsec.mobile_v2.adapter.spinners.SelectColorAdapter;
import com.sprinsec.mobile_v2.adapter.spinners.SelectModelAdapter;
import com.sprinsec.mobile_v2.data.api.GetAddProductsInitialDataService;
import com.sprinsec.mobile_v2.data.api.SellerAddProductService;
import com.sprinsec.mobile_v2.data.model.CountrySelectorModel;
import com.sprinsec.mobile_v2.data.model.SelectBrandModel;
import com.sprinsec.mobile_v2.data.model.SelectColorModel;
import com.sprinsec.mobile_v2.data.model.SelectModelModel;
import com.sprinsec.mobile_v2.data.model.UserHomeCategoryModel;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.ui.fragments.user.user_profile_fragment;
import com.sprinsec.mobile_v2.ui.user.user_home_interface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class seller_add_products_fragment extends Fragment {
    private ArrayList<Uri> selectedImages = new ArrayList<>();
    private ImageView addImage1, addImage2, addImage3;
    private boolean isImage1Selected = false;
    private String categoryId, brandId, modelId, colorId, price, description, quantity, cost_per_item, delivery_fee, title;
    private Spinner fragment_seller_seller_add_products_product_category_spinner, fragment_seller_seller_add_products_product_model_spinner,
            fragment_seller_seller_add_products_product_color_spinner, fragment_seller_seller_add_products_product_brand_spinner;

    // Photo picker activity launchers for each image
    private ActivityResultLauncher<PickVisualMediaRequest> pickImage1;
    private ActivityResultLauncher<PickVisualMediaRequest> pickImage2;
    private ActivityResultLauncher<PickVisualMediaRequest> pickImage3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize activity launchers
        pickImage1 = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                selectedImages.add(0, uri);
                Glide.with(this)
                        .load(uri)
                        .into(addImage1);
                isImage1Selected = true;
            }
        });

        pickImage2 = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                if (selectedImages.size() > 1) {
                    selectedImages.set(1, uri);
                } else {
                    selectedImages.add(1, uri);
                }
                Glide.with(this)
                        .load(uri)
                        .into(addImage2);
            }
        });

        pickImage3 = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                if (selectedImages.size() > 2) {
                    selectedImages.set(2, uri);
                } else {
                    selectedImages.add(2, uri);
                }
                Glide.with(this)
                        .load(uri)
                        .into(addImage3);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__seller__add_products, container, false);
        ScrollView scrollView = view.findViewById(R.id.fragment_seller_add_products_scroll_view);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenHeight = displayMetrics.heightPixels;
        ViewGroup.LayoutParams params = scrollView.getLayoutParams();
        params.height = (int) (screenHeight * 0.8);
        scrollView.setLayoutParams(params);
        ImageView fragment_seller_seller_add_products_back_icon = view.findViewById(R.id.fragment_seller_seller_add_products_back_icon);
        fragment_seller_seller_add_products_product_category_spinner = view.findViewById(R.id.fragment_seller_seller_add_products_product_category_spinner);
        fragment_seller_seller_add_products_product_brand_spinner = view.findViewById(R.id.fragment_seller_seller_add_products_product_brand_spinner);
        fragment_seller_seller_add_products_product_model_spinner = view.findViewById(R.id.fragment_seller_seller_add_products_product_model_spinner);
        fragment_seller_seller_add_products_product_color_spinner = view.findViewById(R.id.fragment_seller_seller_add_products_product_color_spinner);

        ArrayList<UserHomeCategoryModel> categoryList = new ArrayList<>();
        categoryList.add(new UserHomeCategoryModel("0", "Select Category"));
        ArrayList<SelectModelModel> modelList = new ArrayList<>();
        modelList.add(new SelectModelModel("0", "Select Model"));
        ArrayList<SelectBrandModel> brandList = new ArrayList<>();
        brandList.add(new SelectBrandModel("0", "Select Brand"));
        ArrayList<SelectColorModel> colorList = new ArrayList<>();
        colorList.add(new SelectColorModel("0", "Select Color"));

        GetAddProductsInitialDataService.getData(new GeneralCallBack() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                if (getActivity() == null) return;

                getActivity().runOnUiThread(() -> {
                    Log.i("befit_logs", "Add Products All Data ::" + jsonObject.toString());
                    try {
                        if (jsonObject.has("all_data") && jsonObject.get("all_data").isJsonObject()) {
                            JsonObject allData = jsonObject.getAsJsonObject("all_data");

                            // Populate categories
                            if (allData.has("categories") && allData.get("categories").isJsonArray()) {
                                JsonArray categories = allData.getAsJsonArray("categories");
                                for (JsonElement categoryElement : categories) {
                                    JsonObject category = categoryElement.getAsJsonObject();
                                    String id = String.valueOf(category.get("id").getAsInt());
                                    String name = category.get("name").getAsString();
                                    categoryList.add(new UserHomeCategoryModel(id, name));
                                }
                            }

                            // Populate brands
                            if (allData.has("brands") && allData.get("brands").isJsonArray()) {
                                JsonArray brands = allData.getAsJsonArray("brands");
                                for (JsonElement brandElement : brands) {
                                    JsonObject brand = brandElement.getAsJsonObject();
                                    String id = String.valueOf(brand.get("id").getAsInt());
                                    String name = brand.get("name").getAsString();
                                    brandList.add(new SelectBrandModel(id, name));
                                }
                            }

                            // Populate models
                            if (allData.has("models") && allData.get("models").isJsonArray()) {
                                JsonArray models = allData.getAsJsonArray("models");
                                for (JsonElement modelElement : models) {
                                    JsonObject model = modelElement.getAsJsonObject();
                                    String id = String.valueOf(model.get("id").getAsInt());
                                    String name = model.get("name").getAsString();
                                    modelList.add(new SelectModelModel(id, name));
                                }
                            }

                            if (allData.has("colors") && allData.get("colors").isJsonArray()) {
                                JsonArray colors = allData.getAsJsonArray("colors");
                                for (JsonElement colorElement : colors) {
                                    JsonObject color = colorElement.getAsJsonObject();
                                    String id = String.valueOf(color.get("id").getAsInt());
                                    String name = color.get("name").getAsString();
                                    colorList.add(new SelectColorModel(id, name));
                                }
                            }

                            // Set adapters to spinners
                            SelectCategoryAdapter categoryAdapter = new SelectCategoryAdapter(getContext(), R.layout.spinner_category_inner_item, categoryList);
                            fragment_seller_seller_add_products_product_category_spinner.setAdapter(categoryAdapter);

                            SelectBrandAdapter brandAdapter = new SelectBrandAdapter(getContext(), R.layout.spinner_brand_inner_item, brandList);
                            fragment_seller_seller_add_products_product_brand_spinner.setAdapter(brandAdapter);

                            SelectModelAdapter modelAdapter = new SelectModelAdapter(getContext(), R.layout.spinner_model_inner_item, modelList);
                            fragment_seller_seller_add_products_product_model_spinner.setAdapter(modelAdapter);
                            SelectColorAdapter colorAdapter = new SelectColorAdapter(getContext(), R.layout.spinner_color_inner_item, colorList);
                            fragment_seller_seller_add_products_product_color_spinner.setAdapter(colorAdapter);
                        }
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


        addImage1 = view.findViewById(R.id.fragment_seller_add_products_select_image_1);
        addImage2 = view.findViewById(R.id.fragment_seller_add_products_select_image_2);
        addImage3 = view.findViewById(R.id.fragment_seller_add_products_select_image_3);

        addImage1.setOnClickListener(v -> launchImagePicker(1));
        addImage2.setOnClickListener(v -> launchImagePicker(2));
        addImage3.setOnClickListener(v -> launchImagePicker(3));

        fragment_seller_seller_add_products_product_category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserHomeCategoryModel selectedCategory = (UserHomeCategoryModel) parent.getItemAtPosition(position);
                if (!selectedCategory.getCategoryId().equals("0")) {
                    categoryId = selectedCategory.getCategoryId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        fragment_seller_seller_add_products_product_brand_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectBrandModel selectedBrand = (SelectBrandModel) parent.getItemAtPosition(position);
                if (!selectedBrand.getBrand_id().equals("0")) {
                    brandId = selectedBrand.getBrand_id();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        fragment_seller_seller_add_products_product_model_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectModelModel selectedModel = (SelectModelModel) parent.getItemAtPosition(position);
                if (!selectedModel.getModel_id().equals("0")) {
                    modelId = selectedModel.getModel_id();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        fragment_seller_seller_add_products_product_color_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectColorModel selectedColor = (SelectColorModel) parent.getItemAtPosition(position);
                if (!selectedColor.getColorId().equals("0")) {
                    colorId = selectedColor.getColorId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Button addProductButton = view.findViewById(R.id.fragment_seller_seller_add_products_add_product_button);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RadioGroup conditionGroup;
                EditText productTitleEditText, productDescriptionEditText, productQuantityEditText, productCostPerItemEditText, productDeliveryFeeEditText;
                productTitleEditText = view.findViewById(R.id.fragment_seller_seller_add_products_product_title);
                productQuantityEditText = view.findViewById(R.id.fragment_seller_seller_add_products_product_quantity);
                productCostPerItemEditText = view.findViewById(R.id.fragment_seller_seller_add_products_cost_per_item);
                conditionGroup = view.findViewById(R.id.fragment_seller_seller_add_products_product_condition_radio_group);
                productDeliveryFeeEditText = view.findViewById(R.id.fragment_seller_seller_add_products_standard_delivery_fee);
                productDescriptionEditText = view.findViewById(R.id.fragment_seller_seller_add_products_product_description);

                if (fragment_seller_seller_add_products_product_brand_spinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getContext(), "Please select a brand", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fragment_seller_seller_add_products_product_model_spinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getContext(), "Please select a model", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fragment_seller_seller_add_products_product_color_spinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getContext(), "Please select a color", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fragment_seller_seller_add_products_product_category_spinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getContext(), "Please select a category", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (productTitleEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Product Title is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (productQuantityEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Product Quantity is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (productCostPerItemEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Product Cost per item is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (productDeliveryFeeEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Product delivery Fee is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Integer.parseInt(productQuantityEditText.getText().toString()) < 1) {
                    Toast.makeText(getContext(), "Product Quantity should be greater than 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (productDescriptionEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Product Description is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Double.parseDouble(productCostPerItemEditText.getText().toString()) <= 0) {
                    Toast.makeText(getContext(), "Product Cost per item should be greater than 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Double.parseDouble(productDeliveryFeeEditText.getText().toString()) < 0) {
                    Toast.makeText(getContext(), "Enter a valid Delivery Fee", Toast.LENGTH_SHORT).show();
                    return;
                }
                title = productTitleEditText.getText().toString();
                quantity = productQuantityEditText.getText().toString();
                cost_per_item = productCostPerItemEditText.getText().toString();
                delivery_fee = productDeliveryFeeEditText.getText().toString();
                description = productDescriptionEditText.getText().toString();


                if (validateImages()) {
                    ArrayList<byte[]> imageBytesList = new ArrayList<>();

                    // Convert all selected images to byte arrays
                    for (Uri imageUri : selectedImages) {
                        try {
                            byte[] imageBytes = convertImageToBytes(imageUri);
                            if (imageBytes != null) {
                                imageBytesList.add(imageBytes);
                            }
                        } catch (IOException e) {
                            Log.e("befit_logs", "Error converting image to bytes", e);
                            Toast.makeText(getContext(), "Error processing images", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    if (modelId == null || brandId == null || categoryId == null || colorId == null) {
                        Log.i("befit_log", modelId + " " + brandId + " " + categoryId + " " + colorId);
                        Toast.makeText(getContext(), "Please select all the fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    conditionGroup = view.findViewById(R.id.fragment_seller_seller_add_products_product_condition_radio_group);
                    int selectedId = conditionGroup.getCheckedRadioButtonId();

                    if (selectedId != -1) {
                        RadioButton selectedRadioButton = view.findViewById(selectedId);
                        String selectedText = selectedRadioButton.getText().toString();

                        SellerAddProductService.setData(new GeneralCallBack() {
                                                            @Override
                                                            public void onSuccess(JsonObject jsonObject) {
                                                                if (getActivity() == null) return;

                                                                getActivity().runOnUiThread(() -> {
                                                                    Log.i("befit_logs", "Add Product Response ::" + jsonObject.toString());
                                                                    try {
                                                                        if (jsonObject.has("status") && jsonObject.get("status").getAsString().equals("success")) {
                                                                            Toast.makeText(getContext(), "Product added successfully", Toast.LENGTH_SHORT).show();
                                                                            // Clear the form or navigate back
                                                                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.sprinsec.mobile_v2.user_prefs",MODE_PRIVATE);
                                                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                                                            editor.putString("sellerStatus", "true");
                                                                            Intent intent = new Intent(getContext(), user_home_interface.class);
                                                                            startActivity(intent);
                                                                            clearForm();
                                                                        } else if (jsonObject.has("error")) {
                                                                            Toast.makeText(getContext(), jsonObject.get("error").getAsString(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    } catch (Exception e) {
                                                                        Log.e("befit_logs", "Error parsing data", e);
                                                                    }
                                                                });
                                                            }

                                                            @Override
                                                            public void onFailure(String errorMessage) {
                                                                if (getActivity() == null) return;

                                                                getActivity().runOnUiThread(() -> {
                                                                    Log.e("befit_logs", "Error fetching data: " + errorMessage);
                                                                    Toast.makeText(getContext(), "Failed to add product", Toast.LENGTH_SHORT).show();
                                                                });
                                                            }
                                                        }, categoryId, brandId, title, modelId, selectedText, colorId, quantity,
                                cost_per_item, delivery_fee, imageBytesList, description, getContext());
                    } else {
                        Toast.makeText(getContext(), "Select the product condition", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Please select at least one image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fragment_seller_seller_add_products_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.user_home_interface_fragment_container_view, user_profile_fragment.class, null).commit();

            }
        });
        return view;
    }


    private byte[] convertImageToBytes(Uri imageUri) throws IOException {
        if (imageUri == null || getContext() == null) return null;

        InputStream inputStream = null;
        ByteArrayOutputStream byteBuffer = null;
        try {
            inputStream = getContext().getContentResolver().openInputStream(imageUri);
            byteBuffer = new ByteArrayOutputStream();

            // Use a buffer for better performance
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }

            return byteBuffer.toByteArray();
        } finally {
            // Close streams in finally block to ensure they're always closed
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e("befit_logs", "Error closing input stream", e);
                }
            }
            if (byteBuffer != null) {
                try {
                    byteBuffer.close();
                } catch (IOException e) {
                    Log.e("befit_logs", "Error closing byte buffer", e);
                }
            }

        }
    }


    private void clearForm() {
        // Reset images
        addImage1.setImageResource(R.drawable.upload_image_icon);
        addImage2.setImageResource(R.drawable.upload_image_icon);
        addImage3.setImageResource(R.drawable.upload_image_icon);

        // Clear selections
        selectedImages.clear();
        isImage1Selected = false;

        // Reset spinners
        fragment_seller_seller_add_products_product_category_spinner.setSelection(0);
        fragment_seller_seller_add_products_product_brand_spinner.setSelection(0);
        fragment_seller_seller_add_products_product_model_spinner.setSelection(0);
        fragment_seller_seller_add_products_product_color_spinner.setSelection(0);

        // Clear IDs
        categoryId = null;
        brandId = null;
        modelId = null;
        colorId = null;
    }

    private void launchImagePicker(int imageNumber) {
        PickVisualMediaRequest request = new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build();

        switch (imageNumber) {
            case 1:
                pickImage1.launch(request);
                break;
            case 2:
                pickImage2.launch(request);
                break;
            case 3:
                pickImage3.launch(request);
                break;
        }
    }

    private boolean validateImages() {
        return isImage1Selected;
    }

    private ArrayList<Uri> getSelectedImages() {
        return selectedImages;
    }
}