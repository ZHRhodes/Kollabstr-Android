package com.boomer.omer.kollabstr.core;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import com.boomer.omer.kollabstr.analytics.AnswersManager;
import com.boomer.omer.kollabstr.backend.SessionManager;

/**
 * Created by Omer on 7/19/2016.
 */
public abstract class KollabstrFragment extends Fragment implements ComponentBus.Listener ,AnswersManager.TrackableView{

    private ComponentBus mComponentBus;
    private ServiceManager mServiceManager;
    private SessionManager mSessionManager;
    private ResourceManager mResourceManager;

    private KollabstrActivity mKollabstrActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof  KollabstrActivity){
            mKollabstrActivity = (KollabstrActivity)activity;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        AnswersManager.reportContentView(this);
    }

    protected KollabstrActivity getKollabstrActivity(){
        return mKollabstrActivity;
    }

    @Override
    public String getType() {
        return TypeFragment;
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
