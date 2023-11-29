package com.example.topnavigationbar;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PhotoFragment extends Fragment {

    static final int CAMERA_REQUEST_CODE = 100;
    private static final int IMAGE_DIRECTORY = 100;
    ArrayList<PhotoModel> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private PhotoAdapter adapter;


    public PhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo, container, false);

        recyclerView = v.findViewById(R.id.recycle);

        Button cameraButton = v.findViewById(R.id.cameraButtonforphoto);

        ProgressDialog dialog = new ProgressDialog(requireContext());

        dialog.setMessage("Loading");

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the camera here
                openCamera();
            }
        });


        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            // Execute AsyncTask to load images from the gallery
            new LoadImagesTask(dialog, requireActivity()).execute();
        }

        return v;
    }

    private class LoadImagesTask extends AsyncTask<Void, Void, List<PhotoModel>> {

        ProgressDialog dialog;
        Context context;

        public LoadImagesTask(ProgressDialog dialog, Context context) {
            this.dialog = dialog;
            this.context = context;
        }

        @Override
        protected List<PhotoModel> doInBackground(Void... voids) {
            List<PhotoModel> imagesList = new ArrayList<>();
            String[] projection = {
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.SIZE
            };

            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    null
            );

            if (cursor != null) {
                int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
                int pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);

                int batchSize = 20; // Set the batch size
                int count = 0;

                while (cursor.moveToNext() && count < batchSize) {
                    String name = cursor.getString(nameColumn);
                    String path = cursor.getString(pathColumn);
                    long size = cursor.getLong(sizeColumn);
                    PhotoModel imageInfo = new PhotoModel(name, path, size);
                    imagesList.add(imageInfo);
                    count++;
                }

                cursor.close();
            }

            return imagesList;
        }

        @Override
        protected void onPostExecute(List<PhotoModel> result) {
            super.onPostExecute(result);

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

            if (result != null) {
                list.addAll(result);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(new PhotoAdapter(list, context));

            }
        }
    }



    private List<PhotoModel> loadImagesFromGallery() {
        List<PhotoModel> imagesList = new ArrayList<>();
        String[] projection = {
                MediaStore.Images.Media.DISPLAY_NAME,   // Image name
                MediaStore.Images.Media.DATA,           // Image path
                MediaStore.Images.Media.SIZE            // Image size
        };

        Cursor cursor = requireActivity().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
        );

        if (cursor != null) {
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
            int pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);

            while (cursor.moveToNext()) {
                String name = cursor.getString(nameColumn);
                String path = cursor.getString(pathColumn);
                long size = cursor.getLong(sizeColumn);
                PhotoModel imageInfo = new PhotoModel(name, path, size);
                imagesList.add(imageInfo);
            }

            cursor.close();

            adapter = new PhotoAdapter(list, requireActivity());
            recyclerView.setAdapter(adapter);
        }
        return imagesList;

    }


    // Rest of your existing code...

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    // Rest of your existing code...


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            // Get the captured image
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                // Save the image to external storage in the specified folder
                saveImageToStorage(imageBitmap);
            }
        }
    }

    private void saveImageToStorage(Bitmap imageBitmap) {
        // Create a directory if it doesn't exist
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), IMAGE_DIRECTORY + "/faisal");
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                // Directory created successfully
            } else {
                // Handle the case where directory creation fails
            }
        }

        // Generate a unique file name for the image
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "IMG_" + timeStamp + ".jpg";

        // Create a file in the specified directory
        File imageFile = new File(directory, fileName);

        try {
            // Save the image to the file
            FileOutputStream fos = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            // Image saved successfully
        } catch (IOException e) {
            e.printStackTrace();
            // Handle image save failure
        }
    }


}