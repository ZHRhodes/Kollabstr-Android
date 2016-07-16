package com.boomer.omer.kollabstr.analytics;


import android.content.Context;

import com.boomer.omer.kollabstr.R;
import com.boomer.omer.kollabstr.core.ResourceManager;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;

/**
 * Created by Omer on 7/15/2016.
 */
public class AnswersManager {

    private static final String TAG = AnswersManager.class.getName();

    private static final String DEVICE_TYPE        = "Device Type";
    private static final String DEVICE_MODEL       = "Device Model";
    private static final String DEVICE_OS          = "Operating System";
    private static final String APP_VERSION        = "App Version";

    public static final String TYPE_ACTIVITY       = "Activity";
    public static final String TYPE_FRAGMENT       = "Fragment";
    public static final String TYPE_DIALOG         = "Dialog";

    private static boolean sIsInitialized = false;

    public static void initialize(Context context){
        if(context!=null){
            sIsInitialized = true;
            Answers.getInstance().logCustom(
                    new CustomEvent("App Launch").putCustomAttribute(DEVICE_TYPE,android.os.Build.DEVICE)
                                                 .putCustomAttribute(DEVICE_MODEL,android.os.Build.MODEL)
                                                 .putCustomAttribute(DEVICE_OS,System.getProperty("os.version" + android.os.Build.VERSION.INCREMENTAL))
                                                 .putCustomAttribute(APP_VERSION, ResourceManager.getString(context, R.string.app_version)));
        }

    }

    public static AnswersManager track(Trackable trackable){
        if(!sIsInitialized){throw new RuntimeException(TAG + " has not been initialized.");}
        return new AnswersManager(trackable.getTag(),trackable.getType(),trackable.getID());
    }


    private String tag;
    private String type;
    private String id;

    private AnswersManager(String tag,String type,String id){
        this.tag  = tag;
        this.type = type;
        this.id   = id;
    }

    public void log(){
        switch (type){
            case TYPE_ACTIVITY:
            case TYPE_FRAGMENT:
            case TYPE_DIALOG:
                Answers.getInstance().logContentView(new ContentViewEvent()
                        .putContentName(tag)
                        .putContentType(type)
                        .putContentId(id));
                break;

            default:
                break;
        }
    }



    public interface Trackable{
        String getTag();
        String getType();
        String getID();
    }

}
