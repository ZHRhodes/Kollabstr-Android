package com.boomer.omer.kollabstr.analytics;


import android.content.Context;

import com.boomer.omer.kollabstr.R;
import com.boomer.omer.kollabstr.core.ResourceManager;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.answers.LoginEvent;


/**
 * Created by Omer on 7/15/2016.
 */
public class AnswersManager {

    private static final String TAG = AnswersManager.class.getName();

    private static final String APP_VERSION        = "App Version";

    private static boolean sIsInitialized = false;

    public static void initialize(Context context){
        if(context!=null){
            sIsInitialized = true;
            Answers.getInstance().logCustom(
                    new CustomEvent("App Launch").putCustomAttribute(APP_VERSION, ResourceManager.getInstance().getString(R.string.app_version)));
        }

    }

    public static final String METHOD_BACKENDLESS = "Backendless";
    public static final String METHOD_FACEBOOK    = "Facebook";
    public static final String METHOD_TWITTER     = "Twitter";

    public static void reportContentView(TrackableView trackable){
        Answers.getInstance().logContentView(new ContentViewEvent()
        .putContentName(trackable.getTag())
        .putContentType(trackable.getType()));
    }

    public static void reportLogin(String method){
        switch (method){
            case METHOD_BACKENDLESS:
                Answers.getInstance().logLogin(new LoginEvent()
                        .putMethod(METHOD_BACKENDLESS)
                        .putSuccess(true));
                break;

            case METHOD_FACEBOOK:
                Answers.getInstance().logLogin(new LoginEvent()
                .putMethod(METHOD_FACEBOOK)
                .putSuccess(true));
                break;
        }
    }

    public interface TrackableView {
        String TypeActivity = "Activity";
        String TypeFragment = "Fragment";
        String getTag();
        String getType();
    }

}
