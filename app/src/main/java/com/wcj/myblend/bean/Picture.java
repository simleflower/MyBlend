package com.wcj.myblend.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jayli on 2017/5/9 0009.
 */

public class Picture implements Parcelable {

    /**
     * id : 11577737954
     * date : 2016-10-25
     * downloadUrl : http://c.hiphotos.baidu.com/image/pic/item/f636afc379310a55515bfd76b54543a982261030.jpg
     * imageUrl : http://c.hiphotos.baidu.com/image/pic/item/f636afc379310a55515bfd76b54543a982261030.jpg
     * imageWidth : 500
     * imageHeight : 750
     * title : 静若繁花图7
     */

    private String id;
    private String date;
    private String downloadUrl;
    private String imageUrl;
    private int imageWidth;
    private int imageHeight;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.date);
        dest.writeString(this.downloadUrl);
        dest.writeString(this.imageUrl);
        dest.writeInt(this.imageWidth);
        dest.writeInt(this.imageHeight);
        dest.writeString(this.title);
    }

    public Picture() {
    }

    protected Picture(Parcel in) {
        this.id = in.readString();
        this.date = in.readString();
        this.downloadUrl = in.readString();
        this.imageUrl = in.readString();
        this.imageWidth = in.readInt();
        this.imageHeight = in.readInt();
        this.title = in.readString();
    }

    public static final Creator<Picture> CREATOR = new Creator<Picture>() {
        @Override
        public Picture createFromParcel(Parcel source) {
            return new Picture(source);
        }

        @Override
        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };
}
