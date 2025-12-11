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
import com.sprinsec.mobile_v2.data.model.SellerMyProductItemModel;

import java.util.List;

public class SellerMyProductItemRecyclerViewAdapter extends RecyclerView.Adapter<SellerMyProductItemRecyclerViewAdapter.MyViewHolder> {
    List<SellerMyProductItemModel> sellerMyProductItemModelList;

    public SellerMyProductItemRecyclerViewAdapter(List<SellerMyProductItemModel> sellerMyProductItemModelList) {
        this.sellerMyProductItemModelList = sellerMyProductItemModelList;
    }

    @NonNull
    @Override
    public SellerMyProductItemRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single__seller__my_products_item, parent, false);
        return new SellerMyProductItemRecyclerViewAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SellerMyProductItemRecyclerViewAdapter.MyViewHolder holder, int position) {
        SellerMyProductItemModel item = this.sellerMyProductItemModelList.get(position);
        holder.productName.setText(item.getProductName());
        holder.productPrice.setText(item.getProductPrice());
        holder.productStatus.setText(item.getProductStatus());
        holder.productStock.setText(item.getProductStock());
        Glide.with(holder.itemView.getContext())
                .load(item.getProductImageUrl())
                .placeholder(R.drawable.default_product_image2)
                .error(R.drawable.default_product_image2)
                .into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return sellerMyProductItemModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productStock, productStatus;
        ImageView productImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.single_seller_my_products_item_product_name);
            productPrice = itemView.findViewById(R.id.single_seller_my_products_item_price);
            productStock = itemView.findViewById(R.id.single_seller_my_products_item_stock);
            productStatus = itemView.findViewById(R.id.single_seller_my_products_item_status);
            productImage = itemView.findViewById(R.id.single_seller_my_products_item_product_image);

        }
    }
}
