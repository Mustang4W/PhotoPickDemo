package com.mustang4w.wang.photopickproject;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 照片列表实体
 *
 * @author Wangym
 */
public class PhotoItemBean implements Parcelable {

    private String fileName;
    private String filePath;
    private Bitmap bitmapResource;
    private boolean isPhoto;

    public PhotoItemBean(String fileName, String filePath, Bitmap bitmapResource, boolean isPhoto) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.bitmapResource = bitmapResource;
        this.isPhoto = isPhoto;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Bitmap getBitmapResource() {
        return bitmapResource;
    }

    public void setBitmapResource(Bitmap bitmapResource) {
        this.bitmapResource = bitmapResource;
    }

    public boolean isPhoto() {
        return isPhoto;
    }

    public void setPhoto(boolean photo) {
        isPhoto = photo;
    }

    protected PhotoItemBean(Parcel in) {
        fileName = in.readString();
        filePath = in.readString();
        bitmapResource = in.readParcelable(Bitmap.class.getClassLoader());
        isPhoto = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileName);
        dest.writeString(filePath);
        dest.writeParcelable(bitmapResource, flags);
        dest.writeByte((byte) (isPhoto ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PhotoItemBean> CREATOR = new Creator<PhotoItemBean>() {
        @Override
        public PhotoItemBean createFromParcel(Parcel in) {
            return new PhotoItemBean(in);
        }

        @Override
        public PhotoItemBean[] newArray(int size) {
            return new PhotoItemBean[size];
        }
    };
}

