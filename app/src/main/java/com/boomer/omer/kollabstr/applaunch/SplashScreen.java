package com.boomer.omer.kollabstr.applaunch;

import android.os.Bundle;
import android.os.CountDownTimer;

import com.boomer.omer.kollabstr.R;
import com.boomer.omer.kollabstr.core.KollabstrActivity;

/**
 * Created by Omer on 7/17/2016.
 */
public class SplashScreen extends KollabstrActivity {

    private static final String TAG = SplashScreen.class.getSimpleName();

    private static final long SPLASH_LENGTH = 3000;
    private static final long TICK_FREQUENCY= 1000;


    private CountDownTimer mSplashtimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSplashtimer = setSplashTimer();
        mSplashtimer.start();
    }


    private CountDownTimer setSplashTimer(){
        return new CountDownTimer(SPLASH_LENGTH,TICK_FREQUENCY) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                finish();
            }
        };
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

}
