package com.sprinsec.mobile_v2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.model.SingleProductViewSliderModel;

import java.util.List;

public class UserSingleProductViewPager2Adapter extends RecyclerView.Adapter<UserSingleProductViewPager2Adapter.MyViewHolder> {
    private List<SingleProductViewSliderModel> UserProfileSliderList;

    public UserSingleProductViewPager2Adapter(List<SingleProductViewSliderModel> userProfileSliderModel) {
        this.UserProfileSliderList = userProfileSliderModel;
    }

    @NonNull
    @Override
    public UserSingleProductViewPager2Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_single_product_view_slider_item, parent, false);
        return new UserSingleProductViewPager2Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserSingleProductViewPager2Adapter.MyViewHolder holder, int position) {
        SingleProductViewSliderModel item = this.UserProfileSliderList.get(position);
        Glide.with(holder.imageView.getContext()).load(item.getImageUrl()).placeholder(R.drawable.default_product_image).error(R.drawable.default_product_image).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return UserProfileSliderList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.single_user_single_product_view_slider_item_image);
        }
    }
}
