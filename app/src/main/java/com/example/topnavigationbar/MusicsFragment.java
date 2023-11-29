package com.example.topnavigationbar;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MusicsFragment extends Fragment {

    private ArrayList<MusicModel> audioList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MusicsAdapter adapter;

    public MusicsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_musics, container, false);

        recyclerView = v.findViewById(R.id.recycleforaudios);
        int spanCount = 3;
        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), spanCount, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            loadAudiosFromGallery();
        }

        return v;
    }

    private void loadAudiosFromGallery() {
        String[] projection = {
                MediaStore.Audio.Media.DISPLAY_NAME,   // Audio name
                MediaStore.Audio.Media.DATA,           // Audio path
                MediaStore.Audio.Media.SIZE            // Audio size
        };

        Cursor cursor = requireActivity().getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
        );

        if (cursor != null) {
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            int pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);

            while (cursor.moveToNext()) {
                String name = cursor.getString(nameColumn);
                String path = cursor.getString(pathColumn);
                long size = cursor.getLong(sizeColumn);
                MusicModel audioInfo = new MusicModel(name, path, size);  // Remove the unnecessary class argument
                audioList.add(audioInfo);
            }


            cursor.close();

            adapter = new MusicsAdapter(audioList, requireActivity());
            recyclerView.setAdapter(adapter);
        }
    }
    }
