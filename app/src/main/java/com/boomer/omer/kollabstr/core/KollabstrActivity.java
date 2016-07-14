package com.boomer.omer.kollabstr.core;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Omer on 7/12/2016.
 */
public abstract class KollabstrActivity extends Activity implements ComponentBus.Listener{

    private ComponentBus mComponentBus;
    private ServiceManager mServiceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mComponentBus = ComponentBus.getsInstance();
        mServiceManager = ServiceManager.getInstance();
    }

    protected ComponentBus getComponentBus(){
        return mComponentBus;
    }

    protected ServiceManager getServiceManager(){
        return mServiceManager;
    }

}
