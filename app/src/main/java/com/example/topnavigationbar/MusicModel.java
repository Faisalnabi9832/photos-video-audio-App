package com.example.topnavigationbar;

public class MusicModel {

    String name;
    String path;
    long size;

    String Thumbnail;

    public MusicModel(String name, String path, long size) {
        this.name = name;
        this.path = path;
        this.size = size;
        this.Thumbnail=Thumbnail;
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

    public void setThumbnail(String thumbnail) {this.Thumbnail= thumbnail;}
    public String getThumbnail() {return Thumbnail;}

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getImagePath() {
        return null;
    }
}
