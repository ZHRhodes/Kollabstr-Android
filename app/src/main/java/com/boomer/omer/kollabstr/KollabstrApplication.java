package com.boomer.omer.kollabstr;

import android.app.Application;

import com.boomer.omer.kollabstr.analytics.AnswersManager;
import com.boomer.omer.kollabstr.backend.BackEndlessCore;
import com.boomer.omer.kollabstr.core.ServiceManager;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Omer on 7/12/2016.
 */
public class KollabstrApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Answers(), new Crashlytics());

        BackEndlessCore.initialize(this);
        ServiceManager.initialize(this);
        AnswersManager.initialize(this);


    }
}
