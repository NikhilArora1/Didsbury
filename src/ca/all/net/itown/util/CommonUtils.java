package ca.all.net.itown.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;
import ca.all.net.itown.didsbury.R;
import ca.all.net.itown.external.DataExchange;

public class CommonUtils {

	private static final String status_failed ="Failed";
	private static final String status_passed ="Passed";
	private static String TAG = CommonUtils.class.getName();
	
	// Check Internet Connection!!!
	public static boolean isNetworkAvailable(Activity context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static SharedPreferences getGcmPreferences(Activity context) {
	        return context.getSharedPreferences("MyDidsBuryPreferences", Context.MODE_PRIVATE);
	    }
	
	public static void subscribeNotification(final Activity context,final boolean logedFirstTime) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		// set title
		alertDialogBuilder.setTitle("Didsbury Subscribe Notification");

		// set dialog message
		alertDialogBuilder
				.setMessage("Do you want to recieve notification from Didsbury")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								AsyncTaskAdapter asyncTask = new AsyncTaskAdapter(context, logedFirstTime, "Yes");
								asyncTask.execute();
								
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						AsyncTaskAdapter asyncTask = new AsyncTaskAdapter(context, logedFirstTime, "No");
						asyncTask.execute();

					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		// show it
		alertDialog.show();
	}
	
	private static class AsyncTaskAdapter extends AsyncTask<String, Void, String> {
		// private String Content;
		private ProgressDialog Dialog;
		String response = "";
		private String notificationResponse,registrationId,registrationNeeded;
		private Activity context;
		boolean logedFirstTime;
		SharedPreferences.Editor editor;

		public AsyncTaskAdapter(final Activity contextActivity,final boolean logedFirstTime, final String allowRegistration) {
			this.logedFirstTime = logedFirstTime;
			this.context = contextActivity;
			this.registrationNeeded = allowRegistration;
		}

		@Override
		protected void onPreExecute() {
			Dialog = new ProgressDialog(context);
			Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			Dialog.setMessage("Loading data...");
			Dialog.setCancelable(true);
			Dialog.show();

		}

		@Override
		protected String doInBackground(String... urls) {
			try {
					final SharedPreferences prefs = CommonUtils.getGcmPreferences(context);
					editor = prefs.edit();
                    editor.putBoolean("loggedInFirstTime", logedFirstTime);
			        editor.commit();
					registrationId = prefs.getString("registrationId", "");
					if (registrationId != null && !registrationId.isEmpty()) {
						Log.i(TAG, "Registration Id recieved = " + registrationId);
						String params="pf=Android&dT=";
						if("Yes".equalsIgnoreCase(registrationNeeded)){
							params = "pf=Android&dT="+registrationId;
						} else if ("No".equalsIgnoreCase(registrationNeeded)) {
							params = "pf=Android&dT=null";
						}
						DataExchange dataExchange = new DataExchange();
						String baseUrl="";
						baseUrl = context.getString(R.string.notificationRegistration);
						Log.e("URL", baseUrl+params);
						notificationResponse = dataExchange.exchange(baseUrl+params);
						if (notificationResponse.contains("GUID") || notificationResponse.contains("Success")) {
							if(notificationResponse.contains("GUID")) {
								String guidVal[] = notificationResponse.split("</GUID>");
								Log.i(TAG, "SUCCESS GUID RECIEVED : " +guidVal[0]);
								editor.putString("GUID", guidVal[0]);
								editor.commit();
							}
							response = "Success";
						} else {
							response = "Failed";
						}
					} else {
						response = "Failed";
					}
				} catch (Exception e) {
					response = "Failed";
					Log.e("Error", e.getLocalizedMessage());
				}
			return response;
		}

		@Override
        protected void onPostExecute(String result) {
          Dialog.dismiss();
          Dialog = null;
                 if(response.equalsIgnoreCase("Success") && "Yes".equalsIgnoreCase(registrationNeeded)){
                	 editor.putString("registrationStatus", status_passed);
                	 Toast.makeText(context, "Notification Registration Successfull", Toast.LENGTH_LONG).show();
                 } else if (response.equalsIgnoreCase("Failed")){
                	 Toast.makeText(context, "There was an error while registering for notification!!. Please try again later", Toast.LENGTH_LONG).show();
                	 editor.putString("registrationStatus", status_failed);
                 } else if (response.equalsIgnoreCase("Success") && "No".equalsIgnoreCase(registrationNeeded)) {
                     /*Do nothing as the user has asked to not send the notification*/
               }  else {
                       Toast.makeText(context, "There was an error. Please try again later", Toast.LENGTH_LONG).show();
                       editor.putString("registrationStatus", status_failed);
                 }
           editor.commit();
        }
	}
}
