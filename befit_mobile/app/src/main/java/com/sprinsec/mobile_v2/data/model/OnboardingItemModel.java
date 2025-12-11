package com.sprinsec.mobile_v2.data.model;

import com.sprinsec.mobile_v2.R;

public class OnboardingItemModel {
    private boolean hasVideo;
    private int imageResId;
    private String title;
    private String description;

    public OnboardingItemModel(boolean hasVideo, Integer imageResId, String title, String description) {
        this.hasVideo = hasVideo;
        this.imageResId = imageResId != null ? imageResId : 0;
        this.title = title;
        this.description = description;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
