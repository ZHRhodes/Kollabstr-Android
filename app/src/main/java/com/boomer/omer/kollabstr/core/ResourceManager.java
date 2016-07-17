package com.boomer.omer.kollabstr.core;

import android.content.Context;

import com.boomer.omer.kollabstr.R;

/**
 * Created by Omer on 7/12/2016.
 */
public class ResourceManager {

    private static final String TAG = ResourceManager.class.getSimpleName();

    private static ResourceManager sResourceManager;
    private static boolean isInitialized = false;

    public static void initialize(Context context){
        if(isInitialized){throw new RuntimeException(TAG + " has already been initialized.");}
        sResourceManager = new ResourceManager(context);
        isInitialized = true;
    }

    public static ResourceManager getInstance(){
        if(!isInitialized){throw new RuntimeException(TAG + " has not been initialized.");}
        return sResourceManager;
    }



    private Context mContext;

    private ResourceManager(Context context){
        mContext = context;
    }

    public String getString(int stringId){
        return mContext.getResources().getString(stringId);
    }

    public  String getBackEndlessAppID(){
        return mContext.getResources().getString(R.string.backendless_ap_id);
    }

    public String getBackendlessAppSecret(){
        return mContext.getResources().getString(R.string.backendless_scret_key);
    }

    public String getFacebookAppID(){
        return mContext.getResources().getString(R.string.facebook_app_id);
    }

    public String getTwitterKey(){
        return mContext.getResources().getString(R.string.twitter_key);
    }

    public String getTwitterSecret(){
        return mContext.getResources().getString(R.string.twitter_secret);
    }
}
