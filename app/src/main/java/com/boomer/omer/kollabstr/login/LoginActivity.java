package com.boomer.omer.kollabstr.login;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.boomer.omer.kollabstr.R;
import com.boomer.omer.kollabstr.analytics.AnswersManager;
import com.boomer.omer.kollabstr.core.ComponentBus;
import com.boomer.omer.kollabstr.core.KollabstrActivity;


/**
 * Created by Omer on 7/12/2016.
 */
public class LoginActivity extends KollabstrActivity implements View.OnClickListener,ComponentBus.Listener{

    private static final String TAG = LoginActivity.class.getName();

    private EditText emailField;
    private EditText passwordField;
    private Button loginButton;
    private Button facebookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailField = (EditText)findViewById(R.id.activity_login_email_edittext);
        passwordField  = (EditText)findViewById(R.id.activity_login_password_edittext);
        loginButton    = (Button)findViewById(R.id.activity_login_login_button);
        facebookButton = (Button)findViewById(R.id.activity_login_facebook_button);

        loginButton.setOnClickListener(this);
        facebookButton.setOnClickListener(this);

        getComponentBus().subscribeToComponent("loginService",this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_login_login_button:
                final Editable email = emailField.getText();
                final Editable password = passwordField.getText();
                if(email.length() == 0 || password.length() == 0){
                    break;
                }

                Log.d("TEST","LOGGING IN");

                Bundle bundle = new Bundle();
                bundle.putString(LoginService.E_MAIL,email.toString());
                bundle.putString(LoginService.PASSWORD,password.toString());
                getServiceManager().startService(LoginService.class,bundle);

                break;

            case R.id.activity_login_facebook_button:

                break;
        }
    }

    @Override
    public void receiveMessage(Bundle bundle) {
        AnswersManager.reportLogin(AnswersManager.METHOD_BACKENDLESS);
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
