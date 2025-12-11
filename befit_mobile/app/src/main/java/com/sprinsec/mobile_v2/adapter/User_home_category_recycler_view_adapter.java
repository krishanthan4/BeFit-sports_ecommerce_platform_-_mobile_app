package com.sprinsec.mobile_v2.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.model.UserHomeCategoryModel;
import com.sprinsec.mobile_v2.ui.fragments.user.user_search_fragment;

import java.util.List;

public class User_home_category_recycler_view_adapter extends RecyclerView.Adapter<User_home_category_recycler_view_adapter.MyViewHolder> {
    private List<UserHomeCategoryModel> userHomeCategoryModelList;

    public User_home_category_recycler_view_adapter(List<UserHomeCategoryModel> userHomeCategoryModelList) {
        this.userHomeCategoryModelList = userHomeCategoryModelList;
    }

    @NonNull
    @Override
    public User_home_category_recycler_view_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single__user__home_category_item, parent, false);
        return new User_home_category_recycler_view_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull User_home_category_recycler_view_adapter.MyViewHolder holder, int position) {
        UserHomeCategoryModel category = userHomeCategoryModelList.get(position);
        holder.categoryId.setText(category.getCategoryId());
        holder.categoryName.setText(category.getCategoryName());
        Glide.with(holder.itemView.getContext())
                .load(category.getCategoryImage()) // Ensure URL is valid
                .error(R.drawable.default_product_image)
                .placeholder(R.drawable.default_product_image)
                .into(holder.categoryImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("search_query", holder.categoryName.getText().toString());
                user_search_fragment fragment = new user_search_fragment();
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.user_home_interface_fragment_container_view, fragment).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return userHomeCategoryModelList.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName, categoryId;
        ImageView categoryImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryId = itemView.findViewById(R.id.single_user_home_category_item_category_id);
            categoryImage = itemView.findViewById(R.id.single_user_home_category_item_category_image);
            categoryName = itemView.findViewById(R.id.single_user_home_category_item_category_name);
        }
    }
}
