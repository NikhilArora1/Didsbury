package ca.all.net.itown.adapter;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ca.all.net.itown.didsbury.R;
/**
 *This class is used to bind data for the home page 
 * @author Ashwini
 */
public class GillamBinderData extends BaseAdapter {

	// XML node keys
    static final String KEY_ID = "id";
    static final String KEY_NAME = "name";
    static final String KEY_ICON = "icon";
    static final String KEY_COLOR = "color";
    
    
	LayoutInflater inflater;
	List<HashMap<String,String>> eventDataCollection;
	ViewHolder holder;
	Activity context;
	
	public GillamBinderData() {
	}
	
	public GillamBinderData(Activity act, List<HashMap<String,String>> map) {
		
		this.eventDataCollection = map;
		this.context = act;
		inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	

	public int getCount() {
    // return idlist.size();
		return eventDataCollection.size();
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
	     
	      vi = inflater.inflate(R.layout.list_row, null);
	      holder = new ViewHolder();
	      holder.relativeLayout = (RelativeLayout)vi.findViewById(R.id.rowView);
	      holder.name = (TextView)vi.findViewById(R.id.tvCity); // city name
	      holder.icon =(ImageView)vi.findViewById(R.id.list_image); // thumb image
	 
	      vi.setTag(holder);
	    }
	    else{
	    	
	    	holder = (ViewHolder)vi.getTag();
	    }

	      
	    Rect rect = new Rect();
        Window win = context.getWindow();
        win.getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusHeight = rect.top;
        int contentViewTop = win.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleHeight = contentViewTop - statusHeight;
        Log.i("StoneWallBinderData", "titleHeight = " + titleHeight + " statusHeight = " + statusHeight + " contentViewTop = " + contentViewTop);
        //System.out.println("titleHeight = " + titleHeight + " statusHeight = " + statusHeight + " contentViewTop = " + contentViewTop);
	    
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();  // deprecated from API level 13
        int height = display.getHeight();
        //System.out.println("width = " + width + " height = " + height);
        int heightLeft = (height-(/*titleHeight+statusHeight*/+contentViewTop));
        Log.i("StoneWallBinderData", "widthLeft = " + width + " heightLeft = " + heightLeft);
        //System.out.println("widthLeft = " + width + " heightLeft = " + (height-(titleHeight+statusHeight+contentViewTop)));
	    
        int calculatedRowHeight = ((heightLeft/10)*5)/5;
	    
	      // Setting all values in listview
	      //Setting the width and height of the list
	      AbsListView.LayoutParams adaptLayout = new AbsListView.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, calculatedRowHeight);
	      holder.relativeLayout.setLayoutParams(adaptLayout);
	      String colorName = eventDataCollection.get(position).get(KEY_COLOR);
	      holder.name.setTextColor((Color.parseColor(colorName)));
		  Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/arial.ttf");
		  holder.name.setTypeface(tf);
	      holder.name.setText(eventDataCollection.get(position).get(KEY_NAME));
	      
	      //Setting an image
	      String uri = "drawable/"+ eventDataCollection.get(position).get(KEY_ICON);
	      int imageResource = vi.getContext().getApplicationContext().getResources().getIdentifier(uri, null, vi.getContext().getApplicationContext().getPackageName());
	      Drawable image = vi.getContext().getResources().getDrawable(imageResource);
	      holder.icon.setImageDrawable(image);
	      
	      return vi;
	}
	
	/*
	 * 
	 */
	static class ViewHolder{
		RelativeLayout relativeLayout;
		TextView name;
		ImageView icon;
	}
	
}
