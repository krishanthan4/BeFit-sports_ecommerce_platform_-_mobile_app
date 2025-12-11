package com.sprinsec.mobile_v2.ui.fragments.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.RangeSlider;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.adapter.User_home_product_recycler_view_adapter;
import com.sprinsec.mobile_v2.data.api.GetHomeProductsService;
import com.sprinsec.mobile_v2.data.api.user_advanced_search_service;
import com.sprinsec.mobile_v2.data.model.UserHomeProductModel;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.interfaces.RecyclerViewInterface;
import com.sprinsec.mobile_v2.ui.user.user_home_interface;
import com.sprinsec.mobile_v2.ui.user.user_single_product_view_activity;
import com.sprinsec.mobile_v2.util.BroadCastReciever.NetworkStatusManager;
import com.sprinsec.mobile_v2.util.BroadCastReciever.SportsApplication;
import com.sprinsec.mobile_v2.util.CommonFunctions;
import com.sprinsec.mobile_v2.util.Config;

import java.util.ArrayList;
import java.util.List;

public class user_search_fragment extends Fragment implements RecyclerViewInterface {
    SearchView fragment_user_search_search_view;
    ImageView fragment_user_search_nothing_icon;
    TextView fragment_user_search_nothing_text;
    Button fragment_user_search_nothing_shopping_button;
    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;
    private RadioGroup priceRadioGroup, conditionRadioGroup;
    private Button applyFiltersButton, clearFiltersButton;
    private View bottomSheetContainer;
    private ArrayList<UserHomeProductModel> userHomeProductModelList;
    private androidx.recyclerview.widget.RecyclerView userHomeProductRecyclerView;
    private String priceFrom = null;
    private String priceTo = null;
    private String condition = null;
    private ProgressBar fragment_user_search_progressbar;
    private NetworkStatusManager networkStatusManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__user__search, container, false);
        initializeViews(view);
        setupBottomSheet(view);
        fragment_user_search_progressbar.setVisibility(View.VISIBLE);
        userHomeProductRecyclerView.setVisibility(View.GONE);
        setupFilterListeners();
        Bundle bundle = getArguments();

        if (bundle != null && bundle.containsKey("search_query")) {
            String searchQuery = bundle.getString("search_query");
            if (searchQuery != null) {
                getSearchedProducts(searchQuery, view);
                fragment_user_search_search_view.setQuery(searchQuery, false);
            }
        } else {
            getSearchedProducts("", view);
        }
        fragment_user_search_search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getSearchedProducts(query, view);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle text change here
                getSearchedProducts(newText, view);

                return true;
            }
        });
        return view;
    }

    private void initializeViews(View view) {
        userHomeProductModelList = new ArrayList<>();
        fragment_user_search_progressbar = view.findViewById(R.id.fragment_user_search_progressbar);
        fragment_user_search_search_view = view.findViewById(R.id.fragment_user_search_search_view);
        networkStatusManager = new NetworkStatusManager(getActivity());
        bottomSheetContainer = view.findViewById(R.id.bottomSheetContainer);
        LinearLayout bottomSheet = view.findViewById(R.id.bottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        fragment_user_search_nothing_icon = view.findViewById(R.id.fragment_user_search_nothing_icon);
        fragment_user_search_nothing_text = view.findViewById(R.id.fragment_user_search_nothing_text);
        fragment_user_search_nothing_shopping_button = view.findViewById(R.id.fragment_user_search_nothing_shopping_button);
        GridLayoutManager layoutManager;
        // Set up RecyclerView with padding for bottom sheet
        userHomeProductRecyclerView = view.findViewById(R.id.fragment_user_search_recycler_view);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;

        int spanCount = screenWidthDp > 600 ? 4 : 2;
        layoutManager = new GridLayoutManager(getContext(), spanCount);
        userHomeProductRecyclerView.setLayoutManager(layoutManager);

        // Add this to prevent recycler view from interfering with bottom sheet
        userHomeProductRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });

        priceRadioGroup = view.findViewById(R.id.priceRadioGroup);
        conditionRadioGroup = view.findViewById(R.id.conditionRadioGroup);
        applyFiltersButton = view.findViewById(R.id.applyFiltersButton);
        clearFiltersButton = view.findViewById(R.id.clearFiltersButton);
    }

    private void setupBottomSheet(View view) {
        // Set initial state
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        // Set up filter icon click listener
        ImageView filterIcon = view.findViewById(R.id.fragment_user_search_filter);
        filterIcon.setOnClickListener(v -> {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        // Prevent touches from passing through to views behind the bottom sheet
        View bottomSheet = view.findViewById(R.id.bottomSheet);
        bottomSheet.setOnClickListener(v -> {
            // Consume the click event
        });

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // Handle background dimming
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetContainer.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    bottomSheetContainer.setBackgroundColor(Color.parseColor("#80000000"));
                }

                // Disable RecyclerView scrolling when bottom sheet is expanded
                userHomeProductRecyclerView.setNestedScrollingEnabled(
                        newState == BottomSheetBehavior.STATE_HIDDEN
                );
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // Calculate alpha for background dim
                int alpha = (int) (slideOffset * 128); // 128 = 80 in hex (50% opacity)
                bottomSheetContainer.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
            }
        });
    }

    private void setupFilterListeners() {
        // Apply filters button
        applyFiltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterOptions filterOptions = collectFilterOptions();
                if (filterOptions != null) {
                    applyFilters(filterOptions);
                }
                // Hide bottom sheet
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        // Clear filters button
        clearFiltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFilters();
            }
        });
    }

    private void getSearchedProducts(String searchText, View view) {
        user_advanced_search_service.Send(searchText, priceFrom, priceTo, condition, new GeneralCallBack() {
            @Override
            public void onSuccess(JsonObject response) {
                if (getActivity() == null)
                    return; // Prevent crashes if fragment is detached

                getActivity().runOnUiThread(() -> {
                    Log.i("befit_logs", "User Search Fragment Data ::" + response.toString());
                    try {
                        if (response.has("products") && response.get("products").isJsonArray()) {
                            fragment_user_search_progressbar.setVisibility(View.GONE);
                            userHomeProductRecyclerView.setVisibility(View.VISIBLE);
                            userHomeProductModelList.clear();
                            JsonArray products = response.getAsJsonArray("products");
                            userHomeProductRecyclerView.setVisibility(View.VISIBLE);
                            fragment_user_search_nothing_icon.setVisibility(View.GONE);
                            fragment_user_search_nothing_text.setVisibility(View.GONE);
                            fragment_user_search_nothing_shopping_button.setVisibility(View.GONE);
                            for (JsonElement productElement : products) {
                                JsonObject product = productElement.getAsJsonObject();
                                String id = String.valueOf(product.get("id").getAsInt());
                                String title = product.get("title").getAsString();
                                String price = String.valueOf("Rs. " + product.get("price").getAsInt());
                                String avgStars = String.valueOf(product.get("avg_stars").getAsFloat());
                                String imgPath = product.get("img_path").getAsString();
                                String fullImagePath = Config.BACKEND_URL + imgPath.substring(3);
                                Log.i("befit_logs", fullImagePath);
                                userHomeProductModelList.add(new UserHomeProductModel(id, title, price, fullImagePath, avgStars));
                            }
                        } else {
                            fragment_user_search_progressbar.setVisibility(View.GONE);
                            userHomeProductRecyclerView.setVisibility(View.GONE);
                            fragment_user_search_nothing_icon.setVisibility(View.VISIBLE);
                            fragment_user_search_nothing_text.setVisibility(View.VISIBLE);
                            fragment_user_search_nothing_shopping_button.setVisibility(View.VISIBLE);
                            fragment_user_search_nothing_shopping_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getContext(), user_home_interface.class);
                                    startActivity(intent);
                                }
                            });

                        }
                    } catch (Exception e) {
                        Log.e("befit_logs", "user_home_fragment product error: ", e);
                    }

                    userHomeProductRecyclerView.setAdapter(new User_home_product_recycler_view_adapter(userHomeProductModelList, user_search_fragment.this));
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                if (getActivity() == null) return;

                getActivity().runOnUiThread(() -> {
                    Log.e("befit_logs", "Error: " + errorMessage);
                    Toast.makeText(getContext(), "Something Went Wrong. Please Try Again Later", Toast.LENGTH_SHORT).show();
                });
            }
        });

    }

    private FilterOptions collectFilterOptions() {
        FilterOptions options = new FilterOptions();
        int selectedPriceId = priceRadioGroup.getCheckedRadioButtonId();
        if (selectedPriceId != -1) {
            RadioButton selectedPrice = priceRadioGroup.findViewById(selectedPriceId);
            options.setPriceRange(selectedPrice.getText().toString());
        }
        int selectedConditionId = conditionRadioGroup.getCheckedRadioButtonId();
        if (selectedConditionId != -1) {
            RadioButton selectedCondition = conditionRadioGroup.findViewById(selectedConditionId);
            options.setCondition(selectedCondition.getText().toString());
        }
        if (selectedPriceId != -1 || selectedConditionId != -1) return options;

        return null;
    }

    private void resetFilters() {
        priceRadioGroup.clearCheck();
        conditionRadioGroup.clearCheck();
    }

    private void applyFilters(FilterOptions options) {
        if (options.getPriceRange() != null) {
            if (options.getPriceRange().equals("Rs.10,000 +")) {
                priceFrom = "10000";
                priceTo = "0";
            } else {
                String[] priceRange = options.getPriceRange().replace("Rs.", "").split(" - ");
                if (priceRange.length == 2) {
                    priceFrom = priceRange[0].trim();
                    priceTo = priceRange[1].trim();
                }
            }
        }
        if (options.getCondition() != null) {
            condition = options.getCondition();
        }
        getSearchedProducts("", getView());
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(getActivity(), user_single_product_view_activity.class);
        intent.putExtra("product_id", userHomeProductModelList.get(position).getProductId());
        intent.putExtra("product_name", userHomeProductModelList.get(position).getProductName());
        intent.putExtra("product_price", userHomeProductModelList.get(position).getProductPrice().substring(3));
        intent.putExtra("product_image", userHomeProductModelList.get(position).getProductImage());
        intent.putExtra("product_rating", userHomeProductModelList.get(position).getRating());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((SportsApplication) getActivity().getApplication()).addConnectivityListener(networkStatusManager);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((SportsApplication) getActivity().getApplication()).removeConnectivityListener(networkStatusManager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (networkStatusManager != null) {
            networkStatusManager.onDestroy();
        }
    }

    // Filter options data class
    private static class FilterOptions {
        private String priceRange;
        private String condition;

        // Getters and setters
        public String getPriceRange() {
            return priceRange;
        }

        public void setPriceRange(String priceRange) {
            this.priceRange = priceRange;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }
    }
}