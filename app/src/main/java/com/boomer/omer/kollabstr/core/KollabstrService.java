package com.boomer.omer.kollabstr.core;

import android.app.Service;
import android.content.Intent;

/**
 * Created by Omer on 7/17/2016.
 */
public abstract class KollabstrService extends Service implements ComponentBus.Listener {

    private ComponentBus mComponentBus;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {





        return Service.START_STICKY;
    }

    protected ComponentBus getComponentBus(){
        if(mComponentBus == null){
            mComponentBus = ComponentBus.getInstance();
        }
        return mComponentBus;
    }
}
