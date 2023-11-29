package com.example.topnavigationbar;

import java.util.ArrayList;

public class VideoFolder {
    private String folderName;
    private ArrayList<VideoModel> videosInFolder;

    public VideoFolder(String folderName, ArrayList<VideoModel> videosInFolder) {
        this.folderName = folderName;
        this.videosInFolder = videosInFolder;
    }

    public String getFolderName() {
        return folderName;
    }

    public ArrayList<VideoModel> getVideosInFolder() {
        return videosInFolder;
    }
}
