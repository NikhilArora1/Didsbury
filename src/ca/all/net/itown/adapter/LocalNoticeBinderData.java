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
 * This class helps to bind the notice data to the list view
 * @author Ashwini
 *
 */
public class LocalNoticeBinderData extends BaseAdapter {

	// XML node keys
    static final String KEY_ID = "noticeID";
    static final String DATE = "date";
    static final String NOTICE_TITLE = "noticeTitle";
    static final String NOTICE_DESC = "noticeDescription";
    static final String NOTICE="notice";
	
	LayoutInflater inflater;
	ImageView thumb_image;
	List<HashMap<String,String>> localNoticeList;
	ViewHolder holder;
	Activity context;
	public LocalNoticeBinderData() {
	}
	
	public LocalNoticeBinderData(Activity act, List<HashMap<String,String>> map) {
		
		this.localNoticeList = map;
		this.context=act;
		inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	

	public int getCount() {
    // return idlist.size();
		return localNoticeList.size();
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

	      // Setting all values in listview
	    String noticeTitle = localNoticeList.get(position).get(NOTICE_TITLE).trim();
	    if(noticeTitle.length() >= 28){
	    	noticeTitle = noticeTitle.substring(0, 24)+" ...";
	    }
	      holder.eventTitle.setTextColor((Color.parseColor("#333333")));
		  Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/arial.ttf");
		  holder.eventTitle.setTypeface(tf);
	      holder.eventTitle.setText(noticeTitle);
	      holder.eventTitle.setEllipsize(TruncateAt.END);
	      holder.eventVenue.setText("");
	      holder.eventVenue.setEllipsize(TruncateAt.END);
	      //Setting an image
	      String uri = "drawable/"+ NOTICE;
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
