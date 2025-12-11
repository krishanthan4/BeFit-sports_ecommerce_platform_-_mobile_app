package com.sprinsec.mobile_v2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.model.UserHomeSliderModel;

import java.util.List;

public class UserProfileSliderViewPager2Adapter extends RecyclerView.Adapter<UserProfileSliderViewPager2Adapter.MyViewHolder> {
    private List<UserHomeSliderModel> UserHomeSliderModel;

    public UserProfileSliderViewPager2Adapter(List<UserHomeSliderModel> userProfileSliderModel) {
        this.UserHomeSliderModel = userProfileSliderModel;
    }

    @NonNull
    @Override
    public UserProfileSliderViewPager2Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single__user__profile_slider_item, parent, false);
        return new UserProfileSliderViewPager2Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProfileSliderViewPager2Adapter.MyViewHolder holder, int position) {
        UserHomeSliderModel item = this.UserHomeSliderModel.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop());
        Glide.with(holder.imageView.getContext())
                .load(item.getImageUrl())
                .apply(requestOptions)
                .error(R.drawable.black_screen)
                .placeholder(R.drawable.black_screen)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return UserHomeSliderModel.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.single_user_profile_slider_item_image);
        }
    }
}
