package com.boomer.omer.kollabstr.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boomer.omer.kollabstr.R;
import com.boomer.omer.kollabstr.backend.twitteroauth.TwitterManager;
import com.boomer.omer.kollabstr.core.KollabstrFragment;
import com.boomer.omer.kollabstr.customviews.SocialMediaView;

/**
 * Created by Omer on 7/20/2016.
 */
public class PickSocialMediaFragment extends KollabstrFragment implements View.OnClickListener {

    private static final String TAG = PickSocialMediaFragment.class.getSimpleName();




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pick_socialmedia_view,null);

        ((SocialMediaView)view.findViewById(R.id.facebook_item)).setOnClickListener(this);
        ((SocialMediaView)view.findViewById(R.id.twitter_item)).setOnClickListener(this);
        ((SocialMediaView)view.findViewById(R.id.instagram_item)).setOnClickListener(this);
        ((SocialMediaView)view.findViewById(R.id.youtube_item)).setOnClickListener(this);


        return view;
    }

    @Override
    public void receiveMessage(Bundle bundle) {

    }

    @Override
    public void receiveMessage(Object object) {

    }

    @Override
    public String getReportTag() {
        return TAG;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.facebook_item:

                break;

            case R.id.twitter_item:
               getSetupProfileActivity().doTwitterLogin();
                break;

            case R.id.instagram_item:

                break;

            case R.id.youtube_item:

                break;
        }
    }

    private SetupProfileActivity getSetupProfileActivity(){
        return (SetupProfileActivity)getKollabstrActivity();
    }



}
