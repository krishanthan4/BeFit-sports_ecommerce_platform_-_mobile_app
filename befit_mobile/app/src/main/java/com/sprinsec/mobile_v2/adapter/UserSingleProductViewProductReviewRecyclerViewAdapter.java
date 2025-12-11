package com.sprinsec.mobile_v2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.model.UserSingleProductViewProductReviewModel;

import java.util.List;

public class UserSingleProductViewProductReviewRecyclerViewAdapter extends RecyclerView.Adapter<UserSingleProductViewProductReviewRecyclerViewAdapter.MyHolderView> {
    List<UserSingleProductViewProductReviewModel> userSingleProductViewProductReviewModelList;

    public UserSingleProductViewProductReviewRecyclerViewAdapter(List<UserSingleProductViewProductReviewModel> userSingleProductViewProductReviewModelList) {
        this.userSingleProductViewProductReviewModelList = userSingleProductViewProductReviewModelList;
    }

    @NonNull
    @Override
    public UserSingleProductViewProductReviewRecyclerViewAdapter.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_single_product_view_product_review_item, parent, false);
        return new MyHolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserSingleProductViewProductReviewRecyclerViewAdapter.MyHolderView holder, int position) {

        UserSingleProductViewProductReviewModel item = userSingleProductViewProductReviewModelList.get(position);
        holder.reviewerName.setText(item.getReviewerName());
        holder.reviewDate.setText(item.getReviewDate());
        holder.review.setText(item.getReview());
        holder.ratings.setRating(item.getRatings());
    }

    @Override
    public int getItemCount() {
        return userSingleProductViewProductReviewModelList.size();
    }

    public static class MyHolderView extends RecyclerView.ViewHolder {
        TextView reviewerName, reviewDate, review;
        RatingBar ratings;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);
            reviewerName = itemView.findViewById(R.id.single_user_single_product_view_product_review_item_reviewer_name);
            reviewDate = itemView.findViewById(R.id.single_user_single_product_view_product_review_item_review_date);
            review = itemView.findViewById(R.id.single_user_single_product_view_product_review_item_review_text);
            ratings = itemView.findViewById(R.id.single_user_single_product_view_product_review_item_review_rating);
        }
    }
}
