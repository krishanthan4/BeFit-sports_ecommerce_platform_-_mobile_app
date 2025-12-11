package com.sprinsec.mobile_v2.data.model;

public class UserSingleProductViewProductReviewModel {
    private float ratings;
    private String review;
    private String reviewDate;
    private String reviewerName;

    public UserSingleProductViewProductReviewModel(float ratings, String review, String reviewDate, String reviewerName) {
        this.ratings = ratings;
        this.review = review;
        this.reviewDate = reviewDate;
        this.reviewerName = reviewerName;
    }

    public float getRatings() {
        return ratings;
    }

    public void setRatings(float ratings) {
        this.ratings = ratings;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }
}
