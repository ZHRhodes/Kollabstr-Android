package com.boomer.omer.kollabstr.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.boomer.omer.kollabstr.R;
import com.boomer.omer.kollabstr.analytics.AnswersManager;
import com.boomer.omer.kollabstr.backend.SessionManager;
import com.boomer.omer.kollabstr.core.ComponentBus;
import com.boomer.omer.kollabstr.core.KollabstrActivity;
import com.boomer.omer.kollabstr.customviews.KollabstrFacebookButton;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;


/**
 * Created by Omer on 7/12/2016.
 */
public class LoginActivity extends KollabstrActivity implements View.OnClickListener,ComponentBus.Listener{

    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText emailField;
    private EditText passwordField;
    private Button loginButton;
    private KollabstrFacebookButton facebookButton;

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

        emailField = (EditText)findViewById(R.id.activity_login_email_edittext);
        passwordField  = (EditText)findViewById(R.id.activity_login_password_edittext);
        loginButton    = (Button)findViewById(R.id.activity_login_login_button);

        facebookButton = (KollabstrFacebookButton) findViewById(R.id.activity_login_facebook_button);
        mCallbackManager = CallbackManager.Factory.create();
        facebookButton.registerCallback(mCallbackManager,getFacebookCallBack() );

        loginButton.setOnClickListener(this);

        getComponentBus().subscribeToComponent(SessionManager.ACTION,this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_login_login_button:
                doBackEndlessLogin();
                break;
        }
    }

    private FacebookCallback<LoginResult> getFacebookCallBack(){
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getSessionManager().receiveFacebookLoginResult(loginResult);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                getSessionManager().receiveFacebookLoginError(error);
            }
        };
    }

    private void doBackEndlessLogin(){
        Editable email = emailField.getText();
        Editable password = passwordField.getText();
        if(email.length() == 0 || password.length() == 0){
            //Todo: Handle it
            return;
        }
        getSessionManager().loginWithBackendless(email.toString(),password.toString());
    }

    private SessionManager getSessionManager(){
        return SessionManager.getInstance();
    }


    @Override
    public void receiveMessage(Bundle bundle) {
        finish();
    }

    @Override
    public void receiveMessage(Object object) {

    }

    @Override
    public String getTag() {
        return "LoginActivity";
    }

    @Override
    public String getType() {
        return TypeActivity;
    }



}
