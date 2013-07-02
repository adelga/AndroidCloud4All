/*
PreferenceService class
This class extends from Service, this Service is invoked when we want to configure the feedback writting in the app Shared Preferences.

Copyright (c) 2013, Vodafone Spain Foundation
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Vodafone Spain Foundation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package eu.fundacionvf.cloud.accessibilityservicenotify.communication;

import org.json.JSONException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.util.Log;

public class PreferenceService extends Service {

	private static final String TAG = "PreferenceService ";
	private String preferences = "AccessibilityServiceNotifyPreferences";

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public void onCreate() {

		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try {

			CloudIntent cloudinfo = CloudIntent.intentToCloudIntent(intent);
			if (cloudinfo != null) {
				int event = cloudinfo.getIdEvent();
				int action = cloudinfo.getIdAction();
				Log.d(TAG, "action " + action);

				if (event == CommunicationPersistence.EVENT_CONFIGURE_HAPTIC_FEEDBACK) {
					String msge = configure(cloudinfo);
					response(msge, action);
				} else {

					response("Event don't register", action);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	/*
	 * Configure the feedback params for the notification feedback.
	 */
	
	private String configure(CloudIntent cloudinfo) {
		try {

			String[] listaids;

			listaids = cloudinfo.getArrayIds();

			for (int i = 0; i < listaids.length; i++) {
				Log.e("Preference Service", "Parametros: id: " + listaids[i]
						+ ", value: " + cloudinfo.getValue(listaids[i]));

			}

			SharedPreferences prefs = this.getSharedPreferences(preferences,
					Context.MODE_PRIVATE);

			Editor ed= prefs.edit();
			String show_window = cloudinfo.getValue("show_window");

			if (show_window != null) {
				ed.putInt("show_window",
						Integer.parseInt(show_window));
				ed.commit();
			}
			String window_width = cloudinfo.getValue("windows_width");

			if (window_width != null) {
				int width = Integer.parseInt(window_width);
				Log.i("accServiceNotify", "width que escribo :" + width);
				ed.putInt("window_width",
						width);
				ed.commit();
			}
			String window_height = cloudinfo.getValue("windows_height");

			if (window_height != null) {
				ed.putInt("window_height",
						Integer.parseInt(window_height));
				ed.commit();
			}
			String text_color = cloudinfo.getValue("text_color_notification");

			if (text_color != null) {

				ed.putString("text_color_notification", text_color);
				ed.commit();
			}

			String background_color = cloudinfo.getValue("background_color_notification");

			if (background_color != null) {

				ed.putString("background_color_notification", background_color);
				ed.commit();
			}

			String notification_vibrate = cloudinfo
					.getValue("notification_vibrate");

			if (notification_vibrate != null) {

				ed.putInt("notification_vibrate",
						Integer.parseInt(notification_vibrate));
				ed.commit();
			}

			String vibrate_pattern = cloudinfo.getValue("vibrate_pattern");

			if (vibrate_pattern != null) {

				ed.putInt("vibrate",
						Integer.parseInt(notification_vibrate));
				ed.commit();
			}

			

			return "OK";
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
/*
* Send an response as a broadcastIntent
*/
	private void response(String string, int action) {

		CloudIntent intent = new CloudIntent(
				CommunicationPersistence.ACTION_ORCHESTRATOR,
				CommunicationPersistence.EVENT_CONFIGURE_HAPTIC_FEEDBACK_RESPONSE,
				action);
		try {

			intent.setParams("message", string);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			sendBroadcast(intent);

		}

	}

}
