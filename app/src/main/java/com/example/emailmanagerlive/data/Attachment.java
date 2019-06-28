package com.example.emailmanagerlive.data;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Attachment extends BaseObservable implements Parcelable {
    private String fileName;
    private String path;
    private String size;
    private long total;
    private boolean isDownload;
    private boolean enable;

    public Attachment() {
    }

    public Attachment(String fileName, String size, long total) {
        this.fileName = fileName;
        this.size = size;
        this.total = total;
    }

    public Attachment(String fileName, String path, String size, long total) {
        this.fileName = fileName;
        this.path = path;
        this.size = size;
        this.total = total;
    }

    protected Attachment(Parcel in) {
        fileName = in.readString();
        path = in.readString();
        size = in.readString();
        total = in.readLong();
        isDownload = in.readByte() != 0;
        enable = in.readByte() != 0;
    }

    public static final Creator<Attachment> CREATOR = new Creator<Attachment>() {
        @Override
        public Attachment createFromParcel(Parcel in) {
            return new Attachment(in);
        }

        @Override
        public Attachment[] newArray(int size) {
            return new Attachment[size];
        }
    };

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

    @Bindable
    public boolean isEnable() {
        return enable;
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

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileName);
        dest.writeString(path);
        dest.writeString(size);
        dest.writeLong(total);
        dest.writeByte((byte) (isDownload ? 1 : 0));
        dest.writeByte((byte) (enable ? 1 : 0));
    }
}
