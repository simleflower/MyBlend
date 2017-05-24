package com.wcj.myblend.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jayli on 2017/5/7 0007.
 */

public class NewsChanel implements Parcelable {

    /**
     * channelId : 5572a108b3cdc86cf39001ce
     * name : 国际焦点
     */

    private String channelId;
    private String name;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public NewsChanel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.channelId);
        dest.writeString(this.name);
    }

    protected NewsChanel(Parcel in) {
        this.channelId = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<NewsChanel> CREATOR = new Parcelable.Creator<NewsChanel>() {
        @Override
        public NewsChanel createFromParcel(Parcel source) {
            return new NewsChanel(source);
        }

        @Override
        public NewsChanel[] newArray(int size) {
            return new NewsChanel[size];
        }
    };
}
