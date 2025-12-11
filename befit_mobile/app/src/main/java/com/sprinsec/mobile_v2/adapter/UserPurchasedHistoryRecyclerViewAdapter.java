package com.sprinsec.mobile_v2.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.model.GetPurchasedHistoryModel;
import com.sprinsec.mobile_v2.ui.fragments.user.give_feedback_fragment;
import com.sprinsec.mobile_v2.ui.fragments.user.user_order_details_fragment;

import java.util.List;

public class UserPurchasedHistoryRecyclerViewAdapter extends RecyclerView.Adapter<UserPurchasedHistoryRecyclerViewAdapter.MyViewHolder> {
    private List<GetPurchasedHistoryModel> purchasedHistoryList;
    private FragmentTransaction fragmentTransaction;

    public UserPurchasedHistoryRecyclerViewAdapter(List<GetPurchasedHistoryModel> purchasedHistoryList, FragmentTransaction fragmentTransaction) {
        this.purchasedHistoryList = purchasedHistoryList;
        this.fragmentTransaction = fragmentTransaction;
    }

    @NonNull
    @Override
    public UserPurchasedHistoryRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_purchased_history_item, parent, false);
        return new UserPurchasedHistoryRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPurchasedHistoryRecyclerViewAdapter.MyViewHolder holder, int position) {
        GetPurchasedHistoryModel item = this.purchasedHistoryList.get(position);
        holder.orderId.setText(item.getOrderId());
        holder.orderDate.setText(item.getOrderDate());
        holder.orderStatus.setText(item.getOrderStatus());
        holder.productName.setText(item.getProductTitle());
        holder.productId.setText(item.getProductId());
        holder.productPrice.setText(item.getProductPrice());
        holder.orderTotal.setText(item.getOrderTotal());
        Glide.with(holder.itemView.getContext())
                .load(item.getProductImage()) // Ensure URL is valid
                .error(R.drawable.default_product_image2)
                .placeholder(R.drawable.default_product_image2)
                .into(holder.productImage);

        if(item.getOrderStatus().equals("delivery success")){
            holder.orderStatus.setText("Delivered");
            holder.single_user_purchased_history_item_rating_button.setVisibility(View.VISIBLE);
        }else{
            holder.single_user_purchased_history_item_rating_button.setVisibility(View.GONE);
        }

        holder.single_user_purchased_history_item_rating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("order_id", item.getOrderId());
                bundle.putString("product_id", item.getProductId());
                bundle.putString("product_name", item.getProductTitle());
                bundle.putString("product_price", item.getProductPrice());
                bundle.putString("product_image", item.getProductImage());
                give_feedback_fragment fragment = new give_feedback_fragment();
                fragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.user_home_interface_fragment_container_view, fragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return purchasedHistoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView orderId, orderDate, orderStatus, productName, productPrice, orderTotal, productId;
        ImageView productImage;
        Button single_user_purchased_history_item_rating_button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.single_user_purchased_history_item_order_id);
            productId = itemView.findViewById(R.id.single_user_purchased_history_item_product_id);
            orderStatus = itemView.findViewById(R.id.single_user_purchased_history_item_order_status);
            productName = itemView.findViewById(R.id.single_user_purchased_history_item_product_name);
            productPrice = itemView.findViewById(R.id.single_user_purchased_history_item_product_price);
            orderTotal = itemView.findViewById(R.id.single_user_purchased_history_item_order_total);
            productImage = itemView.findViewById(R.id.single_user_purchased_history_item_product_image);
            orderDate = itemView.findViewById(R.id.single_user_purchased_history_item_order_date);
            single_user_purchased_history_item_rating_button = itemView.findViewById(R.id.single_user_purchased_history_item_rating_button);
        }
    }
}
