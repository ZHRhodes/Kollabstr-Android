package com.boomer.omer.kollabstr.core;

import com.boomer.omer.kollabstr.R;

/**
 * Created by Omer on 7/19/2016.
 */
public enum SocialMediaType {

    FACEBOOK("Facebook"),
    TWITTER("Twitter"),
    INSTAGRAM("Instagram"),
    YOUTUBE("Youtube"),
    VINE("Vine");

    private String typeString;

    SocialMediaType(String type){
        this.typeString = type;
    }

    @Override
    public String toString(){
       return typeString;
    }

    public int getIcon(){
        switch (this){
            case FACEBOOK:
                return R.drawable.facebookicon;
            case TWITTER:
                return R.drawable.twittericon;
            case INSTAGRAM:
                return R.drawable.instagramicon;
            case YOUTUBE:
                return R.drawable.youtubeicon;
        }
        return 0;
    }


}
