package com.sprinsec.mobile_v2.adapter;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableResource;
import com.google.gson.JsonObject;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.api.SetToWatchlistService;
import com.sprinsec.mobile_v2.data.model.UserHomeProductModel;
import com.sprinsec.mobile_v2.interfaces.GeneralCallBack;
import com.sprinsec.mobile_v2.interfaces.RecyclerViewInterface;
import com.sprinsec.mobile_v2.ui.user.user_single_product_view_activity;
import com.sprinsec.mobile_v2.util.repository.WatchlistRepository;

import java.util.List;

public class User_home_watchlist_recycler_view_adapter extends RecyclerView.Adapter<User_home_watchlist_recycler_view_adapter.MyViewHolder> {
    private final List<UserHomeProductModel> userHomeProductModelList;
    private final RecyclerViewInterface recyclerViewInterface;
    private final ImageButton fragment_user_watchlist_delete_button;
    private WatchlistRepository watchlistRepository;


    public User_home_watchlist_recycler_view_adapter(List<UserHomeProductModel> userHomeProductModelList, ImageButton fragment_user_watchlist_delete_button, RecyclerViewInterface recyclerViewInterface, WatchlistRepository watchlistRepository) {
        this.userHomeProductModelList = userHomeProductModelList;
        this.recyclerViewInterface = recyclerViewInterface;
        this.fragment_user_watchlist_delete_button = fragment_user_watchlist_delete_button;
        this.watchlistRepository = watchlistRepository;
    }

    @NonNull
    @Override
    public User_home_watchlist_recycler_view_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single__user__watchlist_item, parent, false);
        return new User_home_watchlist_recycler_view_adapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull User_home_watchlist_recycler_view_adapter.MyViewHolder holder, int position) {
        UserHomeProductModel product = userHomeProductModelList.get(position);

        holder.productId.setText(product.getProductId());
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(product.getProductPrice()); // Ensure formatting
        holder.rating.setText(product.getRating()); // Ensure it's a string
        Glide.with(holder.itemView.getContext())
                .load(product.getProductImage())
                .error(R.drawable.default_product_image)
                .placeholder(R.drawable.default_product_image)
                .into(holder.productImage);
        holder.itemView.setOnLongClickListener(v -> {
            // Get current background
            Drawable background = v.getBackground();

            // Check if the background is the selected drawable
            if (background != null && background.getConstantState() != null &&
                    background.getConstantState().equals(ContextCompat.getDrawable(v.getContext(), R.drawable.watchlist_selected_background_outline).getConstantState())) {

                v.setBackgroundResource(R.drawable.watchlist_deselected_background_outline);
            } else {
                v.setBackgroundResource(R.drawable.watchlist_selected_background_outline);

                // Set delete button listener inside the correct condition
                fragment_user_watchlist_delete_button.setOnClickListener(deleteButtonView -> {
                    SetToWatchlistService.Send(product.getProductId(), "1", "no", new GeneralCallBack() {
                        @Override
                        public void onSuccess(JsonObject response) {

                            ((Activity) v.getContext()).runOnUiThread(() -> {
                                Log.i("befit_logs", "Watchlist Updated: " + response);
                                if (response.has("status") && response.get("status").getAsString().equals("deleted")) {
                                    holder.itemView.setVisibility(View.GONE);
                                    userHomeProductModelList.remove(holder.getAdapterPosition());
                                    notifyItemRemoved(holder.getAdapterPosition());
                                    watchlistRepository = new WatchlistRepository(v.getContext());
                                    watchlistRepository.deleteWatchlistItem(product.getProductId());

                                }
                            });
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            ((Activity) deleteButtonView.getContext()).runOnUiThread(() ->
                                    Log.e("befit_logs", "Error: " + errorMessage));
                        }
                    });
                });
            }

            return true; // Ensure the long click is properly handled
        });
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
            productId = itemView.findViewById(R.id.single_user_watchlist_item_product_id);
            productName = itemView.findViewById(R.id.single_user_watchlist_item_product_name);
            productPrice = itemView.findViewById(R.id.single_user_watchlist_item_price);
            rating = itemView.findViewById(R.id.single_user_watchlist_item_rating);
            productImage = itemView.findViewById(R.id.single_user_watchlist_item_product_icon);
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
