package com.sprinsec.mobile_v2.adapter;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.api.AddToCartService;
import com.sprinsec.mobile_v2.data.api.SetToWatchlistService;
import com.sprinsec.mobile_v2.data.model.UserCartModel;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.ui.user.user_single_product_view_activity;
import com.sprinsec.mobile_v2.util.repository.CartRepository;

import java.util.List;

public class UserCartProductRecyclerViewAdapter extends RecyclerView.Adapter<UserCartProductRecyclerViewAdapter.MyViewHolder> {
    ImageView fragment_user_watchlist_delete_button;
    private List<UserCartModel> userCartModelList;
    private Float SubTotal = 0.0f;
    private Float TotalShippingFee = 0.0f;
    private CartRepository cartRepository;

    public UserCartProductRecyclerViewAdapter(List<UserCartModel> userCartModelList, ImageView fragment_user_watchlist_delete_button, CartRepository cartRepository) {
        this.userCartModelList = userCartModelList;
        this.fragment_user_watchlist_delete_button = fragment_user_watchlist_delete_button;
        this.cartRepository = cartRepository;
    }

    @NonNull
    @Override
    public UserCartProductRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single__user__cart_product_item, parent, false);
        return new UserCartProductRecyclerViewAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull UserCartProductRecyclerViewAdapter.MyViewHolder holder, int position) {
        UserCartModel product = userCartModelList.get(position);
        holder.cartProductId.setText(product.getCartProductId());
        holder.cartProductName.setText(product.getCartProductName());
        holder.cartProductQuantity.setText(product.getCartProductQuantity());
        holder.cartQuantity.setText(product.getCartQuantity());
        holder.cartProductDescription.setText(product.getCartProductDescription());
        holder.cartProductPrice.setText(product.getCartProductPrice());
        holder.cartAvgStars.setText(product.getCartProductRatings());
        if (product.getCartProductDeliveryFee().isEmpty()) {
            holder.deliveryFee.setText(product.getCartProductDeliveryFee());
        } else {
            holder.deliveryFee.setText("Rs. 0");
        }
        holder.shippingFee.setText(product.getCartProductShippingFee());
        SubTotal += Float.parseFloat(product.getCartProductPrice().substring(3)) * Float.parseFloat(product.getCartQuantity());
        TextView fragment_user_cart_subtotal = ((Activity) holder.itemView.getContext()).findViewById(R.id.fragment_user_cart_subtotal_set_text);
        fragment_user_cart_subtotal.setText("Rs. " + SubTotal);
        TotalShippingFee += Float.parseFloat(product.getCartProductShippingFee()) + Float.parseFloat(product.getCartProductDeliveryFee());
        TextView fragment_user_cart_shipping_fee = ((Activity) holder.itemView.getContext()).findViewById(R.id.fragment_user_cart_shipping_fee_set_text);
        fragment_user_cart_shipping_fee.setText("Rs. " + TotalShippingFee);
//         Glide image handling
        if (product.getCartProductImage() != null && !product.getCartProductImage().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(product.getCartProductImage())
                    .error(R.drawable.default_product_image)
                    .placeholder(R.drawable.default_product_image)
                    .into(holder.cartProductImage);
        } else {
            holder.cartProductImage.setImageResource(R.drawable.default_product_image);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), user_single_product_view_activity.class);
                intent.putExtra("product_id", product.getCartProductId());
                intent.putExtra("product_name", product.getCartProductName());
                intent.putExtra("product_price", product.getCartProductPrice().substring(3));
                intent.putExtra("product_image", product.getCartProductImage());
                intent.putExtra("product_rating", product.getCartProductRatings());
                v.getContext().startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            // Get current background
            Drawable background = v.getBackground();

            // Check if the background is the selected drawable
            if (background != null && background.getConstantState() != null &&
                    background.getConstantState().equals(ContextCompat.getDrawable(v.getContext(), R.drawable.watchlist_selected_background_outline).getConstantState())) {

                v.setBackgroundResource(R.drawable.watchlist_deselected_background_outline);
            } else {
                v.setBackgroundResource(R.drawable.watchlist_selected_background_outline);

                // Set delete button listener inside the correct condition
                fragment_user_watchlist_delete_button.setOnClickListener(deleteButtonView -> {
                    AddToCartService.Send(product.getCartProductId(), "1", new GeneralCallBack() {
                        @Override
                        public void onSuccess(JsonObject response) {
                            ((Activity) deleteButtonView.getContext()).runOnUiThread(() -> {
                                Log.i("befit_logs", "Watchlist Updated: " + response);
                                if (response.has("status") && response.get("message").getAsString().equals("removed")) {
                                    holder.itemView.setVisibility(View.GONE);
                                    userCartModelList.remove(holder.getAdapterPosition());
                                    notifyItemRemoved(holder.getAdapterPosition());
                                    SubTotal -= Float.parseFloat(product.getCartProductPrice().substring(3)) * Float.parseFloat(product.getCartQuantity());
                                    TextView fragment_user_cart_subtotal = ((Activity) deleteButtonView.getContext()).findViewById(R.id.fragment_user_cart_subtotal_set_text);
                                    fragment_user_cart_subtotal.setText("Rs. " + SubTotal);

                                    cartRepository = new CartRepository(v.getContext());
                                    cartRepository.deleteCartItem(product.getCartProductId());
//                                    TotalShippingFee -= Float.parseFloat(product.getCartProductShippingFee()) + Float.parseFloat(product.getCartProductDeliveryFee());
//                                    TextView fragment_user_cart_shipping_fee = ((Activity) holder.itemView.getContext()).findViewById(R.id.fragment_user_cart_shipping_fee_set_text);
//                                    fragment_user_cart_shipping_fee.setText("Rs. " + TotalShippingFee);
                                }
                            });
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            ((Activity) deleteButtonView.getContext()).runOnUiThread(() ->
                                    Log.e("befit_logs", "Error: " + errorMessage));
                        }
                    });
                });
            }

            return true; // Ensure the long click is properly handled
        });

    }

    @Override
    public int getItemCount() {
        return userCartModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cartProductId, cartProductName, deliveryFee, shippingFee, cartProductDescription, cartProductPrice, cartProductQuantity, cartQuantity, cartAvgStars;
        ImageView cartProductImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cartProductId = itemView.findViewById(R.id.single_user_cart_product_item_cart_product_id);
            cartProductName = itemView.findViewById(R.id.single_user_cart_product_item_cart_product_title);
            cartProductImage = itemView.findViewById(R.id.single_user_cart_product_item_cart_product_image);
            cartQuantity = itemView.findViewById(R.id.fragment_user_single_product_view_quantity_text);
            cartProductDescription = itemView.findViewById(R.id.single_user_cart_product_item_cart_product_description);
            cartProductPrice = itemView.findViewById(R.id.single_user_cart_product_item_cart_product_price);
            cartProductQuantity = itemView.findViewById(R.id.single_user_cart_product_item_cart_product_qty);
            cartAvgStars = itemView.findViewById(R.id.single_user_cart_product_item_cart_product_rating_text);
            deliveryFee = itemView.findViewById(R.id.single_user_cart_product_item_cart_product_delivery_fee);
            shippingFee = itemView.findViewById(R.id.single_user_cart_product_item_cart_product_shipping_fee);
        }
    }
}