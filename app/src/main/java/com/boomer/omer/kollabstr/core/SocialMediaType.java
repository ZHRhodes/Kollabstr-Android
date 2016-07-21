package com.boomer.omer.kollabstr.core;

import com.boomer.omer.kollabstr.R;

/**
 * Created by Omer on 7/19/2016.
 */
public enum SocialMediaType {

    FACEBOOK("Facebook",0),
    TWITTER("Twitter",1),
    INSTAGRAM("Instagram",2),
    YOUTUBE("Youtube",3),
    VINE("Vine",4);

    private final String typeString;
    private final int number;

    SocialMediaType(String type,int i){
        this.typeString = type;
        this.number = i;
    }

    @Override
    public String toString(){
       return typeString;
    }

    public int getValue(){return number;}

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
