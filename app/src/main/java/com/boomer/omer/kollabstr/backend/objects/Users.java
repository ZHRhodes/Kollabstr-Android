package com.boomer.omer.kollabstr.backend.objects;


import android.os.Parcel;
import android.os.Parcelable;

import com.backendless.BackendlessUser;

import java.util.Date;

/**
 * Created by Omer on 7/18/2016.
 */
public class Users extends BackendlessObject {


    public static Users createFrom(BackendlessUser backendlessUser){
        Users users = new Users();
        users.setObjectId(backendlessUser.getObjectId());
        users.setEmail(backendlessUser.getEmail());
        users.setName((String)backendlessUser.getProperty("name"));
        users.setUpdated((Date)backendlessUser.getProperty("updated"));
        users.setCreated((Date)backendlessUser.getProperty("created"));
        return users;
    }

    private String email;
    private String name;
    private String accountType;
    private Boolean isProfileSet;
    private Profile profile;

    public Users(){}

    private Users(Parcel in){
        objectId = in.readString();
        ownerId  = in.readString();
        email    = in.readString();
        name     = in.readString();
        accountType = in.readString();
        isProfileSet = in.readByte()==1;
        profile = Profile.CREATOR.createFromParcel(in);
    }

    public static final Parcelable.Creator<Users> CREATOR = new Parcelable.Creator<Users>() {
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void makeAdmin(){accountType = "admin";}

    public boolean isAdmin(){return accountType.equals("admin");}

    public void makeClient(){accountType="client";}

    public boolean isClient(){return accountType.equals("client");}

    public void makeTalent(){accountType = "talent";}

    public boolean isTalent(){return accountType.equals("talent");}

    public Boolean getIsProfileSet() {
        return isProfileSet;
    }

    public void setIsProfileSet(Boolean profileSet) {
        isProfileSet = profileSet;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(email);
        parcel.writeString(name);
        parcel.writeString(accountType);
        parcel.writeByte(isProfileSet ? (byte)1 : (byte) 0);
        parcel.writeParcelable(profile,profile.describeContents());
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }
}
