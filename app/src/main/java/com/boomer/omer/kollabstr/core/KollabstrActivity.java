package com.boomer.omer.kollabstr.core;

import android.app.Activity;
import android.os.Bundle;

import com.boomer.omer.kollabstr.analytics.AnswersManager;
import com.boomer.omer.kollabstr.backend.SessionManager;

/**
 * Created by Omer on 7/12/2016.
 */
public abstract class KollabstrActivity extends Activity implements ComponentBus.Listener,AnswersManager.TrackableView {

    private ComponentBus mComponentBus;
    private ServiceManager mServiceManager;
    private SessionManager mSessionManager;
    private ResourceManager mResourceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnswersManager.reportContentView(this);
    }


    protected ComponentBus getComponentBus(){
        if(mComponentBus == null){
            mComponentBus = ComponentBus.getInstance();
        }
        return mComponentBus;
    }

    protected ServiceManager getServiceManager(){
        if(mServiceManager == null){
            mServiceManager = ServiceManager.getInstance();
        }
        return mServiceManager;
    }

    protected SessionManager getSessionManager(){
        if(mSessionManager == null){
            mSessionManager = SessionManager.getInstance();
        }
        return mSessionManager;
    }

    protected ResourceManager getResourceManager(){
        if(mResourceManager == null){
            mResourceManager = ResourceManager.getInstance();
        }
        return mResourceManager;
    }

}
