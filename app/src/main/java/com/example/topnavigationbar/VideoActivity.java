package com.example.topnavigationbar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);





        // Retrieve the video data from the intent
        Intent intent = getIntent();
        String videoUri = intent.getStringExtra("videoPath"); // Retrieve the video URI or any other relevant data

        // Prepare the VideoView
        VideoView videoView = findViewById(R.id.videoview1);

        videoView.setVideoURI(Uri.parse(videoUri));

        // Add media controller for playback controls
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        // Start playing the video
        videoView.start();
    }
}


