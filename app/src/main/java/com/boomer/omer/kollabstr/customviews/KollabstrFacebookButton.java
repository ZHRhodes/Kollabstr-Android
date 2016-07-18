package com.boomer.omer.kollabstr.customviews;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.boomer.omer.kollabstr.R;

import com.boomer.omer.kollabstr.core.KollabstrActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.internal.LoginAuthorizationType;
import com.facebook.internal.Utility;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Omer on 7/17/2016.
 */
public class KollabstrFacebookButton extends Button {


    private Fragment mParentFragment;
    private LoginButtonProperties mProperties = new LoginButtonProperties();



    public KollabstrFacebookButton(Context context) {
        super(context);
        initButton();
    }

    public KollabstrFacebookButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initButton();
    }

    public KollabstrFacebookButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initButton();
    }

    private void initButton(){
        setBackgroundResource(R.drawable.facebook_login);
        setOnClickListener(getLoginClickListener());
    }

    private View.OnClickListener getLoginClickListener(){
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        };
    }

    private void performLogin(){

        if (LoginAuthorizationType.PUBLISH.equals(mProperties.authorizationType)) {
            if (KollabstrFacebookButton.this.getFragment() != null) {
                getLoginManager().logInWithPublishPermissions(
                        KollabstrFacebookButton.this.getFragment(),
                        mProperties.permissions);
            } else {
                getLoginManager().logInWithPublishPermissions(
                        KollabstrFacebookButton.this.getActivity(),
                        mProperties.permissions);
            }
        } else {
            if (KollabstrFacebookButton.this.getFragment() != null) {
                getLoginManager().logInWithReadPermissions(
                        KollabstrFacebookButton.this.getFragment(),
                        mProperties.permissions);
            } else {
                getLoginManager().logInWithReadPermissions(
                        KollabstrFacebookButton.this.getActivity(),
                        mProperties.permissions);
            }
        }
    }

    protected LoginManager getLoginManager() {
        LoginManager manager = LoginManager.getInstance();
        manager.setDefaultAudience(mProperties.defaultAudience);
        manager.setLoginBehavior(mProperties.loginBehavior);
        return manager;
    }

    private Activity getActivity(){
        if(getContext() instanceof KollabstrActivity){
            return (KollabstrActivity)getContext();
        }
        return null;
    }

    private void setParentFragment(Fragment fragment){
        mParentFragment = fragment;
    }

    private Fragment getFragment(){
        if(mParentFragment==null){return null;}
        return  mParentFragment;
    }

    public void registerCallback(
            final CallbackManager callbackManager,
            final FacebookCallback<LoginResult> callback) {
            getLoginManager().registerCallback(callbackManager, callback);
    }

    public void setReadPermissions(List<String> permissions) {
        mProperties.setReadPermissions(permissions);
    }


    static class LoginButtonProperties {
        private DefaultAudience defaultAudience = DefaultAudience.FRIENDS;
        private List<String> permissions = new ArrayList<>();
        private LoginAuthorizationType authorizationType = null;
        private LoginBehavior loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK;

        protected LoginButtonProperties(){permissions.add("email");}

        public void setDefaultAudience(DefaultAudience defaultAudience) {
            this.defaultAudience = defaultAudience;
        }

        public DefaultAudience getDefaultAudience() {
            return defaultAudience;
        }

        public void setReadPermissions(List<String> permissions) {

            if (LoginAuthorizationType.PUBLISH.equals(authorizationType)) {
                throw new UnsupportedOperationException("Cannot call setReadPermissions after " +
                        "setPublishPermissions has been called.");
            }
            this.permissions = permissions;
            authorizationType = LoginAuthorizationType.READ;
        }

        public void setPublishPermissions(List<String> permissions) {

            if (LoginAuthorizationType.READ.equals(authorizationType)) {
                throw new UnsupportedOperationException("Cannot call setPublishPermissions after " +
                        "setReadPermissions has been called.");
            }
            if (Utility.isNullOrEmpty(permissions)) {
                throw new IllegalArgumentException(
                        "Permissions for publish actions cannot be null or empty.");
            }
            this.permissions = permissions;
            authorizationType = LoginAuthorizationType.PUBLISH;
        }

        List<String> getPermissions() {
            return permissions;
        }

        public void clearPermissions() {
            permissions = null;
            authorizationType = null;
        }

        public void setLoginBehavior(LoginBehavior loginBehavior) {
            this.loginBehavior = loginBehavior;
        }

        public LoginBehavior getLoginBehavior() {
            return loginBehavior;
        }
    }





}
