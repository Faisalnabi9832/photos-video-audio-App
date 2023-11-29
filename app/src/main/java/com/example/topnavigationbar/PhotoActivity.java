package com.example.topnavigationbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class PhotoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        // Retrieve the image data from the intent
        Intent intent = getIntent();
        String imageUri = intent.getStringExtra("photo"); // Retrieve the image URI or any other relevant data

        // Display the image
        ImageView imageView = findViewById(R.id.imageview1);
        Picasso.get().load(new File(imageUri)).into(imageView);
    }
}

