package com.sprinsec.mobile_v2.ui.fragments.user;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.sprinsec.mobile_v2.adapter.UserCartProductRecyclerViewAdapter;
import com.sprinsec.mobile_v2.data.api.AddInvoiceItemService;
import com.sprinsec.mobile_v2.data.api.AddToCartService;
import com.sprinsec.mobile_v2.data.api.GetCartProductsService;
import com.sprinsec.mobile_v2.data.model.UserCartModel;
import com.sprinsec.mobile_v2.data.model.UserHomeProductModel;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.interfaces.RecyclerViewInterface;
import com.sprinsec.mobile_v2.ui.fragments.user.seller.seller_add_products_fragment;
import com.sprinsec.mobile_v2.ui.user.update_profile_activity;
import com.sprinsec.mobile_v2.ui.user.user_home_interface;
import com.sprinsec.mobile_v2.ui.user.user_single_product_view_activity;
import com.sprinsec.mobile_v2.util.Config;
import com.sprinsec.mobile_v2.util.PaymentsUtil;
import com.sprinsec.mobile_v2.util.repository.CartRepository;
import com.sprinsec.mobile_v2.util.repository.WatchlistRepository;

import java.util.ArrayList;

import lk.payhere.androidsdk.PHConfigs;
import lk.payhere.androidsdk.PHConstants;
import lk.payhere.androidsdk.PHMainActivity;
import lk.payhere.androidsdk.PHResponse;
import lk.payhere.androidsdk.model.InitRequest;
import lk.payhere.androidsdk.model.Item;
import lk.payhere.androidsdk.model.StatusResponse;

public class user_cart_fragment extends Fragment {

    private final int PAYHERE_REQUEST = 1000;
    Float amount;
    private RecyclerView cartRecyclerView;
    private ArrayList<UserCartModel> userCartModelArrayList;
    private Float SubTotal = 0.0f;
    private String orderId;
    private String Currency;
    private String itemsNames;
    private CartRepository cartRepository;
    private ProgressBar fragment_user_cart_progress_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__user__cart, container, false);
        cartRepository = new CartRepository(getContext());
        fragment_user_cart_progress_bar = view.findViewById(R.id.fragment_user_cart_progress_bar);
        cartRecyclerView = view.findViewById(R.id.fragment_user_cart_items_recycler_view);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fragment_user_cart_progress_bar.setVisibility(View.VISIBLE);
        cartRecyclerView.setVisibility(View.GONE);
        userCartModelArrayList = new ArrayList<>();
        GetCartProductsService.getCartData(new GeneralCallBack() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                if (getActivity() == null) return;

                getActivity().runOnUiThread(() -> {
                    Log.i("befit_logs", "Cart Data ::" + jsonObject.toString());
                    try {
                        if (jsonObject.has("cart_products") && jsonObject.get("cart_products").isJsonArray()) {
                            JsonArray products = jsonObject.getAsJsonArray("cart_products");
                            fragment_user_cart_progress_bar.setVisibility(View.GONE);
                            cartRecyclerView.setVisibility(View.VISIBLE);
                            userCartModelArrayList.clear();
                            for (JsonElement productElement : products) {
                                JsonObject product = productElement.getAsJsonObject();

                                // Ensure required fields exist and are valid
                                if (product.has("id") && !product.get("id").isJsonNull() && product.get("id").getAsInt() > 0 &&
                                        product.has("title") && !product.get("title").isJsonNull() && !product.get("title").getAsString().isEmpty() &&
                                        product.has("price") && !product.get("price").isJsonNull() && product.get("price").getAsFloat() > 0 &&
                                        product.has("description") && !product.get("description").isJsonNull() && !product.get("description").getAsString().isEmpty() &&
                                        product.has("total_qty") && !product.get("total_qty").isJsonNull() && product.get("total_qty").getAsInt() > 0 &&
                                        product.has("cart_qty") && !product.get("cart_qty").isJsonNull() && product.get("cart_qty").getAsInt() > 0 &&
                                        product.has("avg_stars") && !product.get("avg_stars").isJsonNull()) {

                                    String id = String.valueOf(product.get("id").getAsInt());
                                    String title = product.get("title").getAsString();
                                    String price = "Rs. " + product.get("price").getAsFloat();
                                    String description = product.get("description").getAsString();
                                    String productQty = String.valueOf(product.get("total_qty").getAsInt());
                                    String cartQty = String.valueOf(product.get("cart_qty").getAsInt());
                                    String avgStars = String.valueOf(product.get("avg_stars").getAsFloat());
                                    String shipping_fee = String.valueOf(product.get("shipping_fee").getAsFloat());
                                    String delivery_fee = String.valueOf(product.get("delivery_fee").getAsFloat());
                                    String fullImagePath = "";
                                    if (product.has("img_path") && !product.get("img_path").isJsonNull() && !product.get("img_path").getAsString().isEmpty()) {
                                        String imgPath = product.get("img_path").getAsString();
                                        fullImagePath = Config.BACKEND_URL + imgPath.substring(3); // Ensure correct URL manipulation
                                    }

                                    Log.i("befit_logs", "Cart Product :: " + title + " :: " + price + " :: " + description + " :: " + productQty + " :: " + cartQty + " :: " + avgStars + " :: " + shipping_fee + " :: " + delivery_fee + " :: " + fullImagePath);
                                    userCartModelArrayList.add(new UserCartModel(id, title, description, price, productQty, cartQty, fullImagePath, avgStars, delivery_fee, shipping_fee));
                                }
                            }
                            cartRepository.updateCart(userCartModelArrayList);
                            updateUI(false, view, cartRecyclerView);
                        } else {
                            showEmptyState(view, cartRecyclerView);
                        }
                    } catch (Exception e) {
                        Log.e("befit_logs", "user_cart_fragment product loading error: ", e);
                        loadOfflineData(view, cartRecyclerView);
                    }

                    if (!userCartModelArrayList.isEmpty()) {
                        ImageView fragment_user_cart_bin_icon = view.findViewById(R.id.fragment_user_cart_bin_icon);
                        cartRecyclerView.setAdapter(new UserCartProductRecyclerViewAdapter(userCartModelArrayList, fragment_user_cart_bin_icon, cartRepository));
                    } else {
                        Log.e("befit_logs", "Cart is empty, not setting adapter");
                    }
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                if (getActivity() == null) return;
                getActivity().runOnUiThread(() -> {
                    Log.e("befit_logs", "Error: " + errorMessage);
                    loadOfflineData(view, cartRecyclerView);
                });
            }
        });

        Button fragment_user_cart_checkout_button = view.findViewById(R.id.fragment_user_cart_checkout_button);
        fragment_user_cart_checkout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderId = java.util.UUID.randomUUID().toString().substring(0, 12);
                TextView subtotalText = view.findViewById(R.id.fragment_user_cart_subtotal_set_text);
                TextView shipping_feeText = view.findViewById(R.id.fragment_user_cart_shipping_fee_set_text);
                amount = Float.parseFloat(String.format("%.2f", Float.parseFloat(shipping_feeText.getText().toString().substring(3)))) + Float.parseFloat(String.format("%.2f", Float.parseFloat(subtotalText.getText().toString().substring(3))));
//                Log.i("befit_logs", "test_shipping_fees :: " + shipping_feeText.getText().toString().substring(3) + subtotalText.getText().toString().substring(3));
//                Log.i("befit_logs", "test_subtotal :: " + amount);
                Currency = "LKR";
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.sprinsec.mobile_v2.user_prefs",MODE_PRIVATE);
                String email = sharedPreferences.getString("email", "");
                String phone = sharedPreferences.getString("phone", "");
                String line1 = sharedPreferences.getString("line1", "");
                String line2 = sharedPreferences.getString("line2", "");
                String city = sharedPreferences.getString("city", "");
                String fname = sharedPreferences.getString("firstName", "");
                String lname = sharedPreferences.getString("lastName", "");
                String country = sharedPreferences.getString("country", "");

                String fullAddress = line1 + ", " + line2 + ", " + city + ", " + country;

                userCartModelArrayList.forEach(userCartModel -> {
                    itemsNames += userCartModel.getCartProductName() + ",";
                });

                if (fullAddress.isEmpty() || city.isEmpty() || country.isEmpty() || fname.isEmpty() || lname.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all the necessary details including First Name,Last Name,Phone Number and Address details", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), update_profile_activity.class);
                    startActivity(intent);
                } else {
                    Intent intent = PaymentsUtil.initiatePayment(getContext(), orderId, amount, Currency, itemsNames, fname, lname, email, phone, fullAddress, city, country);
                    startActivityForResult(intent, PAYHERE_REQUEST);
                }
            }
        });
        return view;
    }

    private void loadOfflineData(View view, RecyclerView cartRecyclerView) {
        userCartModelArrayList = cartRepository.getCart();
        if (userCartModelArrayList.isEmpty()) {
            showEmptyState(view, cartRecyclerView);
        } else {
            fragment_user_cart_progress_bar.setVisibility(View.GONE);
            cartRecyclerView.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Showing offline data", Toast.LENGTH_SHORT).show();
            updateUI(true, view, cartRecyclerView);
        }
    }

    private void updateUI(boolean isOffline, View view, RecyclerView cartRecyclerView) {
        if (getActivity() == null) return;

        ImageView deleteButton = view.findViewById(R.id.fragment_user_cart_bin_icon);
        cartRecyclerView.setAdapter(new UserCartProductRecyclerViewAdapter(
                userCartModelArrayList,
                deleteButton, cartRepository));

        if (isOffline) {
            // Show offline indicator if needed
            // You might want to add a banner or indicator in your layout for this
        }
    }

    private void showEmptyState(View view, RecyclerView cartRecyclerView) {
//        FrameLayout filterContainer = view.findViewById(R.id.fragment_user_cart_filter_container);
//        filterContainer.setVisibility(View.GONE);
        cartRecyclerView.setVisibility(View.GONE);
        fragment_user_cart_progress_bar.setVisibility(View.GONE);

        view.findViewById(R.id.fragment_user_cart_empty_state_group).setVisibility(View.VISIBLE);

        Button shoppingButton = view.findViewById(R.id.fragment_user_cart_nothing_shopping_button);
        shoppingButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), user_home_interface.class);
            startActivity(intent);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYHERE_REQUEST && data != null && data.hasExtra(PHConstants.INTENT_EXTRA_RESULT)) {
            PHResponse<StatusResponse> response = (PHResponse<StatusResponse>) data.getSerializableExtra(PHConstants.INTENT_EXTRA_RESULT);
            if (resultCode == Activity.RESULT_OK) {
                String msg;
                if (response != null)
                    if (response.isSuccess())
//                        TODO: check user address and if succed ,show a success component and redirect to order details.
                        msg = "Activity result:" + response.getData().toString();
                    else
                        msg = "Result:" + response.toString();
                else
                    msg = "Result: no response";
                Log.d("befit_log", msg);
                Toast.makeText(getContext(), "Payment Succeed", Toast.LENGTH_SHORT).show();
                ArrayList<String> products_id = new ArrayList<>();
                ArrayList<String> products_qty = new ArrayList<>();

                userCartModelArrayList.forEach(userCartModel -> {
                    products_id.add(userCartModel.getCartProductId());
                    products_qty.add(userCartModel.getCartQuantity());
                });

                AddInvoiceItemService.Send(orderId, amount, products_id, products_qty, new GeneralCallBack() {
                    @Override
                    public void onSuccess(JsonObject response) {
                        if (getActivity() == null) return;

                        getActivity().runOnUiThread(() -> {
                            if (response.has("status") && response.get("status").getAsString().equals("error")) {
                                Log.e("befit_logs", "Invoice Item Add Error :: " + response.get("error").getAsString());
                                Toast.makeText(getContext(), "Invoice Item Add Error", Toast.LENGTH_SHORT).show();
                            } else if (response.has("status") && response.get("status").getAsString().equals("success")) {
                                Log.i("befit_logs", "Invoice Item Added :: " + response.toString());
                                Bundle bundle = new Bundle();
                                bundle.putString("order_id", response.get("order_id").getAsString());
                                bundle.putString("date", response.get("date").getAsString());
                                bundle.putString("products", response.get("products").toString());
                                user_order_details_fragment fragment = new user_order_details_fragment();
                                fragment.setArguments(bundle);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.user_home_interface_fragment_container_view, fragment).commit();

                            }
                        });
                    }

                    @Override
                    public void onFailure(String errorMessage) {

                    }
                });


            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getContext(), "Payment Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}