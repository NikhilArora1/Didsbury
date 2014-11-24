package ca.all.net.itown.didsbury;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import ca.all.net.itown.adapter.LocalNoticeBinderData;
import ca.all.net.itown.beans.Notices;
import ca.all.net.itown.beans.UpcomingNotices;
import ca.all.net.itown.external.DataExchange;
import ca.all.net.itown.util.XmlMapper;

/**
 * This class is to display the list of Upcoming Activity
 * @author Ashwini
 */
public class LocalNoticeActivity extends Activity implements TextWatcher, OnItemClickListener {
	
	// Events XML node keys
    static final String KEY_ID = "noticeID";
    static final String DATE = "date";
    static final String NOTICE_TITLE = "noticeTitle";
    static final String NOTICE_DESC = "noticeDescription";
    static final String NOTICE= "notice";
    static final String HOME_EVENT_ICON="home";
    // List items 
    ListView list;
    String searchString;
    EditText mySearch;
    List<HashMap<String,String>> localNoticeDataCollection;

    UpcomingNotices noticeList;
    LocalNoticeBinderData bindingData;
    List<HashMap<String,String>> filterArray = new ArrayList<HashMap<String,String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.events_list);
		
		try{
			
			list = (ListView) findViewById(R.id.topicList);
			list.setOnItemClickListener(this);
			mySearch = (EditText) findViewById(R.id.input_search_query);
			mySearch.addTextChangedListener(this);
			
			// XML Parsing Using AsyncTask...
			if (isNetworkAvailable()) {
				// Implementing threading for downloading the data from the
				// internet
				AsyncTaskAdapter asyncTask = new AsyncTaskAdapter();
				asyncTask.execute();
			} else {
				Toast.makeText(this, "No Netwrok Connection!!!",
						Toast.LENGTH_SHORT).show();
				this.finish();
			}
	        
	      //Setting an image
			ImageView imageView = (ImageView)findViewById(R.id.back_image);
		    String uri = "drawable/"+ HOME_EVENT_ICON + "_back";
		    int imageResource = getApplicationContext().getResources().getIdentifier(uri, null, getApplicationContext().getPackageName());
		    Drawable image = getResources().getDrawable(imageResource);
			imageView.setImageDrawable(image);
			
			RelativeLayout imageViewHeader = (RelativeLayout)findViewById(R.id.header_image);
			String uriHeader = "drawable/"+ NOTICE + "_header";
    		int imageResourceHeader = getApplicationContext().getResources().getIdentifier(uriHeader, null, getApplicationContext().getPackageName());
    		Drawable imageHeader = getResources().getDrawable(imageResourceHeader);
    		imageViewHeader.setBackgroundDrawable(imageHeader);
    		
    		//Click Event for going back to home page
			 //Setting the onclick listener
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					if (view.getId() == R.id.back_image) {
						onBackPressed();
				      //  moveTaskToBack(true);
				    }
				}
			});
    		
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
	               Dialog = new ProgressDialog(LocalNoticeActivity.this);
	               Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	               Dialog.setMessage("Loading data...");
	               Dialog.setCancelable(true);
	               Dialog.show();
	       
	        }
	        @Override
	        protected String doInBackground(String... urls) {
	                 try {
	                	 DataExchange dataExchange = new DataExchange();
	                	 String resultData = dataExchange.exchange(LocalNoticeActivity.this.getString(R.string.localNoticeUrl));
	                		XmlMapper mapper = new XmlMapper();
	                		noticeList= (UpcomingNotices)mapper.mapData(UpcomingNotices.class, resultData);
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
	                 if(noticeList != null && noticeList.getNotices() !=null && !noticeList.getNotices().isEmpty() && response.equalsIgnoreCase("Success")){
	         			localNoticeDataCollection = new ArrayList<HashMap<String,String>>();
	         			 HashMap<String,String> upcomingEventMap = null;
	         			for(Notices event : noticeList.getNotices()){
	         				upcomingEventMap = new HashMap<String,String>();
	         				upcomingEventMap.put(KEY_ID,event.getNoticeID().trim());
	         				upcomingEventMap.put(DATE,event.getDate().trim());
	         				upcomingEventMap.put(NOTICE_TITLE,event.getNoticeTitle().trim());
	         				upcomingEventMap.put(NOTICE_DESC,event.getNoticeDescription().trim());
	         				//Add to the Arraylist
	         				localNoticeDataCollection.add(upcomingEventMap);
	         			}
	         			bindingData = new LocalNoticeBinderData(LocalNoticeActivity.this,localNoticeDataCollection);
	        			Log.i("BEFORE", "<<------------- Before SetAdapter-------------->>");
	        			list.setAdapter(bindingData);
	        			Log.i("AFTER", "<<------------- After SetAdapter-------------->>");
	                 }
	                 else{
	                       Toast.makeText(LocalNoticeActivity.this, "Sorry Unable to retrive data. Please try again later", Toast.LENGTH_LONG).show();
	                 }
	        }
	 }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(filterArray.isEmpty()){
			filterArray = localNoticeDataCollection;
		}
		Intent i = new Intent();
		i.setClass(LocalNoticeActivity.this, LocalNoticeDetailsActivity.class);

		// parameters
		i.putExtra("position", String.valueOf(position + 1));
		// selected item parameters
		i.putExtra(KEY_ID, filterArray.get(position).get(KEY_ID));
		i.putExtra(DATE, filterArray.get(position).get(DATE));
		i.putExtra(NOTICE_TITLE, filterArray.get(position).get(NOTICE_TITLE));
		i.putExtra(NOTICE_DESC, filterArray.get(position).get(NOTICE_DESC));

		// start the sample activity
		startActivity(i);
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		filterArray.clear();
		searchString = mySearch.getText().toString().trim().replaceAll("\\s", "");

		if (localNoticeDataCollection.size() > 0 && searchString.length() > 0) {
			for (HashMap<String, String> mapList : localNoticeDataCollection) {
				if (mapList.get(NOTICE_TITLE).toLowerCase().contains(searchString.toLowerCase())) {
					filterArray.add(mapList);
				}
			}
			setAdapterToListview(filterArray);
		} else {
			filterArray.clear();
			setAdapterToListview(localNoticeDataCollection);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	
	// setAdapter Here....
	public void setAdapterToListview(List<HashMap<String,String>> listForAdapter) {
		bindingData = new LocalNoticeBinderData(LocalNoticeActivity.this, listForAdapter);
		Log.i("BEFORE", "<<------------- Before SetAdapter OnChange-------------->>");
		list.setAdapter(bindingData);
		Log.i("AFTER", "<<------------- After SetAdapter OnChange-------------->>");
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent();
		i.setClass(getApplicationContext(), DidsburyActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.putExtra("EXIT", true);
		startActivity(i);
	}
	
	// Check Internet Connection!!!
	public boolean isNetworkAvailable() {
		ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
}
