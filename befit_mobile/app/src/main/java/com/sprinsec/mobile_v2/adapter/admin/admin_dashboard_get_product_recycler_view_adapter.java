package com.sprinsec.mobile_v2.adapter.admin;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.model.AdminDashboardProductModel;

import java.util.ArrayList;

public class admin_dashboard_get_product_recycler_view_adapter extends RecyclerView.Adapter<admin_dashboard_get_product_recycler_view_adapter.MyViewHolder> {
    private ArrayList<AdminDashboardProductModel> adminDashboardProductModelArrayList;

    public admin_dashboard_get_product_recycler_view_adapter(ArrayList<AdminDashboardProductModel> adminDashboardProductModelArrayList) {
        this.adminDashboardProductModelArrayList = adminDashboardProductModelArrayList;
    }

    @NonNull
    @Override
    public admin_dashboard_get_product_recycler_view_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_admin_dashboard_product_management_item, parent, false);
        return new admin_dashboard_get_product_recycler_view_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull admin_dashboard_get_product_recycler_view_adapter.MyViewHolder holder, int position) {
        AdminDashboardProductModel product = adminDashboardProductModelArrayList.get(position);
        holder.productId.setText(product.getProductId());
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(product.getProductPrice());
        holder.productQuantity.setText(product.getProductQuantity());
        holder.registeredDate.setText(product.getProductregistered_date());
        holder.single_admin_dashboard_product_management_status.setText(product.getProductStatus());
        if (product.getProductStatus().equals("ACTIVE")) {
            holder.single_admin_dashboard_product_management_status.setChipBackgroundColorResource(R.color.status_active_color);
        } else {
            holder.single_admin_dashboard_product_management_status.setChipBackgroundColorResource(R.color.status_blocked_color);
        }
        Glide.with(holder.itemView.getContext())
                .load(product.getProductImage()) // Ensure URL is valid
                .error(R.drawable.default_product_image2)
                .placeholder(R.drawable.default_product_image2)
                .into(holder.productImage);

    }

    @Override
    public int getItemCount() {
        return adminDashboardProductModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productId, productName, productPrice, productQuantity, registeredDate;
        ImageView productImage;
        Chip single_admin_dashboard_product_management_status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productId = itemView.findViewById(R.id.single_admin_dashboard_product_management_id);
            productName = itemView.findViewById(R.id.single_admin_dashboard_product_management_title);
            productPrice = itemView.findViewById(R.id.single_admin_dashboard_product_management_price);
            productQuantity = itemView.findViewById(R.id.single_admin_dashboard_product_management_quantity);
            registeredDate = itemView.findViewById(R.id.single_admin_dashboard_product_management_registered_date);
            productImage = itemView.findViewById(R.id.single_admin_dashboard_product_management_image);
            single_admin_dashboard_product_management_status = itemView.findViewById(R.id.single_admin_dashboard_product_management_status);
        }
    }
}
