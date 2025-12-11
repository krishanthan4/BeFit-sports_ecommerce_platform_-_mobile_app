package com.sprinsec.mobile_v2.data.model;

public class AdminDashboardFeedbackModel {
    private String feedbackId;
    private String ReviewerName;
    private String feedbackDescription;
    private int feedbackRating;
    private String feedbackDate;
    private String productName;

    public AdminDashboardFeedbackModel(String feedbackId, String ReviewerName, String feedbackDescription, int feedbackRating, String feedbackDate, String productName) {
        this.feedbackId = feedbackId;
        this.ReviewerName = ReviewerName;
        this.feedbackDescription = feedbackDescription;
        this.feedbackRating = feedbackRating;
        this.feedbackDate = feedbackDate;
        this.productName = productName;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFeedbackTitle() {
        return ReviewerName;
    }

    public void setFeedbackTitle(String ReviewerName) {
        this.ReviewerName = ReviewerName;
    }

    public String getFeedbackDescription() {
        return feedbackDescription;
    }

    public void setFeedbackDescription(String feedbackDescription) {
        this.feedbackDescription = feedbackDescription;
    }

    public int getFeedbackRating() {
        return feedbackRating;
    }

    public void setFeedbackRating(int feedbackRating) {
        this.feedbackRating = feedbackRating;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
