/*
SettingHandlerService class
This class extends from Service, manage all configuration changes.  It's for Android version since Eclair (API 7) until HONEYCOMB (API 12). 
It's necessary put the apk generate in /system/app to get access to all configuration files

Copyright (c) 2013, Vodafone Spain Foundation
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Vodafone Spain Foundation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 */

package eu.fundacionvf.cloud.systemsettingpreicsroot;

import java.io.IOException;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;
import eu.fundacionvf.cloud.systemsettingpreicsroot.persistence.CommunicationPersistence;
import eu.fundacionvf.cloud.systemsettingpreicsroot.util.SystemFontUtil;
import eu.fundacionvf.cloud.systemsettingpreicsroot.util.SystemSettingUtil;
import eu.fundacionvf.cloud.systemsettingpreicsroot.util.SystemSoundsUtil;

@SuppressLint("NewApi")
public class SettingHandlerService extends Service {

	public String MESSAGE_OK = "OK";
	public String MESSAGE_ERROR = "ERROR";
	public static final String TAG = "CLOUD4ALL";
	public static final String MESSAGE_ERROR_PERMISSIONS = "ERROR: Need Root Permissions";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public void onCreate() {
		// Change the ThreadPolicy, so we can download files in the main Service
		// thread (I don't considerate create an asinTask )
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		Log.i("SettingsHandler", "OnCreate: Policy Changed");
		super.onCreate();
	}

	@SuppressLint("NewApi")
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		try {
			Log.e("Receiver", "startComand");
			CloudIntent cloudinfo = CloudIntent.intentToCloudIntent(intent);
			if (cloudinfo != null) {
				int event = cloudinfo.getIdEvent();
				// Switch to launch different process by Event Identifier
				switch (event) {
				case CommunicationPersistence.EVENT_CONFIGURE_SYSTEM_SETTINGS:
					Log.i(TAG, "Configure sound...");
					String m = configure(cloudinfo);
					response(m);
					break;
				case CommunicationPersistence.EVENT_RESTORE_SYSTEM_SETTINGS:
					Log.i(TAG, "restore sound");
					restore(cloudinfo);
					response(MESSAGE_OK);
					break;

				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private void restore(CloudIntent cloudinfo) {
		try {
			// SYSTEM
			SystemSoundsUtil sound = new SystemSoundsUtil(this);

			// FONT

			SystemFontUtil fonts = new SystemFontUtil(this);

			// SOUND

			String urlMode = cloudinfo.getValue("regular_font_mode");
			String urlFont = cloudinfo.getValue("regular_font");
			if (urlFont != null && urlMode != null) {
				fonts.restoreFontRegular();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void response(String msg) {
		try {
			CloudIntent intent = new CloudIntent(
					CommunicationPersistence.ACTION_ORCHESTRATOR,
					CommunicationPersistence.EVENT_CONFIGURE_SYSTEM_SETTINGS_RESPONSE,
					CommunicationPersistence.MODULE_SYSTEM_SETTING_HANDLER);

			intent.setParams("message", msg);
			sendBroadcast(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String configure(CloudIntent cloudinfo) {

		String[] listaids;
		try {
			listaids = cloudinfo.getArrayIds();

			for (int i = 0; i < listaids.length; i++) {
				Log.e("Receiver", "Parametros: id: " + listaids[i]
						+ ", value: " + cloudinfo.getValue(listaids[i]));

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			if (Build.DEVICE.contains("Nexus")) {
				Log.i(TAG, "estamos en el Nexus one -->" + Build.DEVICE);
				String[] hin1 = { "su", "-c",
						" mount -o rw,remount -t yaffs2 /dev/block/mtdblk4 /system" };
				Runtime.getRuntime().exec(hin1);
			} else {
				Log.i(TAG, "No estamos en el Nexus one  -->" + Build.DEVICE + "  " + Build.MANUFACTURER + "  " + Build.PRODUCT);
				String[] hin1 = { "su", "-c", " mount -o rw,remount /system" };
				Runtime.getRuntime().exec(hin1);
			}
			String[] hin1 = { "su", "-c", "mount -o rw,remount -t yaffs2 /dev/block/mtdblk4 /system" };
			Runtime.getRuntime().exec(hin1);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "Mount Error");
			Log.e(TAG, "Are you Root?");

			e.printStackTrace();
			return MESSAGE_ERROR_PERMISSIONS;
		}
		String aux;
		String msgError = " ";
		try {
			// SYSTEM
			SystemSettingUtil system = new SystemSettingUtil(this);

			String acc = cloudinfo.getValue("enable_accessibility");
			if (acc != null) {
				aux = system.enableAccessibility(acc);
				if (!aux.equalsIgnoreCase(MESSAGE_OK)) {
					msgError = msgError + " / EnableAccessibility /";
				}
			}
			String add_serv = cloudinfo.getValue("add_accessibility_service");
			if (add_serv != null) {
				aux = system.addEnableAccessibilityService(add_serv);
				if (!aux.equalsIgnoreCase(MESSAGE_OK)) {
					msgError = msgError + " / Add accessibility Service /";
				}
			}
			String rm_serv = cloudinfo.getValue("remove_accessibility_service");
			if (rm_serv != null) {
				aux = system.removeEnableAccessibilityService(rm_serv);
				if (!aux.equalsIgnoreCase(MESSAGE_OK)) {
					msgError = msgError + " / Remove Accesibility Service /";
				}
			}
			String ime = cloudinfo.getValue("default_ime");
			if (ime != null) {
				aux = system.changeInputMethod(ime);
				if (!aux.equalsIgnoreCase(MESSAGE_OK)) {
					msgError = msgError + " / DefaultIME /";
				}
			}
			String tts = cloudinfo.getValue("tts_engine");
			if (tts != null) {
				aux = system.defaultTTS(tts);
				if (!aux.equalsIgnoreCase(MESSAGE_OK)) {
					msgError = msgError + " / DefaultTTS /";
				}
			}
			String ttsRate = cloudinfo.getValue("tts_rate");
			if (ttsRate != null) {
				aux = system.setRateTTS(ttsRate);
				if (!aux.equalsIgnoreCase(MESSAGE_OK)) {
					msgError = msgError + " / TTSRate /";
				}
			}
			String ttsPitch = cloudinfo.getValue("tts_pitch");
			if (ttsPitch != null) {
				aux = system.setPitchTTS(ttsPitch);
				if (!aux.equalsIgnoreCase(MESSAGE_OK)) {
					msgError = msgError + " / TTSPitch /";
				}
			}
			String touch = cloudinfo.getValue("touch_mode");
			if (touch != null) {
				aux = system.enableTouchMode(touch);
				if (!aux.equalsIgnoreCase(MESSAGE_OK)) {
					msgError = msgError + " / TouchMode /";
				}
			}

			// SOUND
			SystemSoundsUtil sound = new SystemSoundsUtil(this);
			String lock = cloudinfo.getValue("lock_sound");
			if (lock != null) {
				aux = sound.changeLockSound(lock);
				if (!aux.equalsIgnoreCase(MESSAGE_OK)) {
					msgError = msgError + " / LockSound /";
				}
			}
			String unlock = cloudinfo.getValue("unlock_sound");
			if (unlock != null) {
				aux = sound.changeUnlockSound(unlock);
				if (!aux.equalsIgnoreCase(MESSAGE_OK)) {
					msgError = msgError + " / UnlockSound /";
				}
			}
			String low = cloudinfo.getValue("low_battery");
			if (low != null) {
				aux = sound.changeLowBatterySound(low);
				if (!aux.equalsIgnoreCase(MESSAGE_OK)) {
					msgError = msgError + " / LowBatterySound/";
				}
			}
			String tick = cloudinfo.getValue("tick_sound");
			if (tick != null) {
				aux = sound.changeTickSound(tick);
				if (!aux.equalsIgnoreCase(MESSAGE_OK)) {
					msgError = msgError + " / TickSound /";
				}
			}

			// FONT
			SystemFontUtil fonts = new SystemFontUtil(this);


			String urlMode = cloudinfo.getValue("regular_font_mode");
			String urlFont = cloudinfo.getValue("regular_font");
			if (urlFont != null && urlMode != null) {
				if (!fonts.changeRegularFontType(Integer.parseInt(urlMode),
						urlFont).equalsIgnoreCase(MESSAGE_OK)) {
					msgError = msgError + " / RegularFont/";
				}

			}

			if (msgError.equalsIgnoreCase(" ")) {
				return MESSAGE_OK;
			} else {
				return "ERROR:" + msgError;
			}

		}catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_ERROR;
		}

	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}

}
