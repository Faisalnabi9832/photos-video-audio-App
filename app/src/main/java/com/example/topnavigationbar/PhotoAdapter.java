package com.example.topnavigationbar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {

    ArrayList<PhotoModel> imageList;
    Context context;

    public PhotoAdapter(ArrayList<PhotoModel> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    @NonNull
    @Override
    public PhotoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PhotoModel model = imageList.get(position);
        Picasso.get()
                .load(new File(model.getPath())) // Use the file path instead of the URL
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent intent=new Intent(context,PhotoActivity.class);
               intent.putExtra("photo",model.getPath());
               context.startActivity(intent);

            }



        });

    }

    @Override
    public int getItemCount() {return imageList.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageVIew);


        }
    }
}