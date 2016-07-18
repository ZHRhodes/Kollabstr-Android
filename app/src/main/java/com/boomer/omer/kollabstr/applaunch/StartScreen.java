package com.boomer.omer.kollabstr.applaunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.boomer.omer.kollabstr.R;
import com.boomer.omer.kollabstr.core.KollabstrActivity;
import com.boomer.omer.kollabstr.login.LoginActivity;

/**
 * Created by Omer on 7/17/2016.
 */
public class StartScreen extends KollabstrActivity implements View.OnClickListener{

    private static final String TAG = StartScreen.class.getSimpleName();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ((Button)findViewById(R.id.start_activity_signup_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.start_activity_login_button)).setOnClickListener(this);


        showSplash();

    }

    private void showSplash(){
        startActivity(new Intent(this,SplashScreen.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_activity_signup_button:

                break;

            case R.id.start_activity_login_button:
                launchLoginActivity();
                break;
        }
    }

    private void launchLoginActivity(){
        startActivity(new Intent(this, LoginActivity.class));
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
