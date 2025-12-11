package com.sprinsec.mobile_v2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.model.UserHomeCategoryModel;

import java.util.List;

public class User_home_category_filter_recycler_view extends RecyclerView.Adapter<User_home_category_filter_recycler_view.MyViewHolder> {
    private List<UserHomeCategoryModel> userHomeCategoryModelList;

    public User_home_category_filter_recycler_view(List<UserHomeCategoryModel> userHomeCategoryModelList) {
        this.userHomeCategoryModelList = userHomeCategoryModelList;
    }

    @NonNull
    @Override
    public User_home_category_filter_recycler_view.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_home_category_filter_item, parent, false);
        return new User_home_category_filter_recycler_view.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull User_home_category_filter_recycler_view.MyViewHolder holder, int position) {
        UserHomeCategoryModel category = userHomeCategoryModelList.get(position);
        holder.categoryName.setText(category.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return userHomeCategoryModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.single_user_home_category_filter_category_name);
        }
    }
}
