package ca.all.net.itown.didsbury;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SunovaCenterActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sunova);
		
		ImageView backImageView = (ImageView)findViewById(R.id.back_image);
		ImageButton facebookImage = (ImageButton)findViewById(R.id.facebook_image);
		ImageButton twitterImage = (ImageButton)findViewById(R.id.twitter_image);
		ImageButton websiteImage = (ImageButton)findViewById(R.id.website_image);
		ImageButton mapImage = (ImageButton)findViewById(R.id.map_image);
		TextView contactNumber = (TextView)findViewById(R.id.contactNumber);
		TextView contactDetails = (TextView)findViewById(R.id.contactDetails);
		TextView eventTitleHead = (TextView)findViewById(R.id.eventTitleHead);
		
		//Setting the Color 
		contactNumber.setTextColor((Color.parseColor("#4a382a")));
		contactDetails.setTextColor((Color.parseColor("#4a382a")));
		eventTitleHead.setTextColor((Color.parseColor("#4a382a")));
		
		//Setting the Font
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/arial.ttf");
		contactNumber.setTypeface(tf,Typeface.BOLD);
		contactDetails.setTypeface(tf);
		//eventTitleHead.setTypeface(tf);
		
			/*Setting the listeners to the button*/
		
		//Setting the Back Button listener
		backImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (view.getId() == R.id.back_image) {
					onBackPressed();
					//moveTaskToBack(true);
			    }
			}
		});
		
		// Setting the facebook Button listener
		facebookImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (view.getId() == R.id.facebook_image) {
					//Intent for Facebook
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse("http://www.facebook.com/pages/Sunova-Centre-West-St-Pauls-Recreation-Site/212121045516078"));
					startActivity(i);
				}
			}
		});

		// Setting the twitter Button listener
		twitterImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (view.getId() == R.id.twitter_image) {
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse("https://twitter.com/SunovaCentre"));
					startActivity(i);
				}
			}
		});
		
		// Setting the website Button listener
		websiteImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (view.getId() == R.id.website_image) {
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse("http://www.weststpaul.com/main.asp?cat_ID=3"));
					startActivity(i);
				}
			}
		});
		
		// Setting the map Button listener
		mapImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (view.getId() == R.id.map_image) {
					Intent i = new Intent();
					i.setClass(getApplicationContext(), MapCanvasActivity.class);
					startActivity(i);
				}
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent();
		i.setClass(getApplicationContext(), DidsburyActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
}
