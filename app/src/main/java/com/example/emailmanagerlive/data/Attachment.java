package com.example.emailmanagerlive.data;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Attachment extends BaseObservable {
    private String fileName;
    private String path;
    private String size;
    private long total;
    private boolean isDownload;

    @Bindable
    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

    @Bindable
    public String getSize() {
        return size;
    }

    public long getTotal() {
        return total;
    }

    @Bindable
    public boolean isDownload() {
        return isDownload;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }
}
