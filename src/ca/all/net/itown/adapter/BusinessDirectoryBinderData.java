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
public class BusinessDirectoryBinderData extends BaseAdapter {

	// XML node keys
    static final String KEY_ID = "mapID";
    static final String BUSINESS_CATEGORY_NAME = "catName";
    static final String BUSINESS="business";
	
	LayoutInflater inflater;
	ImageView thumb_image;
	List<HashMap<String,String>> businessCategoriesList;
	ViewHolder holder;
	Activity context;
	public BusinessDirectoryBinderData() {
	}
	
	public BusinessDirectoryBinderData(Activity act, List<HashMap<String,String>> map) {
		
		this.businessCategoriesList = map;
		this.context=act;
		inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	

	public int getCount() {
    // return idlist.size();
		return businessCategoriesList.size();
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

	    String businessTitle = businessCategoriesList.get(position).get(BUSINESS_CATEGORY_NAME).trim();
	    if(businessTitle.length() >= 28){
	    	businessTitle = businessTitle.substring(0, 25)+" ...";
	    }
	      // Setting all values in listview
	      holder.eventTitle.setTextColor((Color.parseColor("#333333")));
		  Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/arial.ttf");
		  holder.eventTitle.setTypeface(tf);
	      holder.eventTitle.setText(businessTitle);
	      holder.eventTitle.setEllipsize(TruncateAt.END);
	      holder.eventVenue.setText("");
	      
	      //Setting an image
	      String uri = "drawable/"+ BUSINESS;
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
