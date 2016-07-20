package com.boomer.omer.kollabstr.backend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.boomer.omer.kollabstr.analytics.AnswersManager;
import com.boomer.omer.kollabstr.backend.objects.Users;
import com.boomer.omer.kollabstr.core.ComponentBus;
import com.boomer.omer.kollabstr.core.KollabstrIntentService;
import com.boomer.omer.kollabstr.core.ResourceManager;
import com.boomer.omer.kollabstr.core.ServiceManager;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.facebook.CallbackManager;
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

    public static final String LOGIN_SUCCESS_EVENT = "loginSuccess";
    public static final String SESSION_UPDATED_EVENT = "sessionSuccess";


    public static final String OBJECT_ID_KEY = "objectId";
    public static final String SESSION_DATA_KEY = "sessionData";

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
    private ServiceManager mServiceManager;

    private Context mContext;

    private Users mCurrentUsers;



    private SessionManager(Context context){
        mCurrentUsers = null;
        mComponentBus = ComponentBus.getInstance();
        mComponentBus.subscribeToComponent(SESSION_UPDATED_EVENT,this);
        mComponentBus.subscribeToComponent(LOGIN_SUCCESS_EVENT,this);
        mContext = context;

    }

    public void loginWithBackendless(String email,String password){
        Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {

            @Override
            public void handleResponse(BackendlessUser response) {
                 getComponentBus().sendMessageToListeners(SessionManager.LOGIN_SUCCESS_EVENT,null);
                 AnswersManager.reportLogin(THROUGH_BACKENDLESS);
                 changeSession(response);
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
                getComponentBus().sendMessageToListeners(SessionManager.LOGIN_SUCCESS_EVENT,null);
                AnswersManager.reportLogin(THROUGH_FACEBOOK);
                changeSession(response);
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
                getComponentBus().sendMessageToListeners(SessionManager.LOGIN_SUCCESS_EVENT,null);
                AnswersManager.reportLogin(THROUGH_TWITTER);
                changeSession(response);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    public void loginWithGoogle(){
      // Backendless.UserService.loginWithGooglePlusSdk()
        //Todo: Implement this
    }



    public boolean isCurrentUserPresent(){
        return mCurrentUsers != null;
    }

    private void createSession(BackendlessUser user){
        Bundle data = new Bundle();
        data.putParcelable(SESSION_DATA_KEY,mCurrentUsers);
        getComponentBus().sendMessageToListeners(SESSION_UPDATED_EVENT,data);
    }

    private void changeSession(BackendlessUser backendlessUser){
        Bundle data = new Bundle();
        data.putString(OBJECT_ID_KEY,backendlessUser.getObjectId());
        getServiceManager().startService(SessionService.class,data);
    }

    public void updateUser(Users user){
        Backendless.Persistence.save(user, new AsyncCallback<Users>() {
            @Override
            public void handleResponse(Users response) {
                Bundle data = new Bundle();
                data.putParcelable(SESSION_DATA_KEY,response);
                getComponentBus().sendMessageToListeners(SESSION_UPDATED_EVENT,data);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    public static class SessionService extends KollabstrIntentService{


        public SessionService() {
            super(TAG);
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            Bundle data = intent.getBundleExtra(EXTRA_KEY);
            String objectId = data.getString(OBJECT_ID_KEY);
            Users users = Backendless.Persistence.of(Users.class).findById(objectId);
            Bundle sessionData = new Bundle();
            sessionData.putParcelable(SESSION_DATA_KEY, users);
            this.getComponentBus().sendMessageToListeners(SESSION_UPDATED_EVENT,sessionData);
        }
    }

    public Users getCurrentUser(){
        if(isCurrentUserPresent()){return mCurrentUsers;}
        throw new BackendlessException(TAG + " has no user signed in");
    }

    private ComponentBus getComponentBus(){
        if(mComponentBus == null){
            mComponentBus = ComponentBus.getInstance();
        }
        return mComponentBus;
    }

    private ServiceManager getServiceManager(){
        if(mServiceManager == null){
            mServiceManager = ServiceManager.getInstance();
        }
        return mServiceManager;
    }


    @Override
    public void receiveMessage(Bundle bundle) {
        if(bundle == null){return;}
        if(bundle.containsKey(SESSION_DATA_KEY)){
            mCurrentUsers = bundle.getParcelable(SESSION_DATA_KEY);
        }
    }

    @Override
    public void receiveMessage(Object object) {

    }


}
