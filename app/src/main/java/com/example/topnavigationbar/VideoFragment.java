package com.example.topnavigationbar;
import android.Manifest;
import android.media.ThumbnailUtils;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.topnavigationbar.VideoAccessing.VideoActivityNewPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;



public class VideoFragment extends Fragment {


    private static final int CAMERA_REQUEST_CODE = 100;
    private static final String  VIDEO_DIRECTORY = "MyVideos";

    private ArrayList<VideoModel> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private VideoAdapter adapter;


    public VideoFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video, container, false);
        recyclerView = v.findViewById(R.id.recycleforfolder1);
        Button cameraButton = v.findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the camera here
                openCamera();

            }
        });



        int spanCount = 3;
        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), spanCount, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            loadVideosFromGallery();
        }
        return v;
    }




// ...

    private void loadVideosFromGallery() {
        String[] projection = {
                MediaStore.Video.Media.DISPLAY_NAME,   // Video name
                MediaStore.Video.Media.DATA,           // Video path
                MediaStore.Video.Media.SIZE            // Video size
        };

        Cursor cursor = requireActivity().getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
        );

        if (cursor != null) {
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
            int pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);

            while (cursor.moveToNext()) {
                String name = cursor.getString(nameColumn);
                String path = cursor.getString(pathColumn);
                long size = cursor.getLong(sizeColumn);

                // Generate thumbnail for the video
                Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);

                // Save the thumbnail to a file
                String thumbnailPath = saveThumbnailToFile(thumbnail);

                VideoModel videoInfo = new VideoModel(name, path, thumbnailPath, size);
                list.add(videoInfo);
            }

            cursor.close();
            adapter = new VideoAdapter(list, requireActivity());
            recyclerView.setAdapter(adapter);
        }
    }

    private String saveThumbnailToFile(Bitmap thumbnailBitmap) {
        if (thumbnailBitmap == null) {
            // Handle the case where the thumbnailBitmap is null
            return null;
        }

        File thumbnailFile = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "thumbnails");
        if (!thumbnailFile.exists()) {
            thumbnailFile.mkdirs();
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String thumbnailFileName = "THUMBNAIL_" + timeStamp + ".jpg";

        File thumbnailPath = new File(thumbnailFile, thumbnailFileName);

        try {
            FileOutputStream fos = new FileOutputStream(thumbnailPath);
            thumbnailBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error if there's an issue with saving the thumbnail
            return null;
        }

        return thumbnailPath.getAbsolutePath();
    }






    private void openCamera() {
     startActivity(new Intent(requireActivity(), VideoActivityNewPackage.class));
    }





}








