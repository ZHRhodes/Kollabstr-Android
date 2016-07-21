package com.boomer.omer.kollabstr.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.boomer.omer.kollabstr.R;
import com.boomer.omer.kollabstr.backend.SessionManager;
import com.boomer.omer.kollabstr.backend.objects.Users;
import com.boomer.omer.kollabstr.backend.twitteroauth.TwitterManager;
import com.boomer.omer.kollabstr.core.KollabstrActivity;
import com.boomer.omer.kollabstr.core.KollabstrFragment;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


/**
 * Created by Omer on 7/17/2016.
 */
public class SetupProfileActivity extends KollabstrActivity {

    private static final String TAG = SetupProfileActivity.class.getSimpleName();

    private Users mUserBeingWorkedOn;

    private KollabstrFragment mCurrentFragment;

    private FrameLayout mFragmentContainer;

    private TwitterManager mTwitterManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getTwitterManager().onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_profile);

        mFragmentContainer = (FrameLayout)findViewById(R.id.setup_profile_fragment_container);

        //if(fromScratch()){getCurrentUser().setProfile(Profile.createEmpty());} ///ADJUST
       goToFragment(EditProfileFragment.class);



        getComponentBus().subscribeToComponent(SessionManager.SESSION_UPDATED_EVENT,this);
    }

    @Override
    public void receiveMessage(Bundle bundle) {
        updateCurrentUser();
    }

    private void showFragment(Class<? extends KollabstrFragment> fragment){
        Constructor<?> constructor = null;
        Object instance = null;
        try {
            constructor = fragment.getConstructor();
            instance    = constructor.newInstance();
        } catch (NoSuchMethodException e) {e.printStackTrace();} catch (IllegalAccessException e) {
            e.printStackTrace();} catch (InstantiationException e) {e.printStackTrace();} catch (InvocationTargetException e) {e.printStackTrace();
        } finally {
            KollabstrFragment kollabstrFragment = (KollabstrFragment)instance;
            mFragmentContainer.removeAllViews();
            getFragmentManager().beginTransaction().add(R.id.setup_profile_fragment_container,kollabstrFragment).commit();
            mCurrentFragment = kollabstrFragment;
        }
    }

    public void goToFragment(Class<? extends KollabstrFragment> fragment){
        showFragment(fragment);
        getFragmentBackStack().push(fragment);
    }



    public void goToPreviousFragment(){
        if(getFragmentBackStack().size() > 1){
            if(mCurrentFragment.getClass().equals(getFragmentBackStack().peek())){getFragmentBackStack().pop();}
            showFragment(getFragmentBackStack().pop());
            //getFragmentManager().popBackStack();
        }else{
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG,"Back Event");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
           goToPreviousFragment();
            return true;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void receiveMessage(Object object) {

    }

    @Override
    public String getReportTag() {
        return TAG;
    }


    private void updateCurrentUser(){
        mUserBeingWorkedOn = getSessionManager().getCurrentUser();
    }

    private Users getCurrentUser(){
        if(mUserBeingWorkedOn == null){
                mUserBeingWorkedOn = getSessionManager().getCurrentUser();}
        return mUserBeingWorkedOn;
    }

    private boolean fromScratch(){
        return getSessionManager().getCurrentUser().getIsProfileSet()==null;
    }

    private KollabstrFragment getCurrentFragment(){
        return  mCurrentFragment;
    }

    public void doTwitterLogin(){
        getTwitterManager().logNewSession();
    }

    private TwitterManager getTwitterManager(){
        if(mTwitterManager==null){
            mTwitterManager = new TwitterManager(this);
        }
        return  mTwitterManager;
    }

}
