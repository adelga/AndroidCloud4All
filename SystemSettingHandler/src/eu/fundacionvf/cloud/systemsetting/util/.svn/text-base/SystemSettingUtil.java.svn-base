package eu.fundacionvf.cloud.systemsetting.util;

import eu.fundacionvf.cloud.systemsetting.DummyActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.sax.StartElementListener;
import android.view.WindowManager;

public class SystemSettingUtil {

	private final static String preferences = "SystemSettingsPreferences";

	private final String tag = "SystemSettingTAG";

	private Context cntx;

	public SystemSettingUtil(Context cntx) {
		super();

		this.cntx = cntx;
	}

	public void changeBrightness(int mode, int level) {
		try {
			SharedPreferences prefs = cntx.getSharedPreferences(preferences,
					Context.MODE_PRIVATE);
			int brightnessMode = Settings.System.getInt(
					cntx.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS_MODE);
			int brightness = Settings.System.getInt(cntx.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS);
			prefs.edit().putInt("SCREEN_BRIGHTNESS_MODE", brightnessMode);
			prefs.edit().putInt("SCREEN_BRIGHTNESS", brightness);
			prefs.edit().commit();

			if (mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
				Settings.System.putInt(cntx.getContentResolver(),
						Settings.System.SCREEN_BRIGHTNESS_MODE,
						Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);

			} else {
				Settings.System.putInt(cntx.getContentResolver(),
						Settings.System.SCREEN_BRIGHTNESS_MODE,
						Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

				Settings.System.putInt(cntx.getContentResolver(),
						Settings.System.SCREEN_BRIGHTNESS, level);// for
																		// persistent
																		// change
				Intent dummy = new Intent();
				dummy.setClass(cntx, DummyActivity.class);
				dummy.putExtra("brightness", level); // For the instant change
														// it's necessary an
														// Activity,
				cntx.startActivity(dummy);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void restoreBrightness() {
		try {
			SharedPreferences prefs = cntx.getSharedPreferences(preferences,
					Context.MODE_PRIVATE);
			int brightnessMode = prefs.getInt("SCREEN_BRIGHTNESS_MODE",Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
			int brightness = prefs.getInt("SCREEN_BRIGHTNESS",125);
	

			if (brightnessMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
				Settings.System.putInt(cntx.getContentResolver(),
						Settings.System.SCREEN_BRIGHTNESS_MODE,
						Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);

			} else {
				Settings.System.putInt(cntx.getContentResolver(),
						Settings.System.SCREEN_BRIGHTNESS_MODE,
						Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

				Settings.System.putInt(cntx.getContentResolver(),
						Settings.System.SCREEN_BRIGHTNESS, brightness);// for
																		// persistent
																		// change
				Intent dummy = new Intent();
				dummy.setClass(cntx, DummyActivity.class);
				dummy.putExtra("brightness", brightness); // For the instant change
														// it's necessary an
														// Activity,
				cntx.startActivity(dummy);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void changeTimeScreenOff(String timeoff) {
		try {
			SharedPreferences prefs = cntx.getSharedPreferences(preferences,
					Context.MODE_PRIVATE);
			String time = Settings.System.getString(cntx.getContentResolver(),
					Settings.System.SCREEN_OFF_TIMEOUT);
			prefs.edit().putString("SCREEN_OFF_TIMEOUT", time);
			prefs.edit().commit();

			Settings.System.putString(cntx.getContentResolver(),
					Settings.System.SCREEN_OFF_TIMEOUT, timeoff);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void restoreTimeScreenOff() {
		try {
			SharedPreferences prefs = cntx.getSharedPreferences(preferences,
					Context.MODE_PRIVATE);
			String time = prefs.getString("SCREEN_OFF_TIMEOUT", "30");

			Settings.System.putString(cntx.getContentResolver(),
					Settings.System.SCREEN_OFF_TIMEOUT, time);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void changeDimScreen(boolean dim) {
		try {
			SharedPreferences prefs = cntx.getSharedPreferences(preferences,
					Context.MODE_PRIVATE);

			String dm = Settings.System.getString(cntx.getContentResolver(),
					Settings.System.DIM_SCREEN);
			if (dm != null) {
				if (dm.equals("1")) {
					prefs.edit().putBoolean("DIM_SCREEN", true);
					prefs.edit().commit();
				}else{
					prefs.edit().putBoolean("DIM_SCREEN", false);
					prefs.edit().commit();
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void restoreDimScreen(){
		try {
			SharedPreferences prefs = cntx.getSharedPreferences(preferences,
					Context.MODE_PRIVATE);

			boolean dm = prefs.getBoolean("DIM_SCREEN", false);
			
				if (dm) {
					Settings.System.putString(cntx.getContentResolver(),
							Settings.System.DIM_SCREEN, "1");
				}else{
					Settings.System.putString(cntx.getContentResolver(),
							Settings.System.DIM_SCREEN, "0");
				}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
