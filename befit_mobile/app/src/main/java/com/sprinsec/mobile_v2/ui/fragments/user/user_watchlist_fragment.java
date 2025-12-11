package com.sprinsec.mobile_v2.ui.fragments.user;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.adapter.User_home_watchlist_recycler_view_adapter;
import com.sprinsec.mobile_v2.data.api.GetWatchlistService;
import com.sprinsec.mobile_v2.data.db.SQLiteDatabaseHelper;
import com.sprinsec.mobile_v2.data.model.UserHomeProductModel;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.interfaces.RecyclerViewInterface;
import com.sprinsec.mobile_v2.ui.user.user_home_interface;
import com.sprinsec.mobile_v2.ui.user.user_single_product_view_activity;
import com.sprinsec.mobile_v2.util.BroadCastReciever.NetworkStatusManager;
import com.sprinsec.mobile_v2.util.BroadCastReciever.SportsApplication;
import com.sprinsec.mobile_v2.util.CommonFunctions;
import com.sprinsec.mobile_v2.util.Config;
import com.sprinsec.mobile_v2.util.repository.WatchlistRepository;

import java.util.ArrayList;

public class user_watchlist_fragment extends Fragment implements RecyclerViewInterface {
    private ArrayList<UserHomeProductModel> userWatchListList;
    private RecyclerView recyclerView;
    private WatchlistRepository watchlistRepository;
    private ProgressBar fragment_user_watchlist_progress_bar;
    private NetworkStatusManager networkStatusManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__user__watchlist, container, false);
        userWatchListList = new ArrayList<>();
        networkStatusManager = new NetworkStatusManager(getActivity());
        watchlistRepository = new WatchlistRepository(getContext());
        fragment_user_watchlist_progress_bar = view.findViewById(R.id.fragment_user_watchlist_progress_bar);
        recyclerView = view.findViewById(R.id.fragment_user_watchlist_recycler_view);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;

        int spanCount = screenWidthDp > 600 ? 4 : 2;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        fragment_user_watchlist_progress_bar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        GetWatchlistService.getHomeWatchlist(new GeneralCallBack() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                if (getActivity() == null) return; // Prevent crashes if fragment is detached

                getActivity().runOnUiThread(() -> {
                    Log.i("befit_logs", "WatchList Data ::" + jsonObject.toString());
                    try {
                        userWatchListList.clear();

                        if (jsonObject.has("watchlist") && jsonObject.get("watchlist").isJsonArray()) {
                            JsonArray products = jsonObject.getAsJsonArray("watchlist");
                            fragment_user_watchlist_progress_bar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            for (JsonElement productElement : products) {
                                JsonObject product = productElement.getAsJsonObject();
                                String id = String.valueOf(product.get("id").getAsInt());
                                String title = product.get("title").getAsString();
                                String price = String.valueOf("Rs. " + product.get("price").getAsInt());
                                String avgStars = String.valueOf(product.get("avg_stars").getAsFloat());
                                String imgPath = product.get("img_path").getAsString();
                                String fullImagePath = Config.BACKEND_URL + imgPath.substring(3);
                                Log.i("befit_logs", fullImagePath);
                                userWatchListList.add(new UserHomeProductModel(id, title, price, fullImagePath, avgStars));
                            }
                            watchlistRepository.updateWatchlist(userWatchListList);
                            updateUI(false, view, recyclerView);
                        } else {
                            showEmptyState(view, recyclerView);
                        }
                    } catch (Exception e) {
                        Log.e("befit_logs", "user_home_fragment category error: ", e);
                        loadOfflineData(view, recyclerView);
                    }
                    ImageButton fragment_user_watchlist_delete_button = view.findViewById(R.id.fragment_user_watchlist_delete_button);
                    recyclerView.setAdapter(new User_home_watchlist_recycler_view_adapter(userWatchListList, fragment_user_watchlist_delete_button, user_watchlist_fragment.this, watchlistRepository));
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                if (getActivity() == null) return;
                getActivity().runOnUiThread(() -> {
                    Log.e("befit_logs", "Error: " + errorMessage);
                    loadOfflineData(view, recyclerView);
                });
            }
        });
        return view;
    }

    private void loadOfflineData(View view, RecyclerView recyclerView) {
        userWatchListList = watchlistRepository.getWatchlist();
        if (userWatchListList.isEmpty()) {
            showEmptyState(view, recyclerView);
        } else {
            fragment_user_watchlist_progress_bar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Showing offline data", Toast.LENGTH_SHORT).show();
            updateUI(true, view, recyclerView);
        }
    }

    private void updateUI(boolean isOffline, View view, RecyclerView recyclerView) {
        if (getActivity() == null) return;

        ImageButton deleteButton = view.findViewById(R.id.fragment_user_watchlist_delete_button);
        recyclerView.setAdapter(new User_home_watchlist_recycler_view_adapter(
                userWatchListList,
                deleteButton,
                this, watchlistRepository
        ));

        if (isOffline) {
            // Show offline indicator if needed
            // You might want to add a banner or indicator in your layout for this
        }
    }

    private void showEmptyState(View view, RecyclerView recyclerView) {
        recyclerView.setVisibility(View.GONE);
        fragment_user_watchlist_progress_bar.setVisibility(View.GONE);

        ImageView nothingIcon = view.findViewById(R.id.fragment_user_watchlist_nothing_icon);
        TextView nothingText = view.findViewById(R.id.fragment_user_watchlist_nothing_text);
        Button shoppingButton = view.findViewById(R.id.fragment_user_watchlist_nothing_shopping_button);

        nothingIcon.setVisibility(View.VISIBLE);
        nothingText.setVisibility(View.VISIBLE);
        shoppingButton.setVisibility(View.VISIBLE);

        shoppingButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), user_home_interface.class);
            startActivity(intent);
        });
    }

    // Call this when deleting an item
    public void onWatchlistItemDeleted(String productId) {
        watchlistRepository.deleteWatchlistItem(productId);
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(getActivity(), user_single_product_view_activity.class);
        intent.putExtra("product_id", userWatchListList.get(position).getProductId());
        intent.putExtra("product_name", userWatchListList.get(position).getProductName());
        intent.putExtra("product_price", userWatchListList.get(position).getProductPrice().substring(3));
        intent.putExtra("product_image", userWatchListList.get(position).getProductImage());
        intent.putExtra("product_rating", userWatchListList.get(position).getRating());
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

}