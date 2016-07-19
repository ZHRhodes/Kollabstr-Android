package com.boomer.omer.kollabstr.backend.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omer on 7/18/2016.
 */
public class Profile extends BackendlessObject {

    public static final String ACCOUNT_TALENT = "talent";
    public static final String ACCOUNT_CLIENT = "client";
    public static final String ACCOUNT_ADMIN  = "admin";

    public static Profile createEmpty(){
        Profile profile = new Profile();
        profile.username = "";
        profile.bio = "";
        profile.accountType = "";
        profile.followers = new ArrayList<>();
        profile.following = new ArrayList<>();
        profile.socialMediaAccounts = new ArrayList<>();
        return  profile;
    }

    private String username;
    private String bio;
    private String accountType;
    private List<SocialMedia> socialMediaAccounts;
    private List<Profile> followers;
    private List<Profile> following;

    public Profile(){

    }

    private Profile(Parcel in){
        followers = new ArrayList<>();
        following = new ArrayList<>();
        socialMediaAccounts = new ArrayList<>();


        username    = in.readString();
        bio         = in.readString();
        accountType = in.readString();

        int socialMediaAccountNumber = in.readInt();
        for(int i = 0;i<socialMediaAccountNumber ; i++){
            socialMediaAccounts.add(SocialMedia.CREATOR.createFromParcel(in));
        }

        int followerCount = in.readInt();
        for(int i = 0;i<followerCount ; i++){
            followers.add(Profile.CREATOR.createFromParcel(in));
        }

        int followingCount = in.readInt();
        for(int i = 0;i<followerCount ; i++){
            following.add(Profile.CREATOR.createFromParcel(in));
        }

    }

    public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() {
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public List<SocialMedia> getSocialMediaAccounts() {
        return socialMediaAccounts;
    }

    public void setSocialMediaAccounts(List<SocialMedia> socialMediaAccounts) {
        this.socialMediaAccounts = socialMediaAccounts;
    }

    public List<Profile> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Profile> followers) {
        this.followers = followers;
    }

    public List<Profile> getFollowing() {
        return following;
    }

    public void setFollowing(List<Profile> following) {
        this.following = following;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(username);
        parcel.writeString(bio);
        parcel.writeString(accountType);

        parcel.writeInt(socialMediaAccounts.size());
        for(SocialMedia socialMedia:socialMediaAccounts){
            parcel.writeParcelable(socialMedia,socialMedia.describeContents());
        }

        parcel.writeInt(followers.size());
        for(Profile profile:followers){
            parcel.writeParcelable(profile,profile.describeContents());
        }

        parcel.writeInt(following.size());
        for(Profile profile:following){
            parcel.writeParcelable(profile,profile.describeContents());
        }
    }
}
