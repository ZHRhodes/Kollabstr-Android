package com.boomer.omer.kollabstr.backend.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Omer on 7/18/2016.
 */
public class SocialMedia extends BackendlessObject{
    private static final String FACEBOOK  = "facebook";
    private static final String TWITTER   = "twitter;";
    private static final String INSTAGRAM = "instagram";
    private static final String YOUTUBE   = "youtube";

    private String type;
    private String username;

    public SocialMedia(){}

    private SocialMedia(Parcel in){
        objectId = in.readString();
        ownerId  = in.readString();
        type     = in.readString();
        username = in.readString();
    }

    public static final Parcelable.Creator<SocialMedia> CREATOR = new Parcelable.Creator<SocialMedia>() {
        public SocialMedia createFromParcel(Parcel in) {
            return new SocialMedia(in);
        }

        public SocialMedia[] newArray(int size) {
            return new SocialMedia[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(type);
        parcel.writeString(username);
    }
}
