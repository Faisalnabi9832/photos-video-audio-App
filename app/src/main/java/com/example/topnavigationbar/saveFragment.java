package com.example.topnavigationbar;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.Manifest;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class saveFragment extends Fragment {


    RecyclerView recyclerView;
    RecyclerView recyclerViewforvideo;
    private static final int REQUEST_CODE = 100;
    static final String IMAGE_DIRECTORY = "MyImages";
    static final String VIDEO_DIRECTORY = "MyImages";
    private String videoPath;

    public void saveFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save, container, false);

        recyclerView=view.findViewById(R.id.recycleSave);
        recyclerViewforvideo=view.findViewById(R.id.recycleSaveforvideo);
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, you can access the storage
        } else {
            // Request permission from the user
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
//
// Specify the path to the "Faisal" folder
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), IMAGE_DIRECTORY + "/Faisal");


// Create an ArrayList to hold the image data
        ArrayList<PhotoModel> imageList = new ArrayList<>();

// Check if the directory exists
        if (directory.exists()) {
            // List all files (images) in the directory
            File[] files = directory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    // Filter files based on file extension
                    return name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".jpeg") || name.toLowerCase().endsWith(".png");
                }
            });

            if (files != null && files.length > 0) {
                for (File imageFile : files) {
                    // Get the file path and name
                    String filePath = imageFile.getAbsolutePath();
                    String fileName = imageFile.getName();

                    // Create an instance of the ImageModel class
                    PhotoModel imageModel = new PhotoModel( fileName,filePath,0);
                    // Add the ImageModel instance to the ArrayList
                    imageList.add(imageModel);
                    System.out.println("Size "+imageList.size());
                }
            }
        }


        File directoryMyVideos = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "FaisalTest");

        ArrayList<VideoModel> videoList = new ArrayList<>();

// Check if the directory exists
        if (directoryMyVideos.exists()) {
            // List all video files in the directory
            File[] files = directoryMyVideos.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    // Filter video files based on file extension
                    return name.toLowerCase().endsWith(".mp4") || name.toLowerCase().endsWith(".3gp");
                }
            });



// ...

            if (files != null && files.length > 0) {
                for (File videoFile : files) {
                    // Get the file path and name
                    String filePath = videoFile.getAbsolutePath();
                    String fileName = videoFile.getName();

                    // Create an instance of the MediaMetadataRetriever
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    retriever.setDataSource(filePath);

                    // Get the video duration (size in your case)
                    String duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    long size = Long.parseLong(duration);

                    // Get the video thumbnail
                    Bitmap thumbnailBitmap = retriever.getFrameAtTime();

                    // Create a thumbnail file and save the bitmap
                    String thumbnailPath = saveThumbnailToFile(thumbnailBitmap);

                    // Create an instance of the VideoModel class with name, path, thumbnail, and size
                    VideoModel videoModel = new VideoModel(fileName, filePath, thumbnailPath, size);

                    // Add the VideoModel instance to the ArrayList
                    videoList.add(videoModel);

                    // Release the MediaMetadataRetriever
                    try {
                        retriever.release();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("Size " + videoList.size());
                }
            }

        }

// Create a VideoAdapter and set it to your RecyclerView
        VideoAdapter adapterforvideo = new VideoAdapter(videoList, requireActivity());
        int spanCountforvideo = 3;
        GridLayoutManager layoutManagerforvideo = new GridLayoutManager(requireActivity(), spanCountforvideo, LinearLayoutManager.VERTICAL, false);
        recyclerViewforvideo.setLayoutManager(layoutManagerforvideo);
        recyclerViewforvideo.setAdapter(adapterforvideo);



        PhotoAdapter adapter = new PhotoAdapter(imageList, requireActivity());
        int spanCount = 3;
        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), spanCount, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);





        return view;
    }
    private String saveThumbnailToFile(Bitmap thumbnailBitmap) {
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
        }

        return thumbnailPath.getAbsolutePath();
    }



}
