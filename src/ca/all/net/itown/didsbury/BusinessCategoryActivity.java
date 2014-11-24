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
import ca.all.net.itown.adapter.BusinessDirectoryBinderData;
import ca.all.net.itown.beans.Business;
import ca.all.net.itown.beans.BusinessDetails;
import ca.all.net.itown.external.DataExchange;
import ca.all.net.itown.util.XmlMapper;


/**
 * This class is to display the list of Upcoming Activity
 * 
 * @author Ashwini
 */
public class BusinessCategoryActivity extends Activity implements TextWatcher,
		OnItemClickListener {

	// Events XML node keys
	static final String KEY_ID = "catID";
	static final String BUSINESS_NAME = "busName";
	static final String BUSINESS_CATEGORY_NAME = "catName";
	static final String BUSINESS = "business";

	String BusCatID = "";
	String BusCatName = "";
	// List items
	ListView list;
	String searchString;
	EditText mySearch;
	List<HashMap<String, String>> businessCategoryDataCollection;
	ImageView imageView;

	BusinessDetails businessDetails;
	BusinessDirectoryBinderData bindingData;
	List<HashMap<String, String>> filterArray = new ArrayList<HashMap<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.events_list);

		try {

			list = (ListView) findViewById(R.id.topicList);
			list.setOnItemClickListener(this);
			mySearch = (EditText) findViewById(R.id.input_search_query);
			mySearch.addTextChangedListener(this);
			// Get Category Id and Category Name
			Intent i = getIntent();
			this.BusCatID = i.getStringExtra(KEY_ID);
			if (BusCatID == null) {
				Intent iBack = getIntent();
				this.BusCatID = iBack.getStringExtra("BusCatID");
			}
			this.BusCatName = i.getStringExtra("catName");
			if (BusCatName == null) {
				Intent iBack = getIntent();
				this.BusCatName = iBack.getStringExtra("busName");
			}
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

			// Setting an image
			imageView = (ImageView) findViewById(R.id.back_image);
			String uri = "drawable/" + BUSINESS + "_back";
			int imageResource = getApplicationContext().getResources()
					.getIdentifier(uri, null,
							getApplicationContext().getPackageName());
			Drawable image = getResources().getDrawable(imageResource);
			imageView.setImageDrawable(image);

			RelativeLayout imageViewHeader = (RelativeLayout) findViewById(R.id.header_image);
			String uriHeader = "drawable/" + BUSINESS + "_header";
			int imageResourceHeader = getApplicationContext().getResources()
					.getIdentifier(uriHeader, null,
							getApplicationContext().getPackageName());
			Drawable imageHeader = getResources().getDrawable(
					imageResourceHeader);
			imageViewHeader.setBackgroundDrawable(imageHeader);

			// Click Event for going back to home page
			// Setting the onclick listener
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					if (view.getId() == R.id.back_image) {
						onBackPressed();
						// moveTaskToBack(true);
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
			Dialog = new ProgressDialog(BusinessCategoryActivity.this);
			Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			Dialog.setMessage("Loading data...");
			Dialog.setCancelable(true);
			Dialog.show();

		}

		@Override
		protected String doInBackground(String... urls) {
			try {
				DataExchange dataExchange = new DataExchange();
				String resultData = dataExchange
						.exchange(BusinessCategoryActivity.this
								.getString(R.string.businessCatUrl) + BusCatID);
				XmlMapper mapper = new XmlMapper();
				businessDetails = (BusinessDetails) mapper.mapData(
						BusinessDetails.class, resultData);
				response = "Success";
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
			if (businessDetails != null
					&& businessDetails.getBusiness() != null
					&& !businessDetails.getBusiness().isEmpty()
					&& response.equalsIgnoreCase("Success")) {
				businessCategoryDataCollection = new ArrayList<HashMap<String, String>>();
				HashMap<String, String> upcomingEventMap = null;
				for (Business event : businessDetails.getBusiness()) {
					upcomingEventMap = new HashMap<String, String>();
					upcomingEventMap.put(KEY_ID, event.getBusID().trim());
					upcomingEventMap.put(BUSINESS_CATEGORY_NAME, event
							.getBusName().trim());
					// Add to the Arraylist
					businessCategoryDataCollection.add(upcomingEventMap);
				}
				bindingData = new BusinessDirectoryBinderData(
						BusinessCategoryActivity.this,
						businessCategoryDataCollection);
				Log.i("BEFORE",
						"<<------------- Before SetAdapter-------------->>");
				list.setAdapter(bindingData);
				Log.i("AFTER",
						"<<------------- After SetAdapter-------------->>");
			} else {
				Toast.makeText(BusinessCategoryActivity.this,
						"Sorry Unable to retrive data. Please try again later",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (filterArray.isEmpty()) {
			filterArray = businessCategoryDataCollection;
		}
		Intent i = new Intent();
		i.setClass(BusinessCategoryActivity.this,
				BusinessCategoryDetailsActivity.class);

		// parameters
		i.putExtra("position", String.valueOf(position + 1));
		// selected item parameters
		i.putExtra(KEY_ID, filterArray.get(position).get(KEY_ID));
		i.putExtra(BUSINESS_CATEGORY_NAME,
				filterArray.get(position).get(BUSINESS_CATEGORY_NAME));
		i.putExtra(BUSINESS_NAME, BusCatName);
		i.putExtra("BusCatID", BusCatID);

		// start the sample activity
		startActivity(i);
	}

	/*
	 * @Override protected void onResume() { super.onResume(); if(BusCatID ==
	 * null){ Intent i = getIntent(); this.BusCatID =
	 * i.getStringExtra("BusCatID"); } }
	 * 
	 * @Override protected void onStart() { super.onStart(); if (BusCatID ==
	 * null) { Intent i = getIntent(); this.BusCatID =
	 * i.getStringExtra("BusCatID"); } }
	 */
	@Override
	public void afterTextChanged(Editable arg0) {
		filterArray.clear();
		searchString = mySearch.getText().toString().trim()
				.replaceAll("\\s", "");

		if (businessCategoryDataCollection.size() > 0
				&& searchString.length() > 0) {
			for (HashMap<String, String> mapList : businessCategoryDataCollection) {
				if (mapList.get(BUSINESS_CATEGORY_NAME).toLowerCase()
						.contains(searchString.toLowerCase())) {
					filterArray.add(mapList);
				}
			}
			setAdapterToListview(filterArray);
		} else {
			filterArray.clear();
			setAdapterToListview(businessCategoryDataCollection);
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
	public void setAdapterToListview(
			List<HashMap<String, String>> listForAdapter) {
		bindingData = new BusinessDirectoryBinderData(
				BusinessCategoryActivity.this, listForAdapter);
		Log.i("BEFORE",
				"<<------------- Before SetAdapter OnChange-------------->>");
		list.setAdapter(bindingData);
		Log.i("AFTER",
				"<<------------- After SetAdapter OnChange-------------->>");
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent();
		i.setClass(getApplicationContext(), BusinessDirectoryActivity.class);
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
