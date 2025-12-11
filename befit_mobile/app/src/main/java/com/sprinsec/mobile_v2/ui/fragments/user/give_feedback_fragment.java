package com.sprinsec.mobile_v2.ui.fragments.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.api.GiveFeedbackForProductService;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.ui.common.auth.SignInActivity;
import com.sprinsec.mobile_v2.ui.user.user_home_interface;
import com.sprinsec.mobile_v2.util.Config;

public class give_feedback_fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_give_feedback, container, false);
        RatingBar fragment_give_feedback_rating_bar = view.findViewById(R.id.fragment_give_feedback_rating_bar);
        EditText fragment_give_feedback_review_text = view.findViewById(R.id.fragment_give_feedback_review_text);
        Button fragment_give_feedback_submit_review_button = view.findViewById(R.id.fragment_give_feedback_submit_review_button);
        ImageView fragment_give_feedback_product_image = view.findViewById(R.id.fragment_give_feedback_product_image);
        TextView fragment_give_feedback_product_name = view.findViewById(R.id.fragment_give_feedback_product_name);
        TextView fragment_give_feedback_product_price = view.findViewById(R.id.fragment_give_feedback_product_price);
        Bundle bundle = getArguments();
        if (bundle == null) {
            Intent intent = new Intent(getActivity(), user_home_interface.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            String productId = bundle.getString("product_id");
            String productName = bundle.getString("product_name");
            String productImage = bundle.getString("product_image");
            String productPrice = bundle.getString("product_price");

            fragment_give_feedback_product_price.setText(productPrice);
            fragment_give_feedback_product_name.setText(productName);
            Glide.with(this)
                    .load(productImage)
                    .placeholder(R.drawable.default_product_image2)
                    .error(R.drawable.default_product_image2)
                    .into(fragment_give_feedback_product_image);


            fragment_give_feedback_submit_review_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Float rating = fragment_give_feedback_rating_bar.getRating();
                    if (rating.isNaN()) {
                        Toast.makeText(getContext(), "Ratings cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (fragment_give_feedback_review_text.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "Review cannot be empty", Toast.LENGTH_SHORT).show();
                    } else {
                        GiveFeedbackForProductService.Send(rating, productId, fragment_give_feedback_review_text.getText().toString(), new GeneralCallBack() {
                            @Override
                            public void onSuccess(JsonObject response) {
                                if (getActivity() == null) return;

                                getActivity().runOnUiThread(() -> {
                                    try {
                                        if (response.has("status") && "success".equals(response.get("status").getAsString())) {
                                            Toast.makeText(getContext(), "Feedback submitted successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getContext(), user_home_interface.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(getContext(), "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        Log.e("befit_logs", "Dashboard overview data processing error: ", e);
                                    }
                                });
                            }

                            @Override
                            public void onFailure(String errorMessage) {
                                if (getActivity() == null) return;
                                getActivity().runOnUiThread(() -> {
                                    Log.e("befit_logs", "Error: " + errorMessage);
                                    Toast.makeText(getContext(), "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                                });
                            }
                        });
                    }
                }
            });
        }

        view.findViewById(R.id.fragment_give_feedback_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), user_home_interface.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }
}