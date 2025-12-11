package com.sprinsec.mobile_v2.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.data.model.OnboardingItemModel;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.PageViewHolder> {
    private Context context;
    private List<OnboardingItemModel> pages;

    public OnboardingAdapter(Context context, List<OnboardingItemModel> pages) {
        this.context = context;
        this.pages = pages;
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.onboarding_page, parent, false);
        return new PageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        OnboardingItemModel page = pages.get(position);

        if (page.isHasVideo()) {
            holder.videoView.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.GONE);

            // Set up video
            String videoPath = "android.resource://" + context.getPackageName() + "/raw/video_sample";
            holder.videoView.setVideoURI(Uri.parse(videoPath));
            holder.videoView.start();
            holder.videoView.setOnPreparedListener(mp -> {
                mp.setLooping(true);
                mp.setVolume(0f, 0f);
            });
        } else {
            holder.videoView.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.VISIBLE);
            holder.imageView.setImageResource(page.getImageResId());
        }

        holder.titleText.setText(page.getTitle());
        holder.descriptionText.setText(page.getDescription());
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    static class PageViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        ImageView imageView;
        TextView titleText;
        TextView descriptionText;

        PageViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            imageView = itemView.findViewById(R.id.imageView);
            titleText = itemView.findViewById(R.id.titleText);
            descriptionText = itemView.findViewById(R.id.descriptionText);
        }
    }
}
