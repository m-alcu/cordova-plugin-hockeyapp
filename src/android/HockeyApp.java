package com.zengularity.cordova.hockeyapp;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import android.widget.Toast;

import static net.hockeyapp.android.ExceptionHandler.saveException;
import net.hockeyapp.android.FeedbackManager;
import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.CrashManagerListener;

public class HockeyApp extends CordovaPlugin {

    public static boolean initialized = false;
    public static String token;
    public static String description;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
        if (action.equals("start")) {
            token = args.optString(0);
            CrashManager.register(cordova.getActivity(), token, null);
            initialized = true;
            callbackContext.success();
            return true;
        } else if(action.equals("feedback")) {
			token = args.optString(0);
			FeedbackManager.register(cordova.getActivity(), token, null);
			cordova.getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					FeedbackManager.showFeedbackActivity(cordova.getActivity());
				}
			});
			callbackContext.success();
			return true;
			
        } else if(action.equals("saveException")) {
            description = args.optString(0);
            if(initialized) {
			
			Toast toast = Toast.makeText(cordova.getActivity(), "problem", Toast.LENGTH_SHORT);
			toast.show();
			
			cordova.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
						Exception e = new Exception("Send problem");
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
