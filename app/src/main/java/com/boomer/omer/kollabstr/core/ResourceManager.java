package com.boomer.omer.kollabstr.core;

import android.content.Context;

/**
 * Created by Omer on 7/12/2016.
 */
public class ResourceManager {
    public static String getString(Context context,int stringId){
        return context.getResources().getString(stringId);
    }
}
