package com.sprinsec.mobile_v2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.model.UserHomeProductModel;
import com.sprinsec.mobile_v2.interfaces.RecyclerViewInterface;

import java.util.List;

public class User_home_product_recycler_view_adapter extends RecyclerView.Adapter<User_home_product_recycler_view_adapter.MyViewHolder> {
    private final List<UserHomeProductModel> userHomeProductModelList;
    private final RecyclerViewInterface recyclerViewInterface;

    public User_home_product_recycler_view_adapter(List<UserHomeProductModel> userHomeProductModelList, RecyclerViewInterface recyclerViewInterface) {
        this.userHomeProductModelList = userHomeProductModelList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public User_home_product_recycler_view_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single__user__home_product_item, parent, false);
        return new User_home_product_recycler_view_adapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull User_home_product_recycler_view_adapter.MyViewHolder holder, int position) {
        UserHomeProductModel product = userHomeProductModelList.get(position);
        holder.productId.setText(product.getProductId());
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(product.getProductPrice()); // Ensure it's formatted
        holder.rating.setText(product.getRating()); // Make sure it's a string

        Glide.with(holder.itemView.getContext())
                .load(product.getProductImage()) // Ensure URL is valid
                .error(R.drawable.default_product_image)
                .placeholder(R.drawable.default_product_image)
                .into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return userHomeProductModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, rating, productId;
        ImageView productImage;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            productId = itemView.findViewById(R.id.single_user_home_product_item_product_id);
            productName = itemView.findViewById(R.id.single_user_home_product_item_product_name);
            productPrice = itemView.findViewById(R.id.single_user_home_product_item_price);
            rating = itemView.findViewById(R.id.single_user_home_product_item_rating);
            productImage = itemView.findViewById(R.id.single_user_home_product_item_product_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClicked(position);
                        }

                    }
                }
            });
        }
    }
}
