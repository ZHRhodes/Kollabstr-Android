package com.boomer.omer.kollabstr.backend.twitteroauth;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.boomer.omer.kollabstr.backend.objects.SocialMedia;
import com.boomer.omer.kollabstr.core.KollabstrActivity;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;



/**
 * Created by Omer on 7/20/2016.
 */
public class TwitterManager {

    private static final String TAG = TwitterManager.class.getSimpleName();

    private TwitterCore mTwitterCore;
    private KollabstrActivity mActivity;
    private Callback<TwitterSession> mTwitterSessionListener;
    private TwitterAuthClient mTwitterAuthClient;

    private boolean isSessionSucceded;


    public void onActivityResult(int requestCode, int resultCode, Intent data){
        getTwitterAuthClient().onActivityResult(requestCode,resultCode,data);
    }

    public TwitterManager(KollabstrActivity kollabstrActivity){
        mActivity = kollabstrActivity;
        isSessionSucceded = false;
    }

    public void logNewSession(){
       getTwitterAuthClient().authorize(mActivity,getCallbackListener());
    }

    public void logCurrentSessionOut(){
        getTwitterCore().logOut();
    }





    public Callback<TwitterSession> getCallbackListener(){
       if(mTwitterSessionListener==null){
           mTwitterSessionListener = new Callback<TwitterSession>() {
               @Override
               public void success(Result<TwitterSession> result) {
                    isSessionSucceded = true;
                    createSocialMediaObject(result.data);
               }

               @Override
               public void failure(TwitterException exception) {
                    isSessionSucceded = false;
                   Log.d(TAG,"Twitter Failure");
               }
           };
       }
        return mTwitterSessionListener;
    }

    private void createSocialMediaObject(TwitterSession twitterSession){

    }

    public SocialMedia getSocialMediaObject(){
        if(!isSessionSucceded){throw new RuntimeException(TAG + " does not have a successful session.");}

        return null;
    }

    private TwitterAuthClient getTwitterAuthClient(){
        if(mTwitterAuthClient == null){
           mTwitterAuthClient = new TwitterAuthClient();
        }
        return mTwitterAuthClient;
    }



    private TwitterCore getTwitterCore(){
        if(mTwitterCore == null){
            mTwitterCore = TwitterCore.getInstance();
        }
        return mTwitterCore;
    }



}

