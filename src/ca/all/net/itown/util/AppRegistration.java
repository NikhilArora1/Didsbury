package ca.all.net.itown.util;

import java.io.IOException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import ca.all.net.itown.didsbury.R;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class AppRegistration {
	
	private static String TAG = AppRegistration.class.getName();
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private final Activity activity;
	private GoogleCloudMessaging gcm;
    private String status, regid;
    private static final String PROPERTY_REG_ID ="registrationId";
    private static String  SENDER_ID;
    private String registrationIdValue;

	public AppRegistration(Activity currentActivity) {
		activity = currentActivity;
		SENDER_ID = currentActivity.getString(R.string.Project_Number);
	}
	
	public String getRegistationId() {
		if (checkPlayServices()) {
			System.out.println("Google Play Service - Working");
			gcm = GoogleCloudMessaging.getInstance(activity);
			regid = getRegistrationId();

			if (regid.isEmpty()) {
				registerInBackground();
			}
		} else {
			Log.i(TAG, "No valid Google Play Services APK found.");
            Toast.makeText(activity, "No valid Google Play Services APK found.", Toast.LENGTH_LONG).show();
		}
		return registrationIdValue;
	} 
	
	/**
     * Registers the application with GCM servers asynchronously.
     * Stores the registration ID and the app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(activity);
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    // You should send the registration ID to your server over HTTP, so it
                    // can use GCM/HTTP or CCS to send messages to your app.
                    registrationIdValue = regid;
                    //Storing registration id into shared preference
                    final SharedPreferences prefs = CommonUtils.getGcmPreferences(activity);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("registrationId", registrationIdValue);
			        editor.commit();
                } catch (IOException ex) {
                	ex.printStackTrace();
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                System.out.println("This is the registration key" + msg);
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
            	Log.i(TAG, "Registration Id recieved = " + registrationIdValue);
            }
        }.execute(null, null, null);
    }
	 /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * If result is empty, the app needs to register.
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private String getRegistrationId() {
    	final SharedPreferences prefs = CommonUtils.getGcmPreferences(activity);
        registrationIdValue = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationIdValue.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
            // Check if app was updated; if so, it must clear the registration ID
            // since the existing regID is not guaranteed to work with the new
            // app version.
            int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
            int currentVersion = getAppVersion(activity);
            if (registeredVersion != currentVersion) {
            	SharedPreferences.Editor editor = prefs.edit();
            	editor.putInt(PROPERTY_APP_VERSION, currentVersion);
            	editor.commit();
            	Log.i(TAG, "App version changed.");
                return "";
            }
            return registrationIdValue;
    }
    
    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Activity activity) {
        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
	
	/**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    public boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                status = "Sorry! Your device is not supported.";
                Toast.makeText(activity, status, Toast.LENGTH_LONG).show();
            }
            return false;
        }
        return true;
    }
}
