package com.example.topnavigationbar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.io.File;
import java.util.ArrayList;

public class VideoGridAdapter extends RecyclerView.Adapter<VideoGridAdapter.MyViewHolder> {
    private ArrayList<VideoModel> videoModel;
    private Context context;

    public VideoGridAdapter(ArrayList<VideoModel> videoModel, Context context) {
        this.videoModel = videoModel;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VideoModel model = videoModel.get(position);

        // Use Glide library to load video thumbnails
        Glide.with(context)
                .load(new File(model.getThumbnail()))
                .into(holder.videoView);

        holder.imageView.setImageResource(R.drawable.folder);

        holder.videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("video", model.getPath());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoModel.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView videoView;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoview);
            imageView = itemView.findViewById(R.id.videoview);
        }
    }
}
