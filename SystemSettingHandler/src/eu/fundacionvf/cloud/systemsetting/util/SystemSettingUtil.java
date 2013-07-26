/*
SystemSettingHandler
This class implements the funcionality to modify the system Settings as the screen brightness or time off screen.

Copyright (c) 2013, Vodafone Spain Foundation
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Vodafone Spain Foundation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 */

package eu.fundacionvf.cloud.systemsetting.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import eu.fundacionvf.cloud.systemsetting.DummyActivity;

/**
 * This class contains utility methods.
 * 
 * @author adelga38@corp.vodafone.es (Alberto Delgado García)
 */
public class SystemSettingUtil {

	private final static String preferences = "SystemSettingsPreferences";

	private static final String MESSAGE_ERROR = "ERROR_system";

	private static final String MESSAGE_OK = "OK";

	private final String tag = "SystemSettingTAG";

	private Context cntx;

	public SystemSettingUtil(Context cntx) {
		super();

		this.cntx = cntx;
	}

	/**
	 * Enable he haptic feedback (long presses, ...) This method needs a
	 * instance of SystemSettingUtil because the context is necessary.
	 * 
	 * @param enable
	 *            , The value is boolean (1 or 0).
	 * @return String, "OK" if change successfully , "ERROR" if not
	 * 
	 */

	public String enableHapticFeedBack(String enable) {
		try {

			int efect = Integer.parseInt(enable);
			if (efect == 0 || efect == 1) {
				if (Settings.System.putInt(cntx.getContentResolver(),
						Settings.System.HAPTIC_FEEDBACK_ENABLED, efect))
					return MESSAGE_OK;
				else
					return MESSAGE_ERROR;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_ERROR;
		}
		return MESSAGE_ERROR;
	}

	/**
	 * Configure brightness screen settings This method needs a instance of
	 * SystemSettingUtil because the context is necessary
	 * 
	 * @param mode
	 *            , 0 if the brightness mode is automatic, 1 if it's manual.
	 * @param level
	 *            , if the mode is MANUAL, the level of brightness since 0(min)
	 *            to 255 (max)
	 * @return String, "OK" if change successfully , "ERROR" if not
	 */

	public String changeBrightness(int mode, int level) {
		try {


			if (mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
				Settings.System.putInt(cntx.getContentResolver(),
						Settings.System.SCREEN_BRIGHTNESS_MODE,
						Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
				return MESSAGE_OK;

			} else if (mode == Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL) {
				if (!Settings.System.putInt(cntx.getContentResolver(),
						Settings.System.SCREEN_BRIGHTNESS_MODE,
						Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL))
					return MESSAGE_ERROR;

				if (!Settings.System.putInt(cntx.getContentResolver(),
						Settings.System.SCREEN_BRIGHTNESS, level))
					return MESSAGE_ERROR;// for persistent change
				Intent dummy = new Intent();
				dummy.setClass(cntx, DummyActivity.class);
				dummy.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				dummy.putExtra("brightness", level); // For the instant change
														// it's necessary an
														// Activity,
				cntx.startActivity(dummy);
				return MESSAGE_OK;

			} else {
				return MESSAGE_ERROR;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return MESSAGE_OK;
		}

	}


	

	/**
	 * Configure the screen time off This method needs a instance of
	 * SystemSettingUtil because the context is necessary
	 * 
	 * @param timeoff
	 *            , time of inactivity before turn off the screen.
	 * @return String, "OK" if change successfully , "ERROR" if not
	 */
	public String changeTimeScreenOff(String timeoff) {
		try {
			Log.i(tag,
					"TIME OFF :  "
							+ Settings.System.getString(
									cntx.getContentResolver(),
									Settings.System.SCREEN_OFF_TIMEOUT));
	
			int time = Integer.parseInt(timeoff);
			if (Settings.System.putLong(cntx.getContentResolver(),
					Settings.System.SCREEN_OFF_TIMEOUT, time))
				return MESSAGE_OK;
			else
				return MESSAGE_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_ERROR;

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

			e.printStackTrace();
		}

	}

	/**
	 * Configure the screen time dim This method needs a instance of
	 * SystemSettingUtil because the context is necessary
	 * 
	 * @param dim
	 *            , boolean, if it's true the screen put dim mode before turn
	 *            off.
	 * @return String, "OK" if change successfully , "ERROR" if not
	 */

	public String changeDimScreen(boolean dim) {
		try {
			if (Build.VERSION.SDK_INT < 17) {
				Log.d(tag, "sdsdsddddddddddd  " + Build.VERSION.SDK_INT);

				if (dim) {
					if (Settings.System.putString(cntx.getContentResolver(),
							Settings.System.DIM_SCREEN, "1"))
						return MESSAGE_OK;
					else
						return MESSAGE_ERROR;
				} else {
					if (Settings.System.putString(cntx.getContentResolver(),
							Settings.System.DIM_SCREEN, "0"))
						return MESSAGE_OK;
					else
						return MESSAGE_ERROR;
				}
			} else {
				Log.d(tag, "not available for this android version");
				return MESSAGE_ERROR;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return MESSAGE_ERROR;
		}

	}

	/**
	 * Modity if the screen rotation change when the Accelerometer sensor notify
	 * a change.
	 * 
	 * @param enable
	 *            , The value is boolean (1 or 0).
	 * @return String, "OK" if change successfully , "ERROR" if not
	 * 
	 */
	public String setAutoOrientationEnabled(String enabled) {
		try {
			int en = Integer.parseInt(enabled);
			Log.d(tag, "en = " + en);
			if (en == 0 || en == 1) {
				if (Settings.System.putInt(cntx.getContentResolver(),
						Settings.System.ACCELEROMETER_ROTATION, en))
					return MESSAGE_OK;
				else
					return MESSAGE_ERROR;
			} else {
				return MESSAGE_ERROR;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_ERROR;

		}
	}

	/**
	 * Modify the default screen rotation when no other policy applies This
	 * method needs a instance of SystemSettingUtil because the context is
	 * necessary (Just run properly with auto_rotation disable).
	 * 
	 * @param rotation
	 *            , 0-> 0 Dregree;1-> 90 Dregree;2-> 180 Dregree;3-> 270
	 *            Dregree; .
	 * @return String, "OK" if change successfully , "ERROR" if not
	 * 
	 */

	public String changeDefaultRotation(String rotation) {
		try {

			int rot = Integer.parseInt(rotation);
			if (rot >= 0 && rot <= 3) {
				if (Settings.System.putInt(cntx.getContentResolver(),
						Settings.System.USER_ROTATION, rot))
					return MESSAGE_OK;
				else
					return MESSAGE_ERROR;
			}
		} catch (Exception e) {
			Log.e(tag, "Enable:  " + rotation);
			e.printStackTrace();
			return MESSAGE_ERROR;
		}
		return MESSAGE_ERROR;
	}

}
