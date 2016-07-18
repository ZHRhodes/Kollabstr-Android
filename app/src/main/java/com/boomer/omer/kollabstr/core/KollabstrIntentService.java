package com.boomer.omer.kollabstr.core;

import android.app.IntentService;

/**
 * Created by Omer on 7/13/2016.
 */
public abstract class KollabstrIntentService extends IntentService {

    private ComponentBus mComponentBus;

    protected KollabstrIntentService(String name) {
        super(name);
    }

    protected ComponentBus getComponentBus(){
        if(mComponentBus == null){
            mComponentBus = ComponentBus.getInstance();
        }
        return mComponentBus;
    }

}
