package ca.all.net.itown.didsbury;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import ca.all.net.itown.adapter.GillamBinderData;
import ca.all.net.itown.beans.EventsList;
import ca.all.net.itown.beans.Topic;
import ca.all.net.itown.util.CommonUtils;
import ca.all.net.itown.util.XmlMapper;

/**
 * Main Activity, for home page view
 * 
 * @author Ashwini
 */
public class DidsburyActivity extends Activity {

	// XML node keys
	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";
	static final String KEY_ICON = "icon";
	static final String KEY_COLOR = "color";
	static final String FOOTER = "footer";
	static final String HEADER = "header";
	static String notificationMessage = null;
	// List items
	ListView list;
	List<HashMap<String, String>> eventDataCollection;
	EventsList eventsList;
	GillamBinderData bindingData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		if (notificationMessage != null) {
			getIntent().removeExtra("notificationMessage");
			Log.i("********Main Message**********", notificationMessage);
			AlertDialog.Builder notificationDialog = new AlertDialog.Builder(
					this);
			notificationDialog.setTitle("Notification Alert");
			notificationDialog.setMessage(notificationMessage);
			notificationDialog.setNeutralButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							notificationMessage = null;
						}
					});
			notificationDialog.show();
		}
		
		
		try {

			// Implementing threading for downloading the data from the internet
			AsyncTaskAdapter asyncTask = new AsyncTaskAdapter();
			asyncTask.execute();

		} catch (Exception ex) {
			Log.e("Error", "Loading exception");
		}
	}

	private class AsyncTaskAdapter extends AsyncTask<String, Void, String> {
		// private String Content;
		private ProgressDialog Dialog;
		String response = "";

		@Override
		protected void onPreExecute() {
			Dialog = new ProgressDialog(DidsburyActivity.this);
			Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			Dialog.setMessage("Loading data...");
			Dialog.setCancelable(true);
			Dialog.show();
		}

		@Override
        protected String doInBackground(String... urls) {
                 try {
                	 
				BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("eventlist.xml")));

				StringBuilder sb = new StringBuilder();
             	String line;
             	while ((line = br.readLine()) != null) {
             		sb.append(line);
             	}
               XmlMapper mapper = new XmlMapper();
         	   eventsList= (EventsList)mapper.mapData(EventsList.class, sb.toString());
               response ="Success";
               } catch (Exception e) {
                 response ="Failed";
                 Log.e("Error", e.getLocalizedMessage());
               }
               return response;
        }

		@Override
		protected void onPostExecute(String result) {
			Dialog.dismiss();
			Dialog = null;
			if (eventsList != null && eventsList.getTopic() != null
					&& !eventsList.getTopic().isEmpty()
					&& response.equalsIgnoreCase("Success")) {
				HashMap<String, String> topicMap = null;
				eventDataCollection = new ArrayList<HashMap<String, String>>();
				for (Topic topic : eventsList.getTopic()) {
					topicMap = new HashMap<String, String>();
					topicMap.put(KEY_ID, topic.getId().trim());
					topicMap.put(KEY_NAME, topic.getName().trim());
					topicMap.put(KEY_ICON, topic.getIcon().trim());
					topicMap.put(KEY_COLOR, topic.getColor().trim());
					// Add to the Arraylist
					eventDataCollection.add(topicMap);
				}
				bindingData = new GillamBinderData(DidsburyActivity.this,
						eventDataCollection);
				list = (ListView) findViewById(R.id.list);

				ImageView topImage = (ImageView) findViewById(R.id.imageViewTop);
				String uri = "drawable/" + HEADER;
				int imageResource = getApplicationContext().getResources()
						.getIdentifier(uri, null,
								getApplicationContext().getPackageName());
				Drawable image = getResources().getDrawable(imageResource);
				topImage.setImageDrawable(image);

				ImageView bottomImage = (ImageView) findViewById(R.id.imageViewBottom);
				String uriFooter = "drawable/" + FOOTER;
				int imageResourceHeader = getApplicationContext()
						.getResources().getIdentifier(uriFooter, null,
								getApplicationContext().getPackageName());
				Drawable imageHeader = getResources().getDrawable(
						imageResourceHeader);
				bottomImage.setImageDrawable(imageHeader);
				bottomImage.setOnClickListener(new OnClickListener() {
				    @Override
				    public void onClick(View v) {
				    	Uri uriUrl = Uri.parse("http://all-net.ca");
				        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
				        startActivity(launchBrowser);
				    }
				 });

				Log.i("BEFORE",
						"<<------------- Before SetAdapter-------------->>");
				list.setAdapter(bindingData);
				Log.i("AFTER",
						"<<------------- After SetAdapter-------------->>");

				// Click event for single list row
				list.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						Intent i = new Intent();
						// parameters
						i.putExtra("position", String.valueOf(position + 1));
						int eventID = Integer.parseInt(eventDataCollection.get(
								position).get(KEY_ID));

						switch (eventID) {
						case 1:
							i.setClass(DidsburyActivity.this,
									LocalNoticeActivity.class);
							break;
						case 2:
							i.setClass(DidsburyActivity.this,
									UpcomingEventActivity.class);
							break;
						case 3:
							i.setClass(DidsburyActivity.this,
									MapCategoryActivity.class);
							break;
						case 4:
							i.setClass(DidsburyActivity.this,
									BusinessDirectoryActivity.class);
							break;
						case 5:
							i.setClass(DidsburyActivity.this,
									ContactActivity.class);
							break;
						default:
							break;
						}
						// start the sample activity
						startActivity(i);
					}
				});
				//check if the user is logged in for first time and if yes, ask for the notification service
				final SharedPreferences prefs = CommonUtils.getGcmPreferences(DidsburyActivity.this);
				boolean loggedInFirstTime = prefs.getBoolean("loggedInFirstTime", true);
				String registrationStatus = prefs.getString("registrationStatus", "Failed");
				if(loggedInFirstTime && "Failed".equalsIgnoreCase(registrationStatus)) {
					CommonUtils.subscribeNotification(DidsburyActivity.this, false);
				}
				
			} else {
				Toast.makeText(DidsburyActivity.this,
						"Sorry Unable to retrive data. Please try again later",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	// OnBackPressed...

	@SuppressWarnings("deprecation")
	@Override
	public void onBackPressed() {
		AlertDialog alert_back = new AlertDialog.Builder(this).create();
		alert_back.setTitle("Quit?");
		alert_back.setMessage("Are you sure want to Quit?");

		alert_back.setButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		alert_back.setButton2("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				DidsburyActivity.this.finish();
			}
		});
		alert_back.show();
	}
}
