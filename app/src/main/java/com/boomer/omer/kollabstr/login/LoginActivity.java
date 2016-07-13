package com.boomer.omer.kollabstr.login;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.boomer.omer.kollabstr.R;
import com.boomer.omer.kollabstr.core.KollabstrActivity;

/**
 * Created by Omer on 7/12/2016.
 */
public class LoginActivity extends KollabstrActivity implements View.OnClickListener{

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
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Backendless.UserService.login(email.toString(), password.toString(), new AsyncCallback<BackendlessUser>() {
                            @Override
                            public void handleResponse(BackendlessUser response) {
                                Log.d("RESPONSE","Successful");
                                finish();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Log.d("RESPONSE",fault.toString());
                            }
                        });

                    }
                });
                thread.start();

                break;

            case R.id.activity_login_facebook_button:

                break;
        }
    }
}
