package com.example.emailmanagerlive.data;

import android.os.Parcel;
import android.os.Parcelable;

public class EmailParams implements Parcelable {
    private int type;
    private long id;
    private int function;

    public EmailParams() {
    }

    protected EmailParams(Parcel in) {
        type = in.readInt();
        id = in.readLong();
        function = in.readInt();
    }

    public static final Creator<EmailParams> CREATOR = new Creator<EmailParams>() {
        @Override
        public EmailParams createFromParcel(Parcel in) {
            return new EmailParams(in);
        }

        @Override
        public EmailParams[] newArray(int size) {
            return new EmailParams[size];
        }
    };

    public void setType(int type) {
        this.type = type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFunction(int function) {
        this.function = function;
    }

    public int getType() {
        return type;
    }

    public long getId() {
        return id;
    }

    public int getFunction() {
        return function;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeLong(id);
        dest.writeInt(function);
    }

    public static class Type{
        public static final int INBOX = 1;
        public static final int SENT = 2;
        public static final int DRAFTS = 3;
    }
    public static class Function{
        public static final int NORMAL_SEND = 1;
        public static final int FORWARD = 2;
        public static final int REPLY = 3;
        public static final int EDIT = 4;
    }
}
