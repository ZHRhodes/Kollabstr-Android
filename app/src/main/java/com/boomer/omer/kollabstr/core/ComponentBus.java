package com.boomer.omer.kollabstr.core;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Omer on 7/13/2016.
 */
public class ComponentBus {

    private static ComponentBus sInstance;

    public static ComponentBus getInstance(){
        if(sInstance == null){
            sInstance = new ComponentBus();
        }
        return sInstance;
    }

    private  Handler mUIThreadHandler;

    private HashMap<String,List<ListenerWrapper>> mListeners;

    private ComponentBus(){
        mUIThreadHandler = new Handler(Looper.getMainLooper());
        mListeners       = new HashMap<>();
    }

    public void subscribeToComponent(String componentName,Listener listener){
        if(!mListeners.containsKey(componentName)){
            mListeners.put(componentName,new ArrayList<ListenerWrapper>());
        }
        mListeners.get(componentName).add(createListenerWrapper(listener));
    }

    public void sendMessageToListeners(String component, Bundle bundle){
         if(mListeners.containsKey(component)){
             for(ListenerWrapper listener : mListeners.get(component)){
                 if(listener.threadID == getUIThreadId() && Thread.currentThread().getId() == getUIThreadId()){
                    messageUIFromUI(listener,bundle);
                 }
                 if(listener.threadID == getUIThreadId() && Thread.currentThread().getId() != getUIThreadId()){
                     messageUIFromOther(listener,bundle);
                 }

             }
         }
    }

    private void messageUIFromUI(ListenerWrapper listener,Bundle bundle){
        if(listener.reference.get()!=null){
            listener.reference.get().receiveMessage(bundle);
        }
    }

    private void messageUIFromOther(final ListenerWrapper listener, final Bundle message){
        mUIThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if(listener.reference.get()!=null){
                    listener.reference.get().receiveMessage(message);
                }
            }
        });
    }

    public interface Listener{
        void receiveMessage(Bundle bundle);
        void receiveMessage(Object object);
    }



    private class ListenerWrapper{
        WeakReference<Listener> reference;
        long threadID;
        ListenerWrapper(long threadID,Listener listener){
            this.reference = new WeakReference<Listener>(listener);
            this.threadID = threadID;
        }
    }

    private ListenerWrapper createListenerWrapper(Listener listener){
        return new ListenerWrapper(Thread.currentThread().getId(),listener);
    }

    private long getUIThreadId(){
        return mUIThreadHandler.getLooper().getThread().getId();
    }

}
