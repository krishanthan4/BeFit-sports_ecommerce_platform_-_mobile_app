package com.sprinsec.mobile_v2.adapter.admin;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.model.AdminDashboardSellerModel;

import java.util.ArrayList;

public class admin_dashboard_get_sellers_recycler_view_adapter extends RecyclerView.Adapter<admin_dashboard_get_sellers_recycler_view_adapter.MyViewHolder> {
    private ArrayList<AdminDashboardSellerModel> adminDashboardSellerModelArrayList;

    public admin_dashboard_get_sellers_recycler_view_adapter(ArrayList<AdminDashboardSellerModel> adminDashboardSellerModelArrayList) {
        this.adminDashboardSellerModelArrayList = adminDashboardSellerModelArrayList;
    }

    @NonNull
    @Override
    public admin_dashboard_get_sellers_recycler_view_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_admin_dashboard_seller_management_item, parent, false);
        return new admin_dashboard_get_sellers_recycler_view_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull admin_dashboard_get_sellers_recycler_view_adapter.MyViewHolder holder, int position) {
        AdminDashboardSellerModel seller = adminDashboardSellerModelArrayList.get(position);
        holder.sellerName.setText(seller.getSellerName());
        holder.sellerEmail.setText(seller.getSellerEmail());
        holder.sellerPhone.setText(seller.getSellerPhone());
        holder.sellerTotalProducts.setText(seller.getSellerTotalProducts());
        holder.registeredDate.setText(seller.getRegisteredDate());
        holder.single_admin_dashboard_seller_management_status.setText(seller.getSellerStatus());
        if (seller.getSellerStatus().equals("ACTIVE")) {
            holder.single_admin_dashboard_seller_management_status.setChipBackgroundColorResource(R.color.status_active_color);
        } else {
            holder.single_admin_dashboard_seller_management_status.setChipBackgroundColorResource(R.color.status_blocked_color);
        }
        Glide.with(holder.itemView.getContext())
                .load(seller.getSellerImage()) // Ensure URL is valid
                .error(R.drawable.two_people)
                .placeholder(R.drawable.two_people)
                .into(holder.sellerImage);
    }

    @Override
    public int getItemCount() {
        return adminDashboardSellerModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sellerName, sellerEmail, sellerPhone, registeredDate, sellerTotalProducts;
        ImageView sellerImage;
        Chip single_admin_dashboard_seller_management_status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sellerName = itemView.findViewById(R.id.single_admin_dashboard_seller_management_name);
            sellerEmail = itemView.findViewById(R.id.single_admin_dashboard_seller_management_email);
            sellerPhone = itemView.findViewById(R.id.single_admin_dashboard_seller_management_phone);
            sellerTotalProducts = itemView.findViewById(R.id.single_admin_dashboard_seller_management_total_products);
            registeredDate = itemView.findViewById(R.id.single_admin_dashboard_seller_management_registered_date);
            sellerImage = itemView.findViewById(R.id.single_admin_dashboard_seller_management_profile_image);
            single_admin_dashboard_seller_management_status = itemView.findViewById(R.id.single_admin_dashboard_seller_management_status);

        }
    }
}
