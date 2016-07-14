package com.boomer.omer.kollabstr.core;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Omer on 7/13/2016.
 */
public class ServiceManager {

    private static final String TAG = ServiceManager.class.getName();

    private static ServiceManager sServiceManager;
    private static Context sContext;
    private static boolean isInitialized = false;

    public static void initialize(Context context){
        sContext = context;
        isInitialized = true;
    }

    public static ServiceManager getInstance(){
        if(!isInitialized){throw new RuntimeException(TAG + " has not been initialized.");}
        if(sServiceManager == null){
            sServiceManager = new ServiceManager();
        }

        return  sServiceManager;
    }

    private ServiceManager(){}

    public void startService(Class<? extends KollabstrIntentService> serviceClass){
        sContext.startService(new Intent(sContext,serviceClass));
    }

    public void startService(Class<? extends KollabstrIntentService> serviceClass,Bundle extra){
        Intent intent = new Intent(sContext,serviceClass);
        intent.putExtra("extra",extra);
        sContext.startService(intent);
    }
}
