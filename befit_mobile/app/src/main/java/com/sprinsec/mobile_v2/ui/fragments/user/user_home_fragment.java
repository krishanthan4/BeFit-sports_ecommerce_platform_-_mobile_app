package com.sprinsec.mobile_v2.ui.fragments.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.adapter.UserCartProductRecyclerViewAdapter;
import com.sprinsec.mobile_v2.adapter.UserProfileSliderViewPager2Adapter;
import com.sprinsec.mobile_v2.adapter.User_home_category_filter_recycler_view;
import com.sprinsec.mobile_v2.adapter.User_home_category_recycler_view_adapter;
import com.sprinsec.mobile_v2.adapter.User_home_product_recycler_view_adapter;
import com.sprinsec.mobile_v2.data.api.GetHomeCategoryService;
import com.sprinsec.mobile_v2.data.api.GetHomeProductsService;
import com.sprinsec.mobile_v2.data.model.UserHomeCategoryModel;
import com.sprinsec.mobile_v2.data.model.UserHomeProductModel;
import com.sprinsec.mobile_v2.data.model.UserHomeSliderModel;
import com.sprinsec.mobile_v2.data.model.UserProfileSliderModel;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.interfaces.RecyclerViewInterface;
import com.sprinsec.mobile_v2.ui.user.user_home_interface;
import com.sprinsec.mobile_v2.ui.user.user_single_product_view_activity;
import com.sprinsec.mobile_v2.util.BroadCastReciever.NetworkStatusManager;
import com.sprinsec.mobile_v2.util.BroadCastReciever.SportsApplication;
import com.sprinsec.mobile_v2.util.CommonFunctions;
import com.sprinsec.mobile_v2.util.Config;
import com.sprinsec.mobile_v2.util.contract.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class user_home_fragment extends Fragment implements RecyclerViewInterface {
    private static ArrayList<UserHomeProductModel> userHomeProductModelList;
    private static ArrayList<UserHomeCategoryModel> userHomeCategoryModelList;
    private ProgressBar fragment_user_home_category_progress_bar, fragment_user_home_viewPager2_progress_bar, fragment_user_home_product_progress_bar;
    private RecyclerView userHomeProductRecyclerView, userHomeCategoryRecyclerView;
    private ViewPager2 viewPager;
    private NetworkStatusManager networkStatusManager;
    private ProductRepository productRepository;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__user__home, container, false);
        initialization(view);
        askNotificationPermission();
        GetHomeProductsService.getHomeProducts(new GeneralCallBack() {
            @Override
            public void onSuccess(JsonObject response) {
                if (getActivity() == null) return; // Prevent crashes if fragment is detached

                getActivity().runOnUiThread(() -> {
                    Log.i("befit_logs", "Product Data ::" + response.toString());
                    try {
                        if (response.has("products") && response.get("products").isJsonArray()) {
                            JsonArray products = response.getAsJsonArray("products");
                            fragment_user_home_product_progress_bar.setVisibility(View.GONE);
                            userHomeProductRecyclerView.setVisibility(View.VISIBLE);
                            for (JsonElement productElement : products) {
                                JsonObject product = productElement.getAsJsonObject();
                                String id = String.valueOf(product.get("id").getAsInt());
                                String title = product.get("title").getAsString();
                                String price = String.valueOf("Rs. " + product.get("price").getAsInt());
                                String avgStars = String.valueOf(product.get("avg_stars").getAsFloat());

                              String fullImagePath;
                              if (product.has("img_path") && !product.get("img_path").isJsonNull() && !product.get("img_path").getAsString().isEmpty()) {
                                  String imgPath = product.get("img_path").getAsString();
                                  fullImagePath = Config.BACKEND_URL + imgPath.substring(3);
                                  Log.i("befit_logs", fullImagePath);
                              } else {
                                  fullImagePath = null;
                              }
                                userHomeProductModelList.add(new UserHomeProductModel(id, title, price, fullImagePath, avgStars));
                            }
                            productRepository.updateProducts(userHomeProductModelList);
                            updateProductUI();
                        }

                    } catch (Exception e) {
                        Log.e("befit_logs", "user_home_fragment product error: ", e);
                        loadProductOfflineData(view, userHomeProductRecyclerView);

                    }

                    userHomeProductRecyclerView.setAdapter(new User_home_product_recycler_view_adapter(userHomeProductModelList, user_home_fragment.this));
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                if (getActivity() == null) return;

                getActivity().runOnUiThread(() -> {
                    Log.e("befit_logs", "Error: " + errorMessage);
                    loadProductOfflineData(view, userHomeProductRecyclerView);
                });
            }
        });
        GetHomeCategoryService.getHomeCategory(new GeneralCallBack() {
            @Override
            public void onSuccess(JsonObject response) {
                if (getActivity() == null) return; // Prevent crashes if fragment is detached

                getActivity().runOnUiThread(() -> {
                    Log.i("befit_logs", "Category Data ::" + response.toString());
                    try {
                        if (response.has("categories") && response.get("categories").isJsonArray()) {
                            JsonArray categories = response.getAsJsonArray("categories");
                            userHomeCategoryRecyclerView.setVisibility(View.VISIBLE);
                            fragment_user_home_category_progress_bar.setVisibility(View.GONE);
                            for (JsonElement categoryElement : categories) {
                                JsonObject category = categoryElement.getAsJsonObject();
                                String id = String.valueOf(category.get("cat_id").getAsInt());
                                String title = category.get("cat_name").getAsString();
                                String imgPath = category.get("cat_img").getAsString();
                                String fullImagePath = Config.BACKEND_URL + imgPath.substring(2);
                                Log.i("befit_logs", fullImagePath);
                                userHomeCategoryModelList.add(new UserHomeCategoryModel(id, title, fullImagePath));
                            }
                        }
                    } catch (Exception e) {
                        Log.e("befit_logs", "user_home_fragment category error: ", e);
                    }
                    Log.i("befit_logs", "CategoryData ::" + userHomeCategoryModelList.toString());
                    userHomeCategoryRecyclerView.setAdapter(new User_home_category_recycler_view_adapter(userHomeCategoryModelList));
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

//        set slider
        setSliderItems();

        return view;
    }

    private void loadProductOfflineData(View view, RecyclerView productRecyclerView) {
        userHomeProductModelList = productRepository.getProducts();
        if (userHomeProductModelList.isEmpty()) {

        } else {
            fragment_user_home_product_progress_bar.setVisibility(View.GONE);
            productRecyclerView.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Showing offline data", Toast.LENGTH_SHORT).show();
            updateProductUI();
        }
    }

    private void updateProductUI() {
        if (getActivity() == null) return;

        user_home_fragment.this.userHomeProductRecyclerView.setAdapter(new User_home_product_recycler_view_adapter(
                userHomeProductModelList,
                user_home_fragment.this));
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

    private void sendToSearchFragment(String query) {
        Bundle bundle = new Bundle();
        bundle.putString("search_query", query);
        user_search_fragment fragment = new user_search_fragment();
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.user_home_interface_fragment_container_view, fragment).commit();

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

    private void initialization(View view) {
        userHomeProductModelList = new ArrayList<>();
        userHomeCategoryModelList = new ArrayList<>();
        productRepository = new ProductRepository(getContext());
        networkStatusManager = new NetworkStatusManager(getActivity());
        fragment_user_home_category_progress_bar = view.findViewById(R.id.fragment_user_home_category_progress_bar);
        fragment_user_home_viewPager2_progress_bar = view.findViewById(R.id.fragment_user_home_viewPager2_progress_bar);
        fragment_user_home_product_progress_bar = view.findViewById(R.id.fragment_user_home_products_recycler_view_progress_bar);
        viewPager = view.findViewById(R.id.fragment_user_home_viewPager2);
        userHomeProductRecyclerView = view.findViewById(R.id.fragment_user_home_products_recycler_view);
        userHomeCategoryRecyclerView = view.findViewById(R.id.fragment_user_home_top_category_images_recycler_view);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;

        int spanCount = screenWidthDp > 600 ? 4 : 2; // Tablets usually have >600dp width
        userHomeProductRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));

        userHomeCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        SearchView fragment_user_home_searchView = view.findViewById(R.id.fragment_user_home_searchView);
        fragment_user_home_searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sendToSearchFragment(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        fragment_user_home_category_progress_bar.setVisibility(View.VISIBLE);
        fragment_user_home_viewPager2_progress_bar.setVisibility(View.VISIBLE);
        fragment_user_home_product_progress_bar.setVisibility(View.VISIBLE);
        userHomeProductRecyclerView.setVisibility(View.GONE);
        userHomeCategoryRecyclerView.setVisibility(View.GONE);
    }

    private void setSliderItems() {
        List<UserHomeSliderModel> sliderItems = new ArrayList<>();
        sliderItems.add(new UserHomeSliderModel(Config.BACKEND_URL + "public/images/slider_images/carousel1.jpg"));
        sliderItems.add(new UserHomeSliderModel(Config.BACKEND_URL + "public/images/slider_images/carousel2.jpg"));
        sliderItems.add(new UserHomeSliderModel(Config.BACKEND_URL + "public/images/slider_images/carousel3.jpg"));
        sliderItems.add(new UserHomeSliderModel(Config.BACKEND_URL + "public/images/slider_images/carousel4.jpg"));
        sliderItems.add(new UserHomeSliderModel(Config.BACKEND_URL + "public/images/slider_images/carousel5.jpg"));
        sliderItems.add(new UserHomeSliderModel(Config.BACKEND_URL + "public/images/slider_images/carousel6.jpg"));
        fragment_user_home_viewPager2_progress_bar.setVisibility(View.GONE);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == sliderItems.size() - 1) {
                    viewPager.postDelayed(() -> viewPager.setCurrentItem(0, true), 3000);
                } else {
                    viewPager.postDelayed(() -> viewPager.setCurrentItem(position + 1, true), 3000);
                }
            }
        });
        UserProfileSliderViewPager2Adapter adapter = new UserProfileSliderViewPager2Adapter(sliderItems);
        viewPager.setAdapter(adapter);
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    FirebaseMessaging.getInstance().subscribeToTopic("all_users")
                            .addOnCompleteListener(task -> {
                                String message = task.isSuccessful() ? "Subscribed to notifications" : "Failed to subscribe";
                                Log.d("FCM", message);
                            });
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                FirebaseMessaging.getInstance().subscribeToTopic("all_users")
                        .addOnCompleteListener(task -> {
                            String message = task.isSuccessful() ? "Subscribed to notifications" : "Failed to subscribe";
                            Log.d("FCM", message);
                        });
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }
}