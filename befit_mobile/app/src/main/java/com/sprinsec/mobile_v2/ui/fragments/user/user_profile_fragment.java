package com.sprinsec.mobile_v2.ui.fragments.user;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.adapter.UserProfileSliderViewPager2Adapter;
import com.sprinsec.mobile_v2.data.model.UserHomeSliderModel;
import com.sprinsec.mobile_v2.data.model.UserProfileSliderModel;
import com.sprinsec.mobile_v2.ui.fragments.user.seller.seller_add_products_fragment;
import com.sprinsec.mobile_v2.ui.fragments.user.seller.seller_my_products_fragment;
import com.sprinsec.mobile_v2.ui.fragments.user.seller.seller_order_status_activity;
import com.sprinsec.mobile_v2.ui.fragments.user.seller.seller_selling_history_fragment;
import com.sprinsec.mobile_v2.ui.user.about_us_activity;
import com.sprinsec.mobile_v2.ui.user.user_main_settings;
import com.sprinsec.mobile_v2.util.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class user_profile_fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__user__profile, container, false);
        LinearLayout add_products_button = view.findViewById(R.id.fragment_user_profile_add_product);
        LinearLayout customer_care_button = view.findViewById(R.id.fragment_user_profile_customer_care);
        LinearLayout my_products_button = view.findViewById(R.id.fragment_user_profile_my_products);
        LinearLayout selling_history_button = view.findViewById(R.id.fragment_user_profile_selling_history);
        LinearLayout order_status_button = view.findViewById(R.id.fragment_user_profile_order_status);
        LinearLayout purchased_history_button = view.findViewById(R.id.fragment_user_profile_purchased_history);
        LinearLayout about_us_button = view.findViewById(R.id.fragment_user_profile_about_us);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.sprinsec.mobile_v2.user_prefs",MODE_PRIVATE);

        String line1 = sharedPreferences.getString("line1", "");
        String line2 = sharedPreferences.getString("line2", "");
        String city = sharedPreferences.getString("city", "");
        String country = sharedPreferences.getString("country", "").trim();
        TextView userAddressTextView = view.findViewById(R.id.fragment_user_profile_user_address);
        String profile_img = sharedPreferences.getString("profile_img", "");
        ImageView fragment_user_profile_profile_image = view.findViewById(R.id.fragment_user_profile_profile_image);
        ImageView fragment_user_profile_country_image = view.findViewById(R.id.fragment_user_profile_country_image);

        Log.i("shared_preference_check", "line1: " + line1 + ", line2: " + line2 + ", city: " + city + ", profile_img: " + profile_img +", country: "+country);
        String profileImageUri = sharedPreferences.getString("profile_img", "") != null ? sharedPreferences.getString("profile_img", "") : "";
        Glide.with(getContext()).load(Config.BACKEND_URL + profileImageUri).error(R.drawable.profile_icon3).placeholder(R.drawable.profile_icon3).into(fragment_user_profile_profile_image);

        if (!sharedPreferences.getString("sellerStatus", "").isEmpty()) {
            if (sharedPreferences.getString("sellerStatus", "").equals("true")) {
                view.findViewById(R.id.fragment_user_profile_seller_features_linear_layout).setVisibility(View.VISIBLE);
            } else {
                view.findViewById(R.id.fragment_user_profile_seller_features_linear_layout).setVisibility(View.GONE);
            }
        }
        if (line2.isEmpty() || city.isEmpty() || line1.isEmpty() || country.isEmpty()) {
            String fname = sharedPreferences.getString("firstName", "");
            String lname = sharedPreferences.getString("lastName", "");
            if (!fname.isEmpty() || !lname.isEmpty()) {
                Log.i("shared_preference_check", "fname: " + fname + ", lname: " + lname);
                userAddressTextView.setText("Hi ," + fname + " " + lname);
            }
        }else{
            String fullAddress = line1 + ", " + line2 + ", " + city;
            userAddressTextView.setText(fullAddress.substring(0, Math.min(fullAddress.length(), 35)) + "...");
        }


        Map<String, Integer> countryImageMap = new HashMap<>();
        countryImageMap.put("Sri Lanka", R.drawable.country_sri_lanka);
        countryImageMap.put("India", R.drawable.country_india);
        countryImageMap.put("Bangladesh", R.drawable.country_bangladesh);
        countryImageMap.put("Pakistan", R.drawable.country_pakistan);
        countryImageMap.put("Nepal", R.drawable.country_nepal);
        countryImageMap.put("Maldives", R.drawable.country_maldives);
        countryImageMap.put("Bhutan", R.drawable.country_bhutan);
        countryImageMap.put("Afghanistan", R.drawable.country_afghanistan);

        int countryImageResId = countryImageMap.getOrDefault(country, R.drawable.select_country);
        fragment_user_profile_country_image.setImageResource(countryImageResId);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        add_products_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.user_home_interface_fragment_container_view, seller_add_products_fragment.class, null).commit();
            }
        });
        customer_care_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.user_home_interface_fragment_container_view, user_contact_customer_care_fragment.class, null).commit();
            }
        });

        selling_history_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.user_home_interface_fragment_container_view, seller_selling_history_fragment.class, null).commit();
            }
        });

        my_products_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.user_home_interface_fragment_container_view, seller_my_products_fragment.class, null).commit();
            }
        });
        order_status_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), seller_order_status_activity.class);
                startActivity(intent);
            }
        });
        purchased_history_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.user_home_interface_fragment_container_view, user_purchased_history_fragment.class, null).commit();
            }
        });
        about_us_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), about_us_activity.class);
                startActivity(intent);
            }
        });

        ViewPager2 viewPager = view.findViewById(R.id.fragment_user_profile_viewPager2);
        // Sample Data
        List<UserHomeSliderModel> sliderItems = new ArrayList<>();
        sliderItems.add(new UserHomeSliderModel(Config.BACKEND_URL + "public/images/slider_images/carousel1.jpg"));
        sliderItems.add(new UserHomeSliderModel(Config.BACKEND_URL + "public/images/slider_images/carousel2.jpg"));
        sliderItems.add(new UserHomeSliderModel(Config.BACKEND_URL + "public/images/slider_images/carousel3.jpg"));
        sliderItems.add(new UserHomeSliderModel(Config.BACKEND_URL + "public/images/slider_images/carousel4.jpg"));
        sliderItems.add(new UserHomeSliderModel(Config.BACKEND_URL + "public/images/slider_images/carousel5.jpg"));
        sliderItems.add(new UserHomeSliderModel(Config.BACKEND_URL + "public/images/slider_images/carousel6.jpg"));
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == sliderItems.size() - 1) {
                    viewPager.postDelayed(() -> viewPager.setCurrentItem(0, true), 1000);
                } else {
                    viewPager.postDelayed(() -> viewPager.setCurrentItem(position + 1, true), 1000);
                }
            }
        });
        // Set Adapter  // Set Adapter
        UserProfileSliderViewPager2Adapter adapter = new UserProfileSliderViewPager2Adapter(sliderItems);
        viewPager.setAdapter(adapter);


        ImageView fragment_user_profile_settings_icon = view.findViewById(R.id.fragment_user_profile_settings_icon);
        fragment_user_profile_settings_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), user_main_settings.class);
                startActivity(intent);
            }
        });
        return view;
    }
}