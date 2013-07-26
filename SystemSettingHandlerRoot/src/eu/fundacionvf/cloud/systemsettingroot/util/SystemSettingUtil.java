/*
SystemSettingHandler
This class implements the functionality to modify the system Settings as the screen brightness or time off screen.

Copyright (c) 2013, Vodafone Spain Foundation
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Vodafone Spain Foundation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 */

package eu.fundacionvf.cloud.systemsettingroot.util;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

/**
 * This class contains utility methods.
 * 
 * @author adelga38@corp.vodafone.es (Alberto Delgado García)
 */
public class SystemSettingUtil {

	private final static String preferences = "SystemSettingsPreferences";

	private final String tag = "SystemSettingTAG";
	private String MESSAGE_OK = "OK";
	private String MESSAGE_ERROR = "ERROR_SYSTEM_CONFIGURATION";

	private Context cntx;

	public SystemSettingUtil(Context cntx) {
		super();

		this.cntx = cntx;
	}

	/**
	 * Enable an accessibility Service This method needs a instance of
	 * SystemSettingUtil because the context is necessary.
	 * 
	 * @param accesibility
	 *            , its the packageName and the cAccessibilityService class
	 *            which we want enable
	 * @return void
	 * 
	 */
	public String enableAccessibility(String accesibility) {
		try {
			int e = Integer.parseInt(accesibility);
			if (e == 0 || e == 1) {
				Settings.Secure.putInt(cntx.getContentResolver(),
						Settings.Secure.ACCESSIBILITY_ENABLED, e);

			}
			return MESSAGE_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_ERROR;
		}

	}

	/**
	 * Enable an accessibility Service, the difference with
	 * {@link enableAccessibility} is that this method don't disable the others
	 * accessibility Service. This method needs a instance of SystemSettingUtil
	 * because the context is necessary.
	 * 
	 * @param nameService
	 *            , its the packageName and the cAccessibilityService class
	 *            which we want enable
	 * @return void
	 * 
	 */

	public String addEnableAccessibilityService(String nameService) {
		try {
			String enableSer = Settings.Secure.getString(
					cntx.getContentResolver(),
					Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
			if (!enableSer.contains(nameService)) {
				// If not is enabled we activate the AccessibilityService
				// Service
				Settings.Secure.putInt(cntx.getContentResolver(),
						Settings.Secure.ACCESSIBILITY_ENABLED, 1); // Enable
				String accEna = Settings.Secure.getString(
						cntx.getContentResolver(),
						Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
				Log.d(tag, "enable acc: " + accEna); // accessibility
				Settings.Secure.putString(cntx.getContentResolver(),
						Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES,
						nameService); // Put
										// the
										// package
										// name
										// and
										// the
										// accesibility
										// service
				Settings.Secure.putInt(cntx.getContentResolver(),
						Settings.Secure.ACCESSIBILITY_ENABLED, 1); // Enable
																	// accessibility
			}

			return MESSAGE_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_ERROR;
		}

	}

	/**
	 * Disable an accessibility Service. This method needs a instance of
	 * SystemSettingUtil because the context is necessary.
	 * 
	 * @param nameService
	 *            , its the packageName and the AccessibilityService class which
	 *            we want enable
	 * @return void
	 * 
	 */
	public String removeEnableAccessibilityService(String nameService) {
		try {
			String enableSer = Settings.Secure.getString(
					cntx.getContentResolver(),
					Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
			if (enableSer.contains(nameService)) { // check if the
													// AccessibilityService is
													// enabled
				// If is enabled we disable the AccessibilityService
				Settings.Secure.putInt(cntx.getContentResolver(),
						Settings.Secure.ACCESSIBILITY_ENABLED, 0);// Disable
																	// Accessibility
				Settings.Secure.putString(cntx.getContentResolver(),
						Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, ""); // Clear
																				// the
																				// enabled
																				// accessibility
																				// service

			} else {
				// If not is enabled we activate the AccessibilityService
				// Service
				Settings.Secure.putInt(cntx.getContentResolver(),
						Settings.Secure.ACCESSIBILITY_ENABLED, 1); // Enable
																	// accessibility
				Settings.Secure.putString(cntx.getContentResolver(),
						Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, "");
				Settings.Secure.putInt(cntx.getContentResolver(),
						Settings.Secure.ACCESSIBILITY_ENABLED, 1); // Enable
																	// accessibility
			}

			return MESSAGE_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_ERROR;
		}

	}

	/**
	 * Change the default InputMethod. This method needs a instance of
	 * SystemSettingUtil because the context is necessary.
	 * 
	 * @param nameIME
	 *            , its the packageName and the InputMethodService class which
	 *            we want enable
	 * @return void
	 * 
	 */
	public String changeInputMethod(String nameIME) {
		try {
			String defaultIn = Settings.Secure.getString(
					cntx.getContentResolver(),
					Settings.Secure.DEFAULT_INPUT_METHOD);
			String enabledIn = Settings.Secure.getString(
					cntx.getContentResolver(),
					Settings.Secure.ENABLED_INPUT_METHODS);

			if (!enabledIn.contains(nameIME)) { // Check if the input method is
												// enabled
				enabledIn = enabledIn + ":" + nameIME;
				Settings.Secure.putString(cntx.getContentResolver(),
						Settings.Secure.ENABLED_INPUT_METHODS, enabledIn);// if
																			// not
																			// is
																			// enabled
																			// we
																			// put
																			// the
																			// new
																			// Input
																			// method

			}

			if (!defaultIn.contains(nameIME)) {// Check which is the default
												// input method
				Settings.Secure.putString(cntx.getContentResolver(),
						Settings.Secure.DEFAULT_INPUT_METHOD, nameIME); // Selected
																		// the
																		// default
																		// Input
																		// Method
			}

			return MESSAGE_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_ERROR;
		}
	}

	/**
	 * Enable/Disable TouchExplorationMode. This method needs a instance of
	 * SystemSettingUtil because the context is necessary.
	 * 
	 * @param enable
	 *            , 0 disable 1 enable
	 * @return String, if can`t enable/disable return ERROR, if not OK.
	 * 
	 */
	public String enableTouchMode(String enable) {
		try {

			if (!Settings.Secure.getString(cntx.getContentResolver(),
					Settings.Secure.ACCESSIBILITY_ENABLED).contains("1")) {// The
																			// acc
																			// should
																			// be
																			// enable
				Settings.Secure.putInt(cntx.getContentResolver(),
						Settings.Secure.ACCESSIBILITY_ENABLED, 1);
			} // accessibility

			int en = Integer.valueOf(enable);

			if (en == 0 || en == 1) { // Check if touch
				// exploration is
				// enabled
				Settings.Secure.putInt(cntx.getContentResolver(),
						Settings.Secure.TOUCH_EXPLORATION_ENABLED, en);

			} else {
				return MESSAGE_ERROR;
			}

			return MESSAGE_OK;
		} catch (Exception e) {

			e.printStackTrace();
			return MESSAGE_ERROR;
		}
	}

	/**
	 * Modify the default TTS Synth. This method needs a instance of
	 * SystemSettingUtil because the context is necessary.
	 * 
	 * @param tts
	 *            , the TTSEngine package name.
	 * @return String, if can`t enable/disable return ERROR, if not OK.
	 * 
	 */
	public String defaultTTS(String tts) {
		try {

			Settings.Secure.putString(cntx.getContentResolver(),
					Settings.Secure.TTS_DEFAULT_SYNTH, tts);
			return MESSAGE_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_ERROR;
		}
	}

	/**
	 * Set the TTS Pitch. This method needs a instance of SystemSettingUtil
	 * because the context is necessary.
	 * 
	 * @param pith
	 *            , 0-500
	 * @return String, if can`t configure return ERROR, if not OK.
	 * 
	 */
	public String setPitchTTS(String pitch) {
		try {
			Log.d(tag, "TTS PITCH: " + pitch);
			int p = Integer.parseInt(pitch);
			Log.d(tag, "TTS p: " + p);
			if (p >= 0 && p <= 500) {
				if (!Settings.Secure.putInt(cntx.getContentResolver(),
						Settings.Secure.TTS_DEFAULT_PITCH, p))
					return MESSAGE_ERROR;

				Settings.Secure.putInt(cntx.getContentResolver(),
						Settings.Secure.ACCESSIBILITY_ENABLED, 0);

				Settings.Secure.putInt(cntx.getContentResolver(),
						Settings.Secure.ACCESSIBILITY_ENABLED, 1);
			} else {
				Log.d(tag, "Pitch param: value not valid ");
				return MESSAGE_ERROR;
			}
			return MESSAGE_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_ERROR;
		}
	}

	/**
	 * Set the TTS Rate. This method needs a instance of SystemSettingUtil
	 * because the context is necessary.
	 * 
	 * @param rate
	 *            , 0-500
	 * @return String, if can`t configure return ERROR, if not OK.
	 * 
	 */
	public String setRateTTS(String rate) {
		try {

			int r = Integer.parseInt(rate);
			if (r >= 0 && r <= 500) {
				Settings.Secure.putInt(cntx.getContentResolver(),
						Settings.Secure.TTS_DEFAULT_RATE, r);

				Settings.Secure.putInt(cntx.getContentResolver(),
						Settings.Secure.ACCESSIBILITY_ENABLED, 0);

				Settings.Secure.putInt(cntx.getContentResolver(),
						Settings.Secure.ACCESSIBILITY_ENABLED, 1);
			} else {
				Log.d(tag, "Rate param: value not valid ");
				return MESSAGE_ERROR;
			}
			return MESSAGE_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_ERROR;
		}
	}
}
