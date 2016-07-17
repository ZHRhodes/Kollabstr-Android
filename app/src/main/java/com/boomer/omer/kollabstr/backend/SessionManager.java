package com.boomer.omer.kollabstr.backend;

import android.content.Context;
import android.os.Bundle;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.boomer.omer.kollabstr.core.ComponentBus;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Omer on 7/16/2016.
 */
public class SessionManager implements ComponentBus.Listener{

    private static final String TWITTER_KEY = "0fARaCyfrLoOpA4U2rJavUzqQ";
    private static final String TWITTER_SECRET = "YmqxSyK3rdEqTeAy0QvCm0c8Y82QvysgOrq5VzUU5lCRDrdtnI";

    private static final String DEFAULT_PASSWORD = "!asd1BJFSji6c#_A";

    public static final String TAG = SessionManager.class.getSimpleName();

    public static final String ACTION = "loginAction";
    public static final String ACTION_RESULT = "actionResult";

    private static SessionManager sSessionManager;
    private static boolean isInitialized = false;

    public static void initialize(Context context){
        if(!isInitialized) {
            sSessionManager = new SessionManager(context);
            isInitialized = true;
            TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
            Fabric.with(context, new Answers(), new Crashlytics(), new Twitter(authConfig));

        }else{
            throw new RuntimeException(TAG + " has already been initialized.");
        }
    }

    public static SessionManager getInstance(){
        if(isInitialized){return sSessionManager;}
        throw new RuntimeException(TAG + " has not been initialized.");
    }

    public static final String LOGIN_TYPE = "type";

    public static final String THROUGH_BACKENDLESS = "backendless";
    public static final String THROUGH_FACEBOOK    = "facebook";
    public static final String THROUGH_TWITTER     = "twitter";

    private ComponentBus mComponentBus;
    private Context mContext;

    private KollabstrSession mCurrentSession;

    private SessionManager(Context context){
        mComponentBus = ComponentBus.getsInstance();
        mComponentBus.subscribeToComponent(ACTION_RESULT,this);
        mContext = context;
        mCurrentSession = null;
    }

    public void loginWithBackendless(String email,String password){
        Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {

            @Override
            public void handleResponse(BackendlessUser response) {
                 mComponentBus.sendMessageToListeners(SessionManager.ACTION,null);

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });



    }

    public void receiveFacebookLoginResult(LoginResult loginResult){
        mComponentBus.sendMessageToListeners(ACTION,null);
    }

    public void receiveFacebookLoginError(FacebookException error){

    }


    @Override
    public void receiveMessage(Bundle bundle) {

    }

    @Override
    public void receiveMessage(Object object) {

    }

    private KollabstrSession getmCurrentSession(){
        return mCurrentSession;
    }

    private void createSession(String name,String userId,LoginType method){
        mCurrentSession = new KollabstrSession(name,userId,method);
    }

    private class KollabstrSession{
        String username;
        String userId;
        LoginType method;
        KollabstrSession(String name,String userId,LoginType method){
            this.username = name;
            this.userId = userId;
            this.method = method;
        }
    }


}
