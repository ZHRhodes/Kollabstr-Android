package com.boomer.omer.kollabstr;

import android.app.Application;

import com.boomer.omer.kollabstr.analytics.AnswersManager;
import com.boomer.omer.kollabstr.backend.BackEndlessCore;
import com.boomer.omer.kollabstr.backend.SessionManager;
import com.boomer.omer.kollabstr.core.ResourceManager;
import com.boomer.omer.kollabstr.core.ServiceManager;
import com.facebook.FacebookSdk;

/**
 * Created by Omer on 7/12/2016.
 */
public class KollabstrApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Locals
        ResourceManager.initialize(this);
        ServiceManager.initialize(this);

        //APIs
        BackEndlessCore.initialize(this);
        SessionManager.initialize(this);
        AnswersManager.initialize(this);
        FacebookSdk.sdkInitialize(this);


    }
}
