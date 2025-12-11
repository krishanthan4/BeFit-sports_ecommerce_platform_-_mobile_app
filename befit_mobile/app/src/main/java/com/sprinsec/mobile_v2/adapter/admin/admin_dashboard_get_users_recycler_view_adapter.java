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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.adapter.User_home_watchlist_recycler_view_adapter;
import com.sprinsec.mobile_v2.data.model.AdminDashboardUserModel;
import com.sprinsec.mobile_v2.interfaces.RecyclerViewInterface;

import java.util.ArrayList;

public class admin_dashboard_get_users_recycler_view_adapter extends RecyclerView.Adapter<admin_dashboard_get_users_recycler_view_adapter.MyViewHolder> {
    private ArrayList<AdminDashboardUserModel> adminDashboardUserModelArrayList;

    public admin_dashboard_get_users_recycler_view_adapter(ArrayList<AdminDashboardUserModel> adminDashboardUserModelArrayList) {
        this.adminDashboardUserModelArrayList = adminDashboardUserModelArrayList;
    }

    @NonNull
    @Override
    public admin_dashboard_get_users_recycler_view_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single__admin__dashboard_user_management_item, parent, false);
        return new admin_dashboard_get_users_recycler_view_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull admin_dashboard_get_users_recycler_view_adapter.MyViewHolder holder, int position) {
        AdminDashboardUserModel user = adminDashboardUserModelArrayList.get(position);
        holder.userName.setText(user.getUserName());
        holder.userEmail.setText(user.getUserEmail());
        holder.userPhone.setText(user.getUserPhone());
        holder.registeredDate.setText(user.getRegisteredDate());
        holder.single_admin_dashboard_user_management_status.setText(user.getUserStatus());
        if (user.getUserStatus().equals("ACTIVE")) {
            holder.single_admin_dashboard_user_management_status.setChipBackgroundColorResource(R.color.status_active_color);
        } else {
            holder.single_admin_dashboard_user_management_status.setChipBackgroundColorResource(R.color.status_blocked_color);
        }

        Glide.with(holder.itemView.getContext())
                .load(user.getUserImage()) // Ensure URL is valid
                .error(R.drawable.two_people)
                .placeholder(R.drawable.two_people)
                .into(holder.userImage);

    }

    @Override
    public int getItemCount() {
        return adminDashboardUserModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView userName, userEmail, userPhone, registeredDate;
        Chip single_admin_dashboard_user_management_status;
        ImageView userImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.single_admin_dashboard_user_management_name);
            userEmail = itemView.findViewById(R.id.single_admin_dashboard_user_management_email);
            userPhone = itemView.findViewById(R.id.single_admin_dashboard_user_management_phone);
            registeredDate = itemView.findViewById(R.id.single_admin_dashboard_user_management_registered_date);
            userImage = itemView.findViewById(R.id.single_admin_dashboard_user_management_profile_image);
            single_admin_dashboard_user_management_status = itemView.findViewById(R.id.single_admin_dashboard_user_management_status);

        }
    }
}
