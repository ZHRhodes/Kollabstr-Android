package com.boomer.omer.kollabstr.backend;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.boomer.omer.kollabstr.analytics.AnswersManager;
import com.boomer.omer.kollabstr.core.ComponentBus;
import com.boomer.omer.kollabstr.core.ResourceManager;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Omer on 7/16/2016.
 */
public class SessionManager implements ComponentBus.Listener{

    private static final String DEFAULT_PASSWORD = "!asd1BJFSji6c#_A";

    public static final String TAG = SessionManager.class.getSimpleName();

    public static final String LOGIN_SUCCESS = "loginSuccess";


    private static SessionManager sSessionManager;
    private static boolean isInitialized = false;



    public static void initialize(Context context){
        if(!isInitialized) {
            sSessionManager = new SessionManager(context);
            isInitialized = true;
            TwitterAuthConfig authConfig = new TwitterAuthConfig(ResourceManager.getInstance().getTwitterKey(), ResourceManager.getInstance().getTwitterSecret());
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

    public static final String THROUGH_BACKENDLESS = "Backendless";
    public static final String THROUGH_FACEBOOK    = "Facebook";
    public static final String THROUGH_TWITTER     = "Twitter";
    public static final String THROUGH_GOOGLE      = "Google";

    private ComponentBus mComponentBus;
    private Context mContext;

    private BackendlessUser mCurrentUser;



    private SessionManager(Context context){
        mComponentBus = ComponentBus.getInstance();
        mContext = context;

    }

    public void loginWithBackendless(String email,String password){
        Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {

            @Override
            public void handleResponse(BackendlessUser response) {
                 mComponentBus.sendMessageToListeners(SessionManager.LOGIN_SUCCESS,null);
                 mCurrentUser = response;
                 AnswersManager.reportLogin(THROUGH_BACKENDLESS);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });



    }

   public void loginWithFacebook(Activity activity,CallbackManager callbackManager){
       Map<String,String> propertyMapping = new HashMap<>();
       propertyMapping.put("email","email");
       propertyMapping.put("id","facebookid");
       propertyMapping.put("first_name","name");
       List<String> permissions = new ArrayList<>();
       permissions.add("email");
        Backendless.UserService.loginWithFacebookSdk(activity, propertyMapping, permissions, callbackManager, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                mComponentBus.sendMessageToListeners(SessionManager.LOGIN_SUCCESS,null);
                mCurrentUser = response;
                AnswersManager.reportLogin(THROUGH_FACEBOOK);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d(TAG,fault.toString());
            }
        });
   }

    public void loginWithTwitter(Activity activity){
        Map<String,String> propertyMapping = new HashMap<>();
        propertyMapping.put("id","twitterid");
        propertyMapping.put("name","name");
        Backendless.UserService.loginWithTwitter(activity, propertyMapping, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                mComponentBus.sendMessageToListeners(SessionManager.LOGIN_SUCCESS,null);
                mCurrentUser = response;
                AnswersManager.reportLogin(THROUGH_TWITTER);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    public void loginWithGoogle(){
      // Backendless.UserService.loginWithGooglePlusSdk()
    }

    public boolean isCurrentUserPresent(){
        return Backendless.UserService.CurrentUser() !=null;
    }

    public BackendlessUser getCurrentUser(){
        if(isCurrentUserPresent()){return mCurrentUser;}
        throw new BackendlessException(TAG + " has no user signed in");
    }


    @Override
    public void receiveMessage(Bundle bundle) {

    }

    @Override
    public void receiveMessage(Object object) {

    }






}
