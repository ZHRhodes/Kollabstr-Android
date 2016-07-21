package com.boomer.omer.kollabstr.core;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.boomer.omer.kollabstr.analytics.AnswersManager;
import com.boomer.omer.kollabstr.backend.SessionManager;

import java.util.Stack;

/**
 * Created by Omer on 7/12/2016.
 */
public abstract class KollabstrActivity extends Activity implements ComponentBus.Listener,AnswersManager.TrackableView {

    private ComponentBus mComponentBus;
    private ServiceManager mServiceManager;
    private SessionManager mSessionManager;
    private ResourceManager mResourceManager;

    private Stack<Class<? extends KollabstrFragment>> mFragmentBackStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnswersManager.reportContentView(this);
    }

    protected void addToFragmentBackStack(Class<? extends KollabstrFragment> fragmentClass){
        getFragmentBackStack().push(fragmentClass);
    }

    protected void clearFragmentBackStack(){
        mFragmentBackStack.clear();
    }

    protected Stack<Class<? extends KollabstrFragment>> getFragmentBackStack(){
        if (mFragmentBackStack == null){
            mFragmentBackStack = new Stack<>();
        }
        return mFragmentBackStack;
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

    @Override
    public String getType() {
        return TypeActivity;
    }


}
