package com.boomer.omer.kollabstr.profile;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.boomer.omer.kollabstr.R;
import com.boomer.omer.kollabstr.backend.SessionManager;
import com.boomer.omer.kollabstr.backend.objects.Profile;
import com.boomer.omer.kollabstr.backend.objects.Users;
import com.boomer.omer.kollabstr.core.KollabstrActivity;


/**
 * Created by Omer on 7/17/2016.
 */
public class SetupProfileActivity extends KollabstrActivity implements View.OnClickListener{

    private static final String TAG = SetupProfileActivity.class.getSimpleName();

    private Users mUserBeingWorkedOn;

    private EditText nameEditText;
    private EditText bioEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_profile);

        getComponentBus().subscribeToComponent(SessionManager.SESSION_UPDATED_EVENT,this);

        if(fromScratch()){
            getCurrentUser().setProfile(Profile.createEmpty());
        }

        ((Button)findViewById(R.id.setup_profile_finish)).setOnClickListener(this);

        nameEditText = (EditText)findViewById(R.id.setup_profile_name);
        bioEditText  = (EditText)findViewById(R.id.setup_profile_bio);

    }

    @Override
    public void receiveMessage(Bundle bundle) {
        Toast.makeText(SetupProfileActivity.this, "Profile Updated!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void receiveMessage(Object object) {

    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public String getType() {
        return TypeActivity;
    }

    private Users getCurrentUser(){
        if(mUserBeingWorkedOn == null){
                mUserBeingWorkedOn = getSessionManager().getCurrentUser();}
        return mUserBeingWorkedOn;
    }

    private boolean fromScratch(){
        return getSessionManager().getCurrentUser().getIsProfileSet()==null;
    }

    @Override
    public void onClick(View view) {
        getCurrentUser().getProfile().setUsername(nameEditText.getText().toString());
        getCurrentUser().getProfile().setBio(bioEditText.getText().toString());
        getSessionManager().updateUser(getCurrentUser());
    }
}
