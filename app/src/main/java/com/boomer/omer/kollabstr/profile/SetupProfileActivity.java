package com.boomer.omer.kollabstr.profile;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.boomer.omer.kollabstr.R;
import com.boomer.omer.kollabstr.backend.SessionManager;
import com.boomer.omer.kollabstr.backend.objects.Profile;
import com.boomer.omer.kollabstr.backend.objects.Users;
import com.boomer.omer.kollabstr.core.KollabstrActivity;
import com.boomer.omer.kollabstr.core.KollabstrFragment;


/**
 * Created by Omer on 7/17/2016.
 */
public class SetupProfileActivity extends KollabstrActivity {

    private static final String TAG = SetupProfileActivity.class.getSimpleName();

    private Users mUserBeingWorkedOn;

    private KollabstrFragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_profile);


        //if(fromScratch()){getCurrentUser().setProfile(Profile.createEmpty());} ///ADJUST
        EditProfileFragment editProfileFragment = new EditProfileFragment();
        getFragmentManager().beginTransaction().add(R.id.setup_profile_fragment_container,editProfileFragment).commit();



        getComponentBus().subscribeToComponent(SessionManager.SESSION_UPDATED_EVENT,this);
    }

    @Override
    public void receiveMessage(Bundle bundle) {
        Toast.makeText(SetupProfileActivity.this, "Profile Updated!", Toast.LENGTH_SHORT).show();
        updateCurrentUser();
    }

    @Override
    public void receiveMessage(Object object) {

    }

    @Override
    public String getReportTag() {
        return TAG;
    }


    private void updateCurrentUser(){
        mUserBeingWorkedOn = getSessionManager().getCurrentUser();
    }

    private Users getCurrentUser(){
        if(mUserBeingWorkedOn == null){
                mUserBeingWorkedOn = getSessionManager().getCurrentUser();}
        return mUserBeingWorkedOn;
    }

    private boolean fromScratch(){
        return getSessionManager().getCurrentUser().getIsProfileSet()==null;
    }

    private KollabstrFragment getCurrentFragment(){
        return  mCurrentFragment;
    }


}
