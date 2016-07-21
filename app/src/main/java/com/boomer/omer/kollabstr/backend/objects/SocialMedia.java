package com.boomer.omer.kollabstr.backend.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.boomer.omer.kollabstr.core.SocialMediaType;

/**
 * Created by Omer on 7/18/2016.
 */
public class SocialMedia extends BackendlessObject{

    private String type;
    private String username;
    private int impact;

    public SocialMedia(){}

    private SocialMedia(Parcel in){
        objectId = in.readString();
        ownerId  = in.readString();
        type     = in.readString();
        username = in.readString();
        impact   = in.readInt();
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

    public int getImpact() { return impact; }

    public void setImpact(int impact) { this.impact = impact; }

    public SocialMediaType getTypeEnum(){
        switch (type){
            case "Facebook":
                return SocialMediaType.FACEBOOK;
            case "Twitter":
                return SocialMediaType.TWITTER;
            case "Instagram":
                return SocialMediaType.INSTAGRAM;
            case "Youtube":
                return  SocialMediaType.YOUTUBE;
            case "Vine":
                return SocialMediaType.VINE;
        }
        return null;
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
        parcel.writeInt(impact);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){return true;}
        return this.getType().equals(((SocialMedia)obj).getType()) && this.getUsername().equals(((SocialMedia)obj).getUsername());
    }
}
