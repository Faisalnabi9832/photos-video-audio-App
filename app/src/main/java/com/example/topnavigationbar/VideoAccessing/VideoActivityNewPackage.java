package com.example.topnavigationbar.VideoAccessing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.example.topnavigationbar.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class VideoActivityNewPackage extends AppCompatActivity {
    int VIDEO_CAPTURE_REQUEST=1002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video2);
        startVideoCapture();
    }
    private void startVideoCapture() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, VIDEO_CAPTURE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Into Activity Result");
        if (requestCode == VIDEO_CAPTURE_REQUEST && resultCode == RESULT_OK)
        {
            System.out.println("If Condition is true");
            Uri videoUri = data.getData();
            if (videoUri != null) {
                // Specify your desired directory path
                String desiredDirectoryPath = "/path/to/your/directory/";
                System.out.println("Video URI :"+videoUri.toString());

                // Create a File object for the desired directory
                File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "FaisalTest");

                if (!directory.exists()) {
                    directory.mkdirs(); // Create the directory if it doesn't exist
                }

                // Create a File object for the video file in the desired directory
                File videoFile = new File(directory, "my_video"+System.currentTimeMillis()+".mp4");

                // Move the captured video to the desired directory
                boolean success = moveVideoToDesiredDirectory(videoUri, videoFile);

                if (success) {
                    // Video is successfully saved to the desired directory
                    finish();
                } else {
                    // Error occurred while saving the video
                }
            }
        }
        else {
            System.out.println("Into the else portion");
        }
    }

    private boolean moveVideoToDesiredDirectory(Uri sourceUri, File destinationFile) {
        try {
            InputStream in = getContentResolver().openInputStream(sourceUri);
            OutputStream out = new FileOutputStream(destinationFile);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();

            return true; // Return true if the video is successfully moved
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Return false if there's an error
        }
    }

}

