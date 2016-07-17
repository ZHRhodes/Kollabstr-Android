package com.boomer.omer.kollabstr.backend;

import android.content.Context;

import com.backendless.Backendless;
import com.boomer.omer.kollabstr.R;
import com.boomer.omer.kollabstr.core.ResourceManager;

/**
 * Created by Omer on 7/12/2016.
 */
public class BackEndlessCore {

    private final static String TAG = BackEndlessCore.class.getName();

    private static BackEndlessCore sInstance;
    private static Context sContext;
    private static boolean isInitialized = false;

    public static void initialize(Context context){
        if(!isInitialized){
            isInitialized = true;
            sContext = context;
            sInstance = new BackEndlessCore();
            Backendless.initApp(context,ResourceManager.getInstance().getBackEndlessAppID(),ResourceManager.getInstance().getBackendlessAppSecret(), ResourceManager.getInstance().getString(R.string.app_version));
        }
    }

    public static BackEndlessCore getsInstance(){
        if(!isInitialized){throw new RuntimeException(TAG + "" + " has not been initialized.");}
        return  sInstance;
    }

    private BackEndlessCore(){};

}
