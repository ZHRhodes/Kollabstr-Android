package com.boomer.omer.kollabstr.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.boomer.omer.kollabstr.R;
import com.boomer.omer.kollabstr.backend.SessionManager;
import com.boomer.omer.kollabstr.backend.objects.SocialMedia;
import com.boomer.omer.kollabstr.core.KollabstrFragment;
import com.boomer.omer.kollabstr.customviews.SocialMediaView;

import java.util.List;

/**
 * Created by Omer on 7/19/2016.
 */
public class EditProfileFragment extends KollabstrFragment implements View.OnClickListener{

    private static final String TAG = EditProfileFragment.class.getSimpleName();

    private LinearLayout mSocialMediaStack;

    private EditText usernameEditText;
    private EditText bioEditText;


    @Override
    public void onStart() {
        super.onStart();
        getComponentBus().subscribeToComponent(SessionManager.SESSION_UPDATED_EVENT,this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile_view,null);

        mSocialMediaStack = (LinearLayout)view.findViewById(R.id.setup_profile_socialmedia_stack);
        ((ImageView)view.findViewById(R.id.setup_profile_profile_image)).setOnClickListener(this);
        ((ImageView)view.findViewById(R.id.setup_profile_add_socialmedia)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.setup_profile_finish)).setOnClickListener(this);

        usernameEditText = (EditText)view.findViewById(R.id.setup_profile_name);
        bioEditText      = (EditText)view.findViewById(R.id.setup_profile_bio);

        usernameEditText.setText(getSessionManager().getCurrentUser().getProfile().getUsername());
        bioEditText.setText(getSessionManager().getCurrentUser().getProfile().getBio());

        List<SocialMedia> socialMediaList = getSessionManager().getCurrentUser().getProfile().getSocialMediaAccounts();
        Log.d(TAG,Integer.toString(socialMediaList.size()));
        for(SocialMedia socialMedia:socialMediaList){
            SocialMediaView socialMediaView = new SocialMediaView(getKollabstrActivity());
            socialMediaView.setSocialMediaType(socialMedia.getTypeEnum());
            socialMediaView.setImpact(socialMedia.getImpact());
            mSocialMediaStack.addView(socialMediaView);
        }



        return view;
    }

    @Override
    public void receiveMessage(Bundle bundle) {

    }

    @Override
    public void receiveMessage(Object object) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setup_profile_profile_image:

                break;

            case R.id.setup_profile_finish:

                break;

            case R.id.setup_profile_add_socialmedia:

                break;
        }
    }

    @Override
    public String getReportTag() {
        return TAG;
    }
}
