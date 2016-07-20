package com.boomer.omer.kollabstr.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.boomer.omer.kollabstr.R;
import com.boomer.omer.kollabstr.backend.SessionManager;
import com.boomer.omer.kollabstr.core.KollabstrActivity;
import com.boomer.omer.kollabstr.profile.SetupProfileActivity;
import com.facebook.CallbackManager;


/**
 * Created by Omer on 7/12/2016.
 */
public class LoginActivity extends KollabstrActivity implements View.OnClickListener{

    private static final String TAG = LoginActivity.class.getSimpleName();

    private CallbackManager mCallbackManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode,resultCode,data);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);

        ((Button)findViewById(R.id.login_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.facebook_login)).setOnClickListener(this);
       //((Button)findViewById(R.id.twitter_login)).setOnClickListener(this);

        getComponentBus().subscribeToComponent(SessionManager.LOGIN_SUCCESS_EVENT,this);
        getComponentBus().subscribeToComponent(SessionManager.SESSION_UPDATED_EVENT,this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getSessionManager().isCurrentUserPresent()){startSettingUpProfile();}
    }

    @Override
    public void receiveMessage(Bundle bundle) {
        if(bundle == null){return;}
       if(bundle.containsKey(SessionManager.SESSION_DATA_KEY)){
           Log.d(TAG,"SESSION SUCCESSFUL");
           if(getSessionManager().getCurrentUser().getIsProfileSet() == null){startSettingUpProfile();}
       }
    }

    @Override
    public void receiveMessage(Object object) {

    }

    private void startSettingUpProfile(){
        startActivity(new Intent(this, SetupProfileActivity.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button:
                String email = ((EditText)findViewById(R.id.login_email_edittext)).getText().toString();
                String password = ((EditText)findViewById(R.id.login_password_edittext)).getText().toString();
                getSessionManager().loginWithBackendless(email,password);
                break;

            case R.id.facebook_login:
                getSessionManager().loginWithFacebook(this,getCallbackManager());
                break;
/*
            case R.id.twitter_login:
                getSessionManager().loginWithTwitter(this);
                break;
*/
            default:
                //Todo: nothing for now
                break;
        }
    }

    private CallbackManager getCallbackManager(){
        if(mCallbackManager == null){
            mCallbackManager = CallbackManager.Factory.create();
        }
        return mCallbackManager;
    }


    @Override
    public String getReportTag() {
        return TAG;
    }
}
