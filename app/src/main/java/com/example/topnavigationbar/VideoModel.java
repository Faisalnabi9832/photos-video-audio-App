package com.example.topnavigationbar;

public class VideoModel {
    private String name;
    private String path;
    private String thumbnail;
    private long size;

    public VideoModel(String name, String path, String thumbnail, long size) {
        this.name = name;
        this.path = path;
        this.thumbnail = thumbnail;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
