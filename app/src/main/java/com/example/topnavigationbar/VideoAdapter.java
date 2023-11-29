package com.example.topnavigationbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    ArrayList<VideoModel> videoList;
    Context context;
    RecyclerView recyclerView;

    public VideoAdapter(ArrayList<VideoModel> videoList, Context context) {
        this.videoList = videoList;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.MyViewHolder holder, int position) {
        VideoModel model = videoList.get(position);
        String thumbnailPath = model.getThumbnail();
        String videoFilePath =model.getPath() ;

// Create a bitmap to hold the thumbnail
        Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(videoFilePath, MediaStore.Video.Thumbnails.MINI_KIND);
        holder.imageView.setImageBitmap(thumbnail);
        // Load the thumbnail image into the ImageView using Picasso or your preferred image loading library
////        Picasso.get()
////                .load(new File(model.getThumbnail()))
//                .into(holder.imageView);
        System.out.println("Name"+model.getName());
        System.out.println("Thumbnail"+model.getThumbnail());
        System.out.println("Path"+model.getPath());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("videoPath", model.getPath());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        RecyclerView recyclerView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.videoview);
            recyclerView=itemView.findViewById(R.id.recycleforfolder1);

        }
    }
}