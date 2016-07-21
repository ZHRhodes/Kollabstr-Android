package com.boomer.omer.kollabstr.backend.twitteroauth;

import android.content.Context;

import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;

/**
 * Created by Omer on 7/20/2016.
 */
public class KollabstrTwitterApiClient extends TwitterApiClient {

    /**
     * Must be instantiated after {@link TwitterCore} has been
     * initialized via {@link Fabric#with(Context, Kit[])}.
     *
     * @param session Session to be used to create the API calls.
     * @throws IllegalArgumentException if TwitterSession argument is null
     */
    public KollabstrTwitterApiClient(Session session) {
        super(session);
    }

}
