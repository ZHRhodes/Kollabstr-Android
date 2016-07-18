package com.boomer.omer.kollabstr.profile;

import android.os.Bundle;

import com.boomer.omer.kollabstr.R;
import com.boomer.omer.kollabstr.core.KollabstrActivity;


/**
 * Created by Omer on 7/17/2016.
 */
public class SetupProfileActivity extends KollabstrActivity {

    private static final String TAG = SetupProfileActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_profile);
    }

    @Override
    public void receiveMessage(Bundle bundle) {

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
}
