package com.example.topnavigationbar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class MusicsAdapter extends RecyclerView.Adapter<MusicsAdapter.MyViewHolder> {

    private ArrayList<MusicModel> audioList;
    private Context context;

    public MusicsAdapter(ArrayList<MusicModel> audioList, Context context) {
        this.audioList = audioList;
        this.context = context;
    }

    @NonNull
    @Override
    public MusicsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_music, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicsAdapter.MyViewHolder holder, int position) {
        MusicModel model = audioList.get(position);

        if (model.getThumbnail() != null) {
            // Use Glide library to load audio thumbnails
            Glide.with(context)
                    .load(new File(model.getThumbnail()))
                    .into(holder.audioIcon); // Update the view reference here
        } else {
            // Handle the case when thumbnail path is null
            holder.audioIcon.setImageResource(R.drawable.img);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MusicsActivity.class);
                intent.putExtra("audio", model.getPath());
                context.startActivity(intent);
            }
        });

        // Set other views such as audioName, audioSize, and audioPath
        holder.audioName.setText(model.getName());
        holder.audioSize.setText(String.valueOf(model.getSize()));
        holder.audioPath.setText(model.getPath());
    }



    @Override
    public int getItemCount() {
        return audioList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView audioIcon;
        TextView audioName;
        TextView audioSize;
        TextView audioPath;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            audioIcon = itemView.findViewById(R.id.audioview);
            audioName = itemView.findViewById(R.id.textviewforname);
            audioSize = itemView.findViewById(R.id.textviewforsize);
            audioPath = itemView.findViewById(R.id.textviewforpath);
        }
    }
}
