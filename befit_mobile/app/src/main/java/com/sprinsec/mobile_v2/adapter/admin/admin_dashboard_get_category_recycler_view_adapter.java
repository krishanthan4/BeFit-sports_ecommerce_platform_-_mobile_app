package com.sprinsec.mobile_v2.adapter.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.model.UserHomeCategoryModel;
import java.util.ArrayList;

public class admin_dashboard_get_category_recycler_view_adapter extends RecyclerView.Adapter<admin_dashboard_get_category_recycler_view_adapter.MyViewHolder> {
    private ArrayList<UserHomeCategoryModel> adminDashboardCategoryModelArrayList;

    public admin_dashboard_get_category_recycler_view_adapter(ArrayList<UserHomeCategoryModel> adminDashboardCategoryModelArrayList) {
        this.adminDashboardCategoryModelArrayList = adminDashboardCategoryModelArrayList;
    }

    @NonNull
    @Override
    public admin_dashboard_get_category_recycler_view_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_admin_dashboard_category_management_item, parent, false);
        return new admin_dashboard_get_category_recycler_view_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull admin_dashboard_get_category_recycler_view_adapter.MyViewHolder holder, int position) {
        UserHomeCategoryModel category = adminDashboardCategoryModelArrayList.get(position);
        holder.categoryId.setText(category.getCategoryId());
        holder.categoryName.setText(category.getCategoryName());
        Glide.with(holder.itemView.getContext())
                .load(category.getCategoryImage()) // Ensure URL is valid
                .error(R.drawable.default_category_image)
                .placeholder(R.drawable.default_category_image)
                .into(holder.categoryImage);

    }

    @Override
    public int getItemCount() {
        return adminDashboardCategoryModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView categoryId, categoryName;
        ImageView categoryImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryId = itemView.findViewById(R.id.single_admin_dashboard_category_management_id);
            categoryName = itemView.findViewById(R.id.single_admin_dashboard_category_management_title);
            categoryImage = itemView.findViewById(R.id.single_admin_dashboard_category_management_image);

        }
    }
}
