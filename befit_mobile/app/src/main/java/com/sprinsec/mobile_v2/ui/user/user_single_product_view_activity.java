package com.sprinsec.mobile_v2.ui.user;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.adapter.UserProfileSliderViewPager2Adapter;
import com.sprinsec.mobile_v2.adapter.UserSingleProductViewPager2Adapter;
import com.sprinsec.mobile_v2.adapter.UserSingleProductViewProductReviewRecyclerViewAdapter;
import com.sprinsec.mobile_v2.adapter.User_home_category_filter_recycler_view;
import com.sprinsec.mobile_v2.adapter.User_home_category_recycler_view_adapter;
import com.sprinsec.mobile_v2.adapter.User_home_watchlist_recycler_view_adapter;
import com.sprinsec.mobile_v2.data.api.AddToCartService;
import com.sprinsec.mobile_v2.data.api.GetSingleProductViewService;
import com.sprinsec.mobile_v2.data.api.SetToWatchlistService;
import com.sprinsec.mobile_v2.data.model.SingleProductViewSliderModel;
import com.sprinsec.mobile_v2.data.model.UserHomeCategoryModel;
import com.sprinsec.mobile_v2.data.model.UserHomeProductModel;
import com.sprinsec.mobile_v2.data.model.UserProfileSliderModel;
import com.sprinsec.mobile_v2.data.model.UserSingleProductViewProductReviewModel;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.ui.fragments.user.user_watchlist_fragment;
import com.sprinsec.mobile_v2.util.CommonFunctions;
import com.sprinsec.mobile_v2.util.Config;

import java.util.ArrayList;
import java.util.List;

public class user_single_product_view_activity extends AppCompatActivity {
    private static final int CALL_PERMISSION_REQUEST = 100;
    ViewPager2 viewPager;
    List<SingleProductViewSliderModel> sliderItems;
    private int totalProductQuantity;
    private TextView productNameTextField, productPriceTextField, productQuantityTextField, productDescriptionTextField, fragment_user_single_product_view_stock_status;
    private RatingBar productRatingBar;
    private ImageView productSmallImage1;
    private String Passed_product_id;
    private ImageView backIcon;
    private String phoneNumber;
    private RecyclerView productReviewsRecyclerView;
    private ImageView fragment_user_single_product_view_watchlist_toggle_button;
    private Button fragment_user_single_product_view_add_to_cart;
    private Button fragment_user_single_product_view_buy_now;

    private void requestCallPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            openDialer(); // Open dialer if permission is already granted
        } else {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CALL_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openDialer(); // Permission granted, open dialer
            } else {
                Toast.makeText(this, "Permission denied to access the dialer", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openDialer() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity__user__single_product_view);
        setupWindowInsets();
        initializeViews();
        handleIntent();

        fragment_user_single_product_view_add_to_cart = findViewById(R.id.fragment_user_single_product_view_add_to_cart);
        fragment_user_single_product_view_buy_now = findViewById(R.id.fragment_user_single_product_view_buy_now);
        fragment_user_single_product_view_stock_status = findViewById(R.id.fragment_user_single_product_view_stock_status);
        fragment_user_single_product_view_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToCartService.Send(Passed_product_id, productQuantityTextField.getText().toString(), new GeneralCallBack() {
                    @Override
                    public void onSuccess(JsonObject response) {
                        runOnUiThread(() -> {
                            try {
                                Log.i("befit_logs", "Add to Cart Response: " + response);
                                if (response.has("status") && "success".equals(response.get("status").getAsString()) && response.has("message") && "added".equals(response.get("message").getAsString())) {
                                    Toast.makeText(user_single_product_view_activity.this, "Product Added To Cart", Toast.LENGTH_SHORT).show();

                                } else if (response.has("status") && "success".equals(response.get("status").getAsString()) && response.has("message") && "removed".equals(response.get("message").getAsString())) {
                                    Toast.makeText(user_single_product_view_activity.this, "Product Removed From Cart", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(user_single_product_view_activity.this, "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.e("befit_logs", "Dashboard overview data processing error: ", e);
                            }
                        });
                    }

                    @Override
                    public void onFailure(String errorMessage) {

                    }
                });
            }
        });

        fragment_user_single_product_view_watchlist_toggle_button = findViewById(R.id.fragment_user_single_product_view_watchlist_toggle_button);
        fragment_user_single_product_view_watchlist_toggle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment_user_single_product_view_watchlist_toggle_button.getImageTintList().getDefaultColor() == ContextCompat.getColor(user_single_product_view_activity.this, R.color.colorPink)) {

                    SetToWatchlistService.Send(Passed_product_id, productQuantityTextField.getText().toString(), "no", new GeneralCallBack() {
                        @Override
                        public void onSuccess(JsonObject response) {
                            runOnUiThread(() -> {
                                Log.i("befit_logs", "Watchlist Removed: " + response);
                                if (response.get("status").getAsString().equals("deleted")) {
                                    fragment_user_single_product_view_watchlist_toggle_button.setImageTintList(ContextCompat.getColorStateList(user_single_product_view_activity.this, R.color.gray_200));
                                }
                            });
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            runOnUiThread(() -> Log.e("befit_logs", "Error: " + errorMessage));
                        }
                    });
                } else {
                    SetToWatchlistService.Send(Passed_product_id, productQuantityTextField.getText().toString(), "yes", new GeneralCallBack() {
                        @Override
                        public void onSuccess(JsonObject response) {
                            runOnUiThread(() -> {
                                Log.i("befit_logs", "Watchlist Added: " + response);
                                if (response.get("status").getAsString().equals("inserted") || response.get("status").getAsString().equals("updated")) {
                                    fragment_user_single_product_view_watchlist_toggle_button.setImageTintList(ContextCompat.getColorStateList(user_single_product_view_activity.this, R.color.colorPink));
                                }
                            });
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            runOnUiThread(() -> Log.e("befit_logs", "Error: " + errorMessage));
                        }
                    });
                }
            }
        });

        productReviewsRecyclerView = findViewById(R.id.fragment_user_single_product_view_reviews);
        productReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewPager = findViewById(R.id.fragment_user_single_product_view_viewPager2);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == sliderItems.size() - 1) {
                    viewPager.postDelayed(() -> viewPager.setCurrentItem(0, true), 4000);
                } else {
                    viewPager.postDelayed(() -> viewPager.setCurrentItem(position + 1, true), 4000);
                }
            }
        });
        ImageView fragment_user_single_product_view_contact_seller = findViewById(R.id.fragment_user_single_product_view_contact_seller);
        fragment_user_single_product_view_contact_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneNumber != null) {
                    new AlertDialog.Builder(user_single_product_view_activity.this)
                            .setMessage("Would you like to call the Seller?")
                            .setPositiveButton("Yes", (dialog, which) -> requestCallPermission())
                            .setNegativeButton("No", null)
                            .show();
                } else {
                    Toast.makeText(user_single_product_view_activity.this, "Cannot Contact Seller Now", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backIcon = findViewById(R.id.fragment_user_single_product_view_back_icon);

        backIcon.setOnClickListener(v -> CommonFunctions.backToHome(backIcon, this, user_home_interface.class));
    }


    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.fragment_user_single_product_view_constraint_layout),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
                }
        );
    }

    private void initializeViews() {
        productNameTextField = findViewById(R.id.fragment_user_single_product_view_item_name);
        productPriceTextField = findViewById(R.id.fragment_user_single_product_view_price);
        productQuantityTextField = findViewById(R.id.fragment_user_single_product_view_quantity_text);
        productDescriptionTextField = findViewById(R.id.fragment_user_single_product_view_description);
        productRatingBar = findViewById(R.id.fragment_user_single_product_view_ratings);
        productSmallImage1 = findViewById(R.id.fragment_user_single_product_view_image1);

        setupQuantityControls();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        Passed_product_id = intent.getStringExtra("product_id");

        if (Passed_product_id != null) {
            displayInitialProductInfo(intent);
            fetchProductDetails(Passed_product_id);
        } else {
            navigateToHome();
        }
    }

    private void displayInitialProductInfo(Intent intent) {
        productNameTextField.setText(intent.getStringExtra("product_name"));
        productPriceTextField.setText(intent.getStringExtra("product_price"));

        String productRating = intent.getStringExtra("product_rating");
        if (productRating != null) {
            productRatingBar.setRating(Float.parseFloat(productRating));
        }

        String productImageUrl = intent.getStringExtra("product_image");
        if (productImageUrl != null) {
            loadProductImage(productImageUrl, productSmallImage1);
        }
    }

    private void loadProductImage(String imageUrl, ImageView productSmallImage) {
        Glide.with(this)
                .load(imageUrl)
                .error(R.drawable.default_product_image)
                .placeholder(R.drawable.default_product_image)
                .into(productSmallImage);
    }

    private void fetchProductDetails(String productId) {
        GetSingleProductViewService.getData(new GeneralCallBack() {
            @Override
            public void onSuccess(JsonObject response) {
                runOnUiThread(() -> handleProductResponse(response));
            }

            @Override
            public void onFailure(String errorMessage) {
                runOnUiThread(() -> Log.e("befit_logs", "Error: " + errorMessage));
            }
        }, productId);
    }

    private void handleProductResponse(JsonObject response) {
        Log.i("befit_logs", "Single Product Data: " + response);
        try {
            if (!response.has("product") || !response.get("product").isJsonObject()) {
                return;
            }

            JsonObject product = response.getAsJsonObject("product");
            updateProductDetails(product);
            handleProductImages(product.getAsJsonArray("img_paths"));

        } catch (Exception e) {
            Log.e("befit_logs", "Error processing product data: ", e);
        }
    }

    private void updateProductDetails(JsonObject product) {
        productDescriptionTextField.setText(product.get("description").getAsString());
        totalProductQuantity = product.get("qty").getAsInt();
        if (totalProductQuantity == 0) {
            fragment_user_single_product_view_stock_status.setText("Out of Stock");
            fragment_user_single_product_view_stock_status.setTextColor(ContextCompat.getColor(this, R.color.red_600));
            fragment_user_single_product_view_stock_status.setBackgroundResource(R.drawable.product_out_of_stock_background);
        } else {
            fragment_user_single_product_view_stock_status.setText("In Stock");
            fragment_user_single_product_view_stock_status.setTextColor(ContextCompat.getColor(this, R.color.green_600));
            fragment_user_single_product_view_stock_status.setBackgroundResource(R.drawable.product_instock_background);
        }
        if (product.has("seller_mobile")) {
            phoneNumber = product.get("seller_mobile").getAsString();
        }

        if (product.has("watchlisted") && product.get("watchlisted").getAsBoolean()) {
            fragment_user_single_product_view_watchlist_toggle_button.setImageTintList(ContextCompat.getColorStateList(user_single_product_view_activity.this, R.color.colorPink));
        } else {
            fragment_user_single_product_view_watchlist_toggle_button.setImageTintList(ContextCompat.getColorStateList(user_single_product_view_activity.this, R.color.gray_200));
        }
        List<UserSingleProductViewProductReviewModel> productReviewsList = new ArrayList<>();
        if (product.has("feedbacks") && product.get("feedbacks").isJsonArray()) {
            JsonArray ProductReviews = product.getAsJsonArray("feedbacks");
            for (JsonElement productReviewElement : ProductReviews) {
                JsonObject productReview = productReviewElement.getAsJsonObject();
                Float rating = productReview.get("stars").getAsFloat();
                String datetime_added = productReview.get("datetime_added").getAsString();
                String comment = productReview.get("comment").getAsString();
                String user_name = productReview.get("user_name").getAsString();

                productReviewsList.add(new UserSingleProductViewProductReviewModel(rating, comment, datetime_added, user_name));
            }
        } else {
            productReviewsRecyclerView.setVisibility(View.GONE);
            TextView fragment_user_single_product_view_reviews_label = findViewById(R.id.fragment_user_single_product_view_reviews_label);
            fragment_user_single_product_view_reviews_label.setVisibility(View.GONE);
        }
        productReviewsRecyclerView.setAdapter(new UserSingleProductViewProductReviewRecyclerViewAdapter(productReviewsList));
    }

    private void handleProductImages(JsonArray imgPaths) {
        String fullImagePath = null;
        sliderItems = new ArrayList<>(); // Initialize sliderItems here
        for (int i = 0; i < imgPaths.size(); i++) {
            String imgPath = imgPaths.get(i).getAsString();
            fullImagePath = Config.BACKEND_URL + imgPath.substring(2);

            int imageViewId = getResources().getIdentifier("fragment_user_single_product_view_image" + (i + 1), "id", getPackageName());
            ImageView productImage = findViewById(imageViewId);
            if (productImage != null) {
                Glide.with(this)
                        .load(fullImagePath)
                        .error(R.drawable.default_product_image)
                        .placeholder(R.drawable.default_product_image)
                        .into(productImage);

                sliderItems.add(new SingleProductViewSliderModel(fullImagePath)); // Add to sliderItems
            }
        }
        if (sliderItems.size() != 0) {
            UserSingleProductViewPager2Adapter adapter = new UserSingleProductViewPager2Adapter(sliderItems);
            viewPager.setAdapter(adapter);
        }
    }

    private void setupQuantityControls() {
        TextView plusButton = findViewById(R.id.fragment_user_single_product_view_quantity_plus);
        TextView minusButton = findViewById(R.id.fragment_user_single_product_view_quantity_minus);

        plusButton.setOnClickListener(v -> updateQuantity(true));
        minusButton.setOnClickListener(v -> updateQuantity(false));
    }

    private void updateQuantity(boolean increment) {
        int currentQuantity = Integer.parseInt(productQuantityTextField.getText().toString());

        if (increment && currentQuantity < totalProductQuantity) {
            productQuantityTextField.setText(String.valueOf(currentQuantity + 1));
        } else if (!increment && currentQuantity > 1) {
            productQuantityTextField.setText(String.valueOf(currentQuantity - 1));
        }
    }

    private void navigateToHome() {
        startActivity(new Intent(this, user_home_interface.class));
        finish();
    }
}