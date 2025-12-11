package com.sprinsec.mobile_v2.ui.fragments.user;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.api.OrderDetailsRecyclerViewAdapter;
import com.sprinsec.mobile_v2.data.model.UserOrderDetailsModel;
import com.sprinsec.mobile_v2.ui.user.user_home_interface;
import com.sprinsec.mobile_v2.util.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class user_order_details_fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_order_details, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            RecyclerView recyclerView = view.findViewById(R.id.fragment_user_order_details_product_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            ArrayList<UserOrderDetailsModel> userOrderDetailsList = new ArrayList<>();

            String orderId = bundle.getString("order_id");
            String date = bundle.getString("date");
            TextView fragment_user_order_details_order_date = view.findViewById(R.id.fragment_user_order_details_order_date);
            fragment_user_order_details_order_date.setText("Order placed - " + date);

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.sprinsec.mobile_v2.user_prefs",MODE_PRIVATE);

            String line1 = sharedPreferences.getString("line1", "");
            String line2 = sharedPreferences.getString("line2", "");
            String city = sharedPreferences.getString("city", "");
            String country = sharedPreferences.getString("country", "");

            TextView fragment_user_order_details_delivery_address = view.findViewById(R.id.fragment_user_order_details_delivery_address);

            fragment_user_order_details_delivery_address.setText(line1 + ",\n " + line2 + ",\n " + city + ",\n " + country);

            try {
                JSONArray productsArray = new JSONArray(bundle.getString("products"));
                for (int i = 0; i < productsArray.length(); i++) {
                    JSONObject product = productsArray.getJSONObject(i);
                    String productName = product.getString("title");
                    String productPrice = "Rs. " + product.getString("price");
                    String productDescription = product.getString("description");
                    String productImage = String.valueOf(Config.BACKEND_URL + product.getString("image").substring(3));
                    Log.i("productImage", productImage);
                    userOrderDetailsList.add(new UserOrderDetailsModel(productName, productPrice, productDescription, productImage));
                }
                recyclerView.setAdapter(new OrderDetailsRecyclerViewAdapter(userOrderDetailsList));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            Intent intent = new Intent(getContext(), user_home_interface.class);
            startActivity(intent);
        }


        TextView fragment_user_order_details_buy_more = view.findViewById(R.id.fragment_user_order_details_buy_more);
        fragment_user_order_details_buy_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), user_home_interface.class);
                startActivity(intent);
            }
        });
        return view;
    }
}