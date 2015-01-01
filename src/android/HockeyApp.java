package com.zengularity.cordova.hockeyapp;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;

import net.hockeyapp.android.FeedbackManager;
import net.hockeyapp.android.ExceptionHandler.saveException;

public class HockeyApp extends CordovaPlugin {

    public static boolean initialized = false;
    public static String token;
    public static String description;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
        if (action.equals("start")) {
            token = args.optString(0);
            FeedbackManager.register(cordova.getActivity(), token, null);
            initialized = true;
            callbackContext.success();
            return true;
        } else if(action.equals("feedback")) {
            if(initialized) {
                cordova.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FeedbackManager.showFeedbackActivity(cordova.getActivity());
                    }
                });
                callbackContext.success();
                return true;
            }
            else {
                callbackContext.error("cordova hockeyapp plugin not initialized, call start() first");
                return false;
            }
        } else if(action.equals("saveException")) {
            description = args.optString(0);
            if(initialized) {
                cordova.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Exception e = new Exception();
                        saveException(e, new CrashManagerListener() {
                            public String getDescription() {
                                return description;
                            }
                        });
                    }
                });
                callbackContext.success();
                return true;
            } else {
                callbackContext.error("cordova hockeyapp plugin not initialized, call start() first");
                return false;                
            }  
        }
        else {
            return false;
        }
    }

}
