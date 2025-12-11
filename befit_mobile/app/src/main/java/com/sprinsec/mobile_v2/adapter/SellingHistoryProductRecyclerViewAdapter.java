package com.sprinsec.mobile_v2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.model.SellingHistoryProductModel;

import java.util.List;

public class SellingHistoryProductRecyclerViewAdapter extends RecyclerView.Adapter<SellingHistoryProductRecyclerViewAdapter.MyViewHolder> {
    private List<SellingHistoryProductModel> sellingHistoryProductList;

    public SellingHistoryProductRecyclerViewAdapter(List<SellingHistoryProductModel> sellingHistoryProductList) {
        this.sellingHistoryProductList = sellingHistoryProductList;
    }

    @NonNull
    @Override
    public SellingHistoryProductRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single__seller__selling_history_item, parent, false);
        return new SellingHistoryProductRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellingHistoryProductRecyclerViewAdapter.MyViewHolder holder, int position) {
        SellingHistoryProductModel item = this.sellingHistoryProductList.get(position);
        holder.productName.setText(item.getProductName());
        holder.productPrice.setText(item.getProductPrice());
        holder.buyerName.setText(item.getBuyerName());
        holder.boughtQuantity.setText(item.getBoughtQuantity());
        holder.selledDate.setText(item.getSelledDate());
    }

    @Override
    public int getItemCount() {
        return sellingHistoryProductList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productPrice, buyerName, boughtQuantity, selledDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.selling_history_single_item_title);
            productPrice = itemView.findViewById(R.id.selling_history_single_item_price);
            buyerName = itemView.findViewById(R.id.selling_history_single_item_buyer_name);
            boughtQuantity = itemView.findViewById(R.id.selling_history_single_item_quantity);
            selledDate = itemView.findViewById(R.id.selling_history_single_item_date);
        }
    }
}
