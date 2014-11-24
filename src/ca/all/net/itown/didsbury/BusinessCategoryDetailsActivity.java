package ca.all.net.itown.didsbury;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ca.all.net.itown.external.DataExchange;


public class BusinessCategoryDetailsActivity extends Activity {
	
	static final String BUSINESS= "business";
	static final String BUSINESS_NAME = "busName";
	
	String position = "1";
	String busID = "";
	String resultData = "";
	String catName = "";
	String busCatID = "";
	Drawable image;
	
	WebView teventDescription;
	ImageView timageView;
	TextView teventTitleHead;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_detailpage);
        
		try {
			
			//handle for the UI elements 
			//Text fields
			teventDescription = (WebView) findViewById(R.id.webViewUpcoming);
			timageView = (ImageView)findViewById(R.id.back_image);
			teventTitleHead = (TextView) findViewById(R.id.eventTitleHead);
			
			// Get position to display
	        Intent i = getIntent();
	        
	        this.position = i.getStringExtra("position");
	        this.busID = i.getStringExtra("catID");
	        this.catName = i.getStringExtra(BUSINESS_NAME);
	        this.busCatID = i.getStringExtra("BusCatID");
	       
			// XML Parsing Using AsyncTask...
			if (isNetworkAvailable()) {
				// Implementing threading for downloading the data from the internet
				AsyncTaskAdapter asyncTask = new AsyncTaskAdapter();
				asyncTask.execute();
			} else {
				Toast.makeText(this, "No Netwrok Connection!!!", Toast.LENGTH_SHORT).show();
				this.finish();
			}
			
			teventTitleHead.setTextColor((Color.parseColor("#993333")));
 		    Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/arial.ttf");
 		    teventTitleHead.setTypeface(tf1);
 		    teventTitleHead.setTypeface(null, Typeface.BOLD);
 		    teventTitleHead.setText(catName);
	      
		    //Setting the onclick listener
		    timageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					if (view.getId() == R.id.back_image) {
						onBackPressed();
						//moveTaskToBack(true);
				    }
				}
			});
		}
		catch (Exception ex) {
			Log.e("Error", "Loading exception");
		}
		
    }
    
  //Implementing Threading in Android
    private class AsyncTaskAdapter extends AsyncTask<String, Void, String> {
        // private String Content;
        private ProgressDialog Dialog;
        String response = "";
        
        @Override
        protected void onPreExecute() {
               Dialog = new ProgressDialog(BusinessCategoryDetailsActivity.this);
               Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
               Dialog.setMessage("Loading data...");
               Dialog.setCancelable(true);
               Dialog.show();
       
        }
        @Override
        protected String doInBackground(String... urls) {
                 try {
                	 	DataExchange dataExchange = new DataExchange();
                	 	resultData = dataExchange.exchange(BusinessCategoryDetailsActivity.this.getString(R.string.businessCatDescUrl)+busID);
                	 	Log.i("BusinessId - ", busID);
                        response ="Success";
                       }
                 catch (Exception e) {
                              response ="Failed";
                              Log.e("Error", e.getLocalizedMessage());
                       }
               return response;
        }
        @Override
        protected void onPostExecute(String result) {
          Dialog.dismiss();
          Dialog = null;
                 if(!resultData.isEmpty() && response.equalsIgnoreCase("Success")){
         	        //text elements
                	teventDescription.loadData(resultData, "text/html", "UTF-8");
                 } else{
                       Toast.makeText(BusinessCategoryDetailsActivity.this, "Sorry Unable to retrive data. Please try again later", Toast.LENGTH_LONG).show();
                 }
        }
 }
    
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent();
		i.setClass(getApplicationContext(), BusinessCategoryActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.putExtra("BusCatID", busCatID);
		i.putExtra(BUSINESS_NAME, catName);
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
