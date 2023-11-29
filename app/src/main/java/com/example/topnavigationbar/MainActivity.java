package com.example.topnavigationbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TabLayout tab;
    ViewPager ViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tab = findViewById(R.id.tab);
        ViewPager = findViewById(R.id.viewpager);
        ViewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ViewPagerMessengerAdapter adapter = new ViewPagerMessengerAdapter(getSupportFragmentManager());
        ViewPager.setAdapter(adapter);
        tab.setupWithViewPager(ViewPager);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Main Activity");
        if (requestCode == 1001) {
            System.out.println("Main Activity        Request Code");
            Uri videoUri = data.getData();
            Log.d("checkingVideoUri", "onActivityResult: " + videoUri);
            if (videoUri != null) {
                String videoFilePath = uriToFile(videoUri).getAbsolutePath();
                if (videoFilePath != null) {
                    // Save the video to external storage
                    saveVideoToStorage(videoFilePath);


                } else {
                    // Handle the case where video file path is null
                    System.out.println("Video path is null");
                }
            } else {
                // Handle the case where video URI is null
                System.out.println("Video URI is Null");

            }
        }else {
        System.out.println("Request Code is not True");}
    }

    private File uriToFile(Uri uri) {
        String filePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            filePath = cursor.getString(column_index);
            cursor.close();
        }

        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }


    private void saveVideoToStorage(String videoFilePath) {
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "FaisalTest");
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                Toast.makeText(this, "Directory Created", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Directory Already Created", Toast.LENGTH_SHORT).show();
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "VIDEO_" + timeStamp + ".mp4";
        System.out.println("FILE NAME :"+fileName);
        // Create a file in the specified directory
        File videoFile = new File(directory, fileName);

        try {
            FileInputStream fis = new FileInputStream(new File(videoFilePath));
            FileOutputStream fos = new FileOutputStream(videoFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fis.close();
            fos.close();
            Toast.makeText(this, "Video saved successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving video: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}