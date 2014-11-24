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

public class ContactActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		
		ImageView backImageView = (ImageView)findViewById(R.id.back_image);
		ImageButton callImageView = (ImageButton)findViewById(R.id.call_image);
		ImageButton emailImageView = (ImageButton)findViewById(R.id.email_image);
		TextView contactNumber = (TextView)findViewById(R.id.contactNumber);
		TextView contactDetails = (TextView)findViewById(R.id.contactDetails);
		//TextView eventTitleHead = (TextView)findViewById(R.id.eventTitleHead);
		
		//Setting the Color 
		contactNumber.setTextColor((Color.parseColor("#333333")));
		contactDetails.setTextColor((Color.parseColor("#333333")));
		//eventTitleHead.setTextColor((Color.parseColor("#333333")));
		
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
		
		// Setting the Call Button listener
		callImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (view.getId() == R.id.call_image) {
					Intent callIntent = new Intent(Intent.ACTION_CALL);
			        callIntent.setData(Uri.parse("tel:1(403)335-3391"));
			        startActivity(callIntent);
				}
			}
		});

		// Setting the Email Button listener
		emailImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (view.getId() == R.id.email_image) {
					Intent email = new Intent(Intent.ACTION_SEND);
					email.setType("text/html");
					String to = "inquiries@didsbury.ca";
					email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
					startActivity(Intent.createChooser(email, "Send Email"));
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
