package ca.all.net.itown.adapter;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ca.all.net.itown.didsbury.R;
/**
 *This class is used to bind data for the home page 
 * @author Ashwini
 */
public class UpcomingEventBinderData extends BaseAdapter {

	// XML node keys
    static final String KEY_ID = "eventID";
    static final String START_DATE = "startDate";
//    static final String START_TIME = "startTime";
//    static final String END_DATE = "endDate";
//    static final String END_TIME = "endTime";
    static final String EVENT_TITLE = "eventTitle";
//    static final String EVENT_LOC = "eventLocation";
//    static final String EVENT_DESC = "eventDescription";
    static final String UPCOMING_EVENT_ICON="upcoming";
	
	LayoutInflater inflater;
	ImageView thumb_image;
	List<HashMap<String,String>> upcomingEventsList;
	ViewHolder holder;
	Activity context;
	public UpcomingEventBinderData() {
	}
	
	public UpcomingEventBinderData(Activity act, List<HashMap<String,String>> map) {
		
		this.upcomingEventsList = map;
		this.context=act;
		inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	

	public int getCount() {
    // return idlist.size();
		return upcomingEventsList.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		 
		View vi=convertView;
	    if(convertView==null){
	     
	      vi = inflater.inflate(R.layout.events_list_row, null);
	      holder = new ViewHolder();
	     
	      holder.eventTitle = (TextView)vi.findViewById(R.id.eventTitle); // city name
	      holder.eventVenue =(TextView)vi.findViewById(R.id.eventVenue); // thumb image
	      holder.eventImage =(ImageView)vi.findViewById(R.id.list_image); // thumb image
	 
	      vi.setTag(holder);
	    }
	    else{
	    	
	    	holder = (ViewHolder)vi.getTag();
	    }
	    
	    String upcomingTitle = upcomingEventsList.get(position).get(EVENT_TITLE).trim();
	    if(upcomingTitle.length() >= 28){
	    	upcomingTitle = upcomingTitle.substring(0, 24)+" ...";
	    }
	      // Setting all values in listview
	      holder.eventTitle.setTextColor((Color.parseColor("#333333")));
		  Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/arial.ttf");
		  holder.eventTitle.setTypeface(tf);
	      holder.eventTitle.setText(upcomingTitle);
	      holder.eventTitle.setEllipsize(TruncateAt.END);
	      
	      
	      String upcomingVenue = upcomingEventsList.get(position).get(START_DATE).trim();
		    if(upcomingVenue.length() >= 28){
		    	upcomingVenue = upcomingVenue.substring(0, 24)+" ...";
		    }
//	      holder.eventVenue.setTextColor((Color.parseColor("#373737")));
		  Typeface tf1 = Typeface.createFromAsset(context.getAssets(), "fonts/arial.ttf");
		  holder.eventVenue.setTypeface(tf1);
	 holder.eventVenue.setText(upcomingVenue);
	      holder.eventVenue.setEllipsize(TruncateAt.END);
	      
	      //Setting an image
	      String uri = "drawable/"+ UPCOMING_EVENT_ICON;
	      int imageResource = vi.getContext().getApplicationContext().getResources().getIdentifier(uri, null, vi.getContext().getApplicationContext().getPackageName());
	      Drawable image = vi.getContext().getResources().getDrawable(imageResource);
	      holder.eventImage.setImageDrawable(image);
	      
	      return vi;
	}
	
	/**
	 * Implementation of View Holder design pattern
	 * @author Ashwini
	 */
	static class ViewHolder{
		TextView eventTitle;
		TextView eventVenue;
		ImageView eventImage;
	}
	
}
