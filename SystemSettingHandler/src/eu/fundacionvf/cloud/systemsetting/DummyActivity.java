package eu.fundacionvf.cloud.systemsetting;

/*
 * Dummy Activity is an Activity without UI for change the brightness intanstantly
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

public class DummyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.dummylayout);
		int brightness=this.getIntent().getExtras().getInt("brightness");
		Log.d("DUmmy Activity", "brillo: " + brightness);
		WindowManager.LayoutParams lp = getWindow()
				.getAttributes();
		lp.screenBrightness = brightness / 255.0f; // the most value of
													// brightness is 255
		getWindow().setAttributes(lp); // for the instant
												// change
		
		finish();
	}

}
