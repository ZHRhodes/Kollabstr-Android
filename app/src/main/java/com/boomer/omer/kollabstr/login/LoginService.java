package com.boomer.omer.kollabstr.login;

import android.content.Intent;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.boomer.omer.kollabstr.core.KollabstrIntentService;

/**
 * Created by Omer on 7/13/2016.
 */
public class LoginService extends KollabstrIntentService {

    private static final String TAG = LoginService.class.getName();

    public static final String E_MAIL = "email";
    public static final String PASSWORD = "password";

    public LoginService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

            String email    = intent.getBundleExtra("extra").getString(E_MAIL);
            String password = intent.getBundleExtra("extra").getString(PASSWORD);
            Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser response) {
                    Log.d(TAG,response.toString());
                    getComponentBus().sendMessageToListeners("loginService",null);
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.d(TAG,fault.toString());
                }
            });
    }
}
