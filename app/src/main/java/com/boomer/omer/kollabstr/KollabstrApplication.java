package com.boomer.omer.kollabstr;

import android.app.Application;

import com.boomer.omer.kollabstr.backend.BackEndlessCore;

/**
 * Created by Omer on 7/12/2016.
 */
public class KollabstrApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        BackEndlessCore.initialize(this);


    }
}
