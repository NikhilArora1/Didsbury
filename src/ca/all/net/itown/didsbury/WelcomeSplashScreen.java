package ca.all.net.itown.didsbury;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import ca.all.net.itown.util.AppRegistration;

/**
 * Displays the Splash Screen on the start of the application
 * @author Ashwini
 */
public class WelcomeSplashScreen extends Activity {

	// ==============================================
	// Class Variables
	// ==============================================
	private static String TAG = WelcomeSplashScreen.class.getName();
	private static long SLEEP_TIME = 2; // Sleep for some time
	// used to know if the back button was pressed in the splash screen activity and avoid opening the next activity
    private boolean mIsBackButtonPressed;
	private IntentLauncher launcher;

	// ==============================================
	// Instance Methods
	// ==============================================
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Removes title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Removes notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Setting the view to the layout
		setContentView(R.layout.activity_welcome_splash_screen);

		// Start timer and launch main activity
		launcher = new IntentLauncher();
		launcher.start();
	}

	/**
	 * This private class handles the sleep time and the call to the next
	 * Activity for the Application
	 * @author Aswhini
	 */
	private class IntentLauncher extends Thread {
		@Override
		/**
		 * Sleep for some time and than start new activity.
		 */
		public void run() {
			try {
				// Sleeping
				Thread.sleep(SLEEP_TIME * 1000);
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
			WelcomeSplashScreen.this.finish();
			// start the home screen if the back button wasn't pressed already
			if (!mIsBackButtonPressed) {
				//get App registration id
				new AppRegistration(WelcomeSplashScreen.this).getRegistationId();
				// Start main activity
				Intent intent = new Intent(WelcomeSplashScreen.this, DidsburyActivity.class);
				WelcomeSplashScreen.this.startActivity(intent);
			}
		}
	}

	// Function that will handle the touch
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			synchronized (launcher) {
				launcher.notifyAll();
			}
		}
		return true;
	}

	@Override
	public void onBackPressed() {
		// set the flag to true so the next activity won't start up
		mIsBackButtonPressed = true;
		super.onBackPressed();
	}
}
