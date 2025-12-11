package com.sprinsec.mobile_v2.data.api;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.model.UserOrderDetailsModel;

import java.util.List;

public class OrderDetailsRecyclerViewAdapter extends RecyclerView.Adapter<OrderDetailsRecyclerViewAdapter.MyViewHolder> {
    private List<UserOrderDetailsModel> orderDetailsModel;

    public OrderDetailsRecyclerViewAdapter(List<UserOrderDetailsModel> orderDetailsModel) {
        this.orderDetailsModel = orderDetailsModel;
    }

    @NonNull
    @Override
    public OrderDetailsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_order_details_item, parent, false);
        return new OrderDetailsRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsRecyclerViewAdapter.MyViewHolder holder, int position) {
        UserOrderDetailsModel item = this.orderDetailsModel.get(position);
        holder.productName.setText(item.getProductName());
        holder.productPrice.setText(item.getProductPrice());
        holder.productDetails.setText(item.getProductDetails());
        Glide.with(holder.productImage.getContext())
                .load(item.getProductImage())
                .error(R.drawable.default_product_image2)
                .placeholder(R.drawable.default_product_image2)
                .into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return orderDetailsModel.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productDetails;
        ImageView productImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.single_user_order_details_item_product_name);
            productPrice = itemView.findViewById(R.id.single_user_order_details_item_product_price);
            productDetails = itemView.findViewById(R.id.single_user_order_details_item_product_details);
            productImage = itemView.findViewById(R.id.single_user_order_details_item_product_image);
        }
    }
}