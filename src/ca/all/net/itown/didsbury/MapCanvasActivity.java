package ca.all.net.itown.didsbury;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapCanvasActivity extends Activity {

	static final LatLng SUNOVA_CENTER = new LatLng(49.978238, -97.082954);
	static final String MAP_ICON="maps";
	static final String NOTICE_DETAILS = "Notice Details";
	private GoogleMap map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_canvas);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		Marker hamburg = map.addMarker(new MarkerOptions().position(SUNOVA_CENTER).title("Sunova Center"));
		//Marker kiel = map.addMarker(new MarkerOptions().position(KIEL).title("Kiel").snippet("Kiel is cool").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));

		    // Move the camera instantly to hamburg with a zoom of 15.
		    map.moveCamera(CameraUpdateFactory.newLatLngZoom(SUNOVA_CENTER, 15));

		    // Zoom in, animating the camera.
		    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	}
}
