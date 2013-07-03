/*
SettingHandlerService class
This class extends from Service, manage all configuration changes.  It's for Android version since Eclair (API 7) until HONEYCOMB (API 12). 
You should install this app as a normal app, You don't need special permissions to use this app.
Copyright (c) 2013, Vodafone Spain Foundation
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Vodafone Spain Foundation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 */

package eu.fundacionvf.cloud.systemsettingpreics;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;

import eu.fundacionvf.cloud.systemsettingpreics.persistence.*;
import eu.fundacionvf.cloud.systemsettingpreics.util.*;

@SuppressLint("NewApi")
public class SettingHandlerService extends Service {

	private String MESSAGE_OK = "OK";
	private static final String TAG = "CLOUD4ALL";

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

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try {
			CloudIntent cloudinfo = CloudIntent.intentToCloudIntent(intent);
			if (cloudinfo != null) {
				int event = cloudinfo.getIdEvent();
				int action = cloudinfo.getIdAction();
				Log.d(TAG, "action " + action);
				// Switch to launch different process by Event Identifier
				switch (event) {
				case CommunicationPersistence.EVENT_CONFIGURE_SYSTEM_SETTINGS:
					Log.i(TAG, "Configure sound...");
					String msge = configure(cloudinfo);
					response(msge, action);
					break;
				case CommunicationPersistence.EVENT_RESTORE_SYSTEM_SETTINGS:
					Log.i(TAG, "restore sound");
					restore(cloudinfo);
					response(MESSAGE_OK, action);
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
			SystemSoundsUtil sound = new SystemSoundsUtil(this);

			String notificationsound = cloudinfo.getValue("notification_sound");
			if (notificationsound != null) {
				sound.restoreNotificationSound();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}

	private void response(String msg, int action) {
		try {
			CloudIntent intent = new CloudIntent(
					CommunicationPersistence.ACTION_ORCHESTRATOR,
					CommunicationPersistence.EVENT_CONFIGURE_SYSTEM_SETTINGS_RESPONSE,
					action);

			intent.setParams("message", msg);
			sendBroadcast(intent);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private String configure(CloudIntent cloudinfo) {
		String[] listaids;
		try {
			listaids = cloudinfo.getArrayIds();

			for (int i = 0; i < listaids.length; i++) {
				Log.e("Receiver", "Parametros: id: " + listaids[i]
						+ ", value: " + cloudinfo.getValue(listaids[i]));

			}

			// System
			SystemSettingUtil system = new SystemSettingUtil(this);

			String brillo = cloudinfo.getValue("brightness");
			String brilloModo = cloudinfo.getValue("brightness_mode");
			if (brillo != null && brilloModo != null) {
				system.changeBrightness(Integer.parseInt(brilloModo),
						Integer.parseInt(brillo));
			}
			String screen_time_off = cloudinfo.getValue("screen_time_off");

			if (screen_time_off != null) {
				system.changeTimeScreenOff(screen_time_off);
			}
			String dim_screen = cloudinfo.getValue("dim_screen");

			if (dim_screen != null) {
				if (dim_screen.contains("0"))
					system.changeDimScreen(false);
				else
					system.changeDimScreen(true);

			}
			String haptic_feedback = cloudinfo.getValue("haptic_feedback");
			if (haptic_feedback != null) {
				system.enableHapticFeedBack(haptic_feedback);
			}

			String auto_rotation = cloudinfo.getValue("auto_rotation");
			if (auto_rotation != null) {
				system.setAutoOrientationEnabled(auto_rotation);
			}

			String msgSound = " ";

			// sound
			SystemSoundsUtil sound = new SystemSoundsUtil(this);

			String notificationsound = cloudinfo.getValue("notification_sound");
			if (notificationsound != null) {
				sound.changeNotificationSound(notificationsound);
			} else {
				Log.e(TAG, "notification_sound null");
			}

			String ringtone = cloudinfo.getValue("ringtone_sound");
			if (ringtone != null) {
				sound.changeRingtoneSound(ringtone);
			} else {
				Log.e(TAG, "rigntone_sound null");
			}
			String sound_effects = cloudinfo.getValue("sound_effects");
			if (sound_effects != null) {
				sound.enableSoundEffects(sound_effects);
			}
			String music_volume = cloudinfo.getValue("music_volume");
			if (music_volume != null) {
				if (!sound.changeMusicVolume(music_volume).equalsIgnoreCase(
						MESSAGE_OK)) {
					msgSound = msgSound + " / changeMusic /";
				}
			}
			String alarm_volume = cloudinfo.getValue("alarm_volume");
			if (alarm_volume != null) {
				if (!sound.changeAlarmVolume(alarm_volume).equalsIgnoreCase(
						MESSAGE_OK)) {
					msgSound = msgSound + " / changeAlarm /";
				}
			}
			String dtmf_volume = cloudinfo.getValue("dtmf_volume");
			if (dtmf_volume != null) {
				if (!sound.changeDTMFVolume(dtmf_volume).equalsIgnoreCase(
						MESSAGE_OK)) {
					msgSound = msgSound + " / changeDTMF /";
				}
			}
			String notification_volume = cloudinfo
					.getValue("notification_volume");
			if (notification_volume != null) {
				if (!sound.changeNotificationVolume(notification_volume)
						.equalsIgnoreCase(MESSAGE_OK)) {
					msgSound = msgSound + " / changeNotification /";
				}
			}
			String ring_volume = cloudinfo.getValue("ring_volume");
			if (ring_volume != null) {
				if (!sound.changeRingVolume(ring_volume).equalsIgnoreCase(
						MESSAGE_OK)) {
					msgSound = msgSound + " / changeRING /";
				}
			}
			String system_volume = cloudinfo.getValue("system_volume");
			if (system_volume != null) {
				if (!sound.changeSystemVolume(system_volume).equalsIgnoreCase(
						MESSAGE_OK)) {
					msgSound = msgSound + " / changeSystem /";
				}
			}
			String voice_call_volume = cloudinfo.getValue("voice_call_volume");
			if (voice_call_volume != null) {
				if (!sound.changeVoiceCallVolume(voice_call_volume)
						.equalsIgnoreCase(MESSAGE_OK)) {
					msgSound = msgSound + " / changeVOICE /";
				}
			}
			String vibrate_notification = cloudinfo
					.getValue("vibrate_notification");
			if (vibrate_notification != null) {
				if (!sound.changeVibrateNotification(vibrate_notification)
						.equalsIgnoreCase(MESSAGE_OK)) {
					msgSound = msgSound + " / vibrateNotification /";
				}
			}
			String vibrate_rigntone = cloudinfo.getValue("vibrate_ringtone");
			if (vibrate_rigntone != null) {
				if (!sound.changeVibrateRigntone(vibrate_rigntone)
						.equalsIgnoreCase(MESSAGE_OK)) {
					msgSound = msgSound + " / vibrateRigntone /";
				}
			}

			// font
			SystemFontUtil font = new SystemFontUtil(this);
			String font_scale = cloudinfo.getValue("font_scale");
			if (font_scale != null) {
				font.changeFontScale(font_scale);
			}

			if (msgSound.equalsIgnoreCase("")) {
				return MESSAGE_OK;
			} else {
				return "ERROR: " + msgSound;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return MESSAGE_OK;
	}

	@Override
	public boolean onUnbind(Intent intent) {
	
		return super.onUnbind(intent);
	}

}
