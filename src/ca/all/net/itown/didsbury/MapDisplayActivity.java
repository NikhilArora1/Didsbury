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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ca.all.net.itown.beans.Location;
import ca.all.net.itown.beans.MapsDetails;
import ca.all.net.itown.external.DataExchange;
import ca.all.net.itown.util.XmlMapper;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapDisplayActivity extends Activity {
	
	static final String MAP_ICON="maps";
	static final String NOTICE_DETAILS = "Notice Details";
	private GoogleMap map;
	
	String position = "1";
	String mapID = "";
	String resultData ="";
	String mapCatName ="";
	
	MapsDetails mapDetails;
	ImageView timageView;
	TextView teventTitleHead;
	ImageView timageViewHeader;
	Drawable image;
	Drawable imageHeader;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_canvas);
        
		try {
			
			//handle for the UI elements 
			timageView = (ImageView)findViewById(R.id.back_image);
			teventTitleHead = (TextView) findViewById(R.id.mapTitleHead);
//			timageViewHeader = (ImageView)findViewById(R.id.header_image_detail);
			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			
			// Get position to display
 	        Intent i = getIntent();
 	        this.position = i.getStringExtra("position");
 	        this.mapID = i.getStringExtra("mapID");
 	        this.mapCatName = i.getStringExtra("mapName");
			
 	       // XML Parsing Using AsyncTask...
			if (isNetworkAvailable()) {
				//Implementing threading for downloading the data from the internet 
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
 		    teventTitleHead.setText(mapCatName);
	            		
	        //Setting the onclick listener
		    timageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					if (view.getId() == R.id.back_image) {
						onBackPressed();
				       // moveTaskToBack(true);
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
               Dialog = new ProgressDialog(MapDisplayActivity.this);
               Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
               Dialog.setMessage("Loading data...");
               Dialog.setCancelable(true);
               Dialog.show();
       
        }
        @Override
        protected String doInBackground(String... urls) {
                 try {
                	  DataExchange dataExchange = new DataExchange();
                	  resultData = dataExchange.exchange(MapDisplayActivity.this.getString(R.string.mapCategoryDescUrl)+mapID);
                	  XmlMapper mapper = new XmlMapper();
              		  mapDetails= (MapsDetails)mapper.mapData(MapsDetails.class, resultData);
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
                 if( response.equalsIgnoreCase("Success") && mapDetails != null && mapDetails.getLocation() != null && !mapDetails.getLocation().isEmpty()){
         	        //text elements
                	 LatLng latLngValue;
                	 LatLng firstLatLngValue = new LatLng(Double.parseDouble(mapDetails.getLocation().get(0).getLocLat()), Double.parseDouble(mapDetails.getLocation().get(0).getLocLong()));;
                	 for(Location location : mapDetails.getLocation()) {
                		 latLngValue = new LatLng(Double.parseDouble(location.getLocLat()), Double.parseDouble(location.getLocLong()));
                		 map.addMarker(new MarkerOptions().position(latLngValue).title(location.getLocName()));
                	}
                	 // Move the camera instantly to hamburg with a zoom of 15.
         		    map.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLatLngValue, 15));

         		    // Zoom in, animating the camera.
         		    map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                	 
                 } else{
                       Toast.makeText(MapDisplayActivity.this, "Sorry Unable to retrive data. Please try again later", Toast.LENGTH_LONG).show();
                 }
        }
 }
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent();
		i.setClass(getApplicationContext(), MapCategoryActivity.class);
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
