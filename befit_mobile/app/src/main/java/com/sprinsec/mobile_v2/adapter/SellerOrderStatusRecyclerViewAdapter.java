package com.sprinsec.mobile_v2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.model.SellerOrderStatusModel;

import java.util.List;

public class SellerOrderStatusRecyclerViewAdapter extends RecyclerView.Adapter<SellerOrderStatusRecyclerViewAdapter.MyViewHolder> {
    private List<SellerOrderStatusModel> sellerOrderStatusProductList;

    public SellerOrderStatusRecyclerViewAdapter(List<SellerOrderStatusModel> sellerOrderStatusProductList) {
        this.sellerOrderStatusProductList = sellerOrderStatusProductList;
    }

    @NonNull
    @Override
    public SellerOrderStatusRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_seller_order_status_item, parent, false);
        return new SellerOrderStatusRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellerOrderStatusRecyclerViewAdapter.MyViewHolder holder, int position) {
        SellerOrderStatusModel item = this.sellerOrderStatusProductList.get(position);
        holder.order_id.setText(item.getOrder_id());
        holder.order_status.setText(item.getOrder_status());
        holder.order_date.setText(item.getOrder_date());
        holder.order_total_price.setText(item.getOrder_total_price());
        holder.products.setText(item.getProducts());
    }

    @Override
    public int getItemCount() {
        return sellerOrderStatusProductList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView order_id, order_status, order_date, order_total_price, products;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            order_id = itemView.findViewById(R.id.single_seller_order_status_item_order_id);
            order_status = itemView.findViewById(R.id.single_seller_order_status_item_order_status_button);
            order_date = itemView.findViewById(R.id.single_seller_order_status_item_date);
            order_total_price = itemView.findViewById(R.id.single_seller_order_status_item_total);
            products = itemView.findViewById(R.id.single_seller_order_status_item_products);
        }
    }
}
