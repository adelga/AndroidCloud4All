/*
NotificationAccessibilityService class
This class extends from Accessibility Service, this class listen all notification AccessibilityEvents in the system and provide custom feedback, which can be haptic feeback or visual feedback	on screen.

Copyright (c) 2013, Vodafone Spain Foundation
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Vodafone Spain Foundation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/
package eu.fundacionvf.cloud.accessibilityservicenotify;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.util.SparseArray;
import android.view.accessibility.AccessibilityEvent;
import eu.fundacionvf.cloud.accessibilityservicenotify.popup.NotificationActivityTransparent;



/**
 * This class provides custom feedback
 */
public class NotificationAccessibilityService extends AccessibilityService {

	/** Constant for the preferences */
	private String preferences = "AccessibilityServiceNotifyPreferences";

	/** Tag for logging from this service. */
	private static final String LOG_TAG = "AccService";

	// Fields for configuring how the system handles this accessibility service.

	/** Minimal timeout between accessibility events we want to receive. */
	private static final int EVENT_NOTIFICATION_TIMEOUT_MILLIS = 80;

	// Message types we are passing around.
	/** Shows a Windows with a text message */
	private static final int MESSAGE_SHOWS_WINDOWS = 5;

	/** Vibrate a pattern. */
	private static final int MESSAGE_VIBRATE = 7;

	/** Stop vibrating. */
	private static final int MESSAGE_STOP_VIBRATE = 8;



	/**
	 * The queuing mode we are using - interrupt a spoken utterance before
	 * speaking another one.
	 */
	private static final int QUEUING_MODE_INTERRUPT = 2;

	/** The space string constant. */
	private static final String SPACE = " ";

	/**
	 * Mapping from integers to vibration patterns for haptic notification
	 * feedback.
	 */
	private static final SparseArray<long[]> sVibrationPatterns = new SparseArray<long[]>();
	static {
		sVibrationPatterns.put(0, new long[] { 0L, 100L });
		sVibrationPatterns.put(1, new long[] { 0L, 15L, 10L, 15L });
		sVibrationPatterns.put(2, new long[] { 0L, 25L, 50L, 25L, 50L, 25L });
		sVibrationPatterns.put(3, new long[] { 0L, 10L, 10L, 20L, 20L, 30L });
	}

	// Auxiliary fields.

	/**
	 * Handle to this service to enable inner classes to access the
	 * {@link Context}.
	 */
	Context mContext;

	/** The feedback this service is currently providing. */
	int mProvidedFeedbackType;

	/** Reusable instance for building utterances. */
	private final StringBuilder mUtterance = new StringBuilder();

	// Feedback providing services.

	/** Vibrator for providing haptic feedback. */
	private Vibrator mVibrator;

	/** Flag if the infrastructure is initialized. */
	private boolean isInfrastructureInitialized;

	/** {@link Handler} for executing messages on the service main thread. */
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			switch (message.what) {

			case MESSAGE_SHOWS_WINDOWS:
				try {

					Bundle data = message.getData();

					Log.i(LOG_TAG, "package: " + data.getString("package"));

					Log.d("TAG", "text: " + data.getString("text"));

					String pack = (String) data.getString("package");


					KeyguardManager kmng = (KeyguardManager) getApplicationContext()
							.getSystemService("keyguard");
					Intent i = new Intent(getApplicationContext(),
							NotificationActivityTransparent.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.putExtra("screenWasOff", true);

					i.putExtra("textNotification", data.getString("text"));
					i.putExtra("textApp", pack);
					startActivity(i);
				} catch (Exception e) {
					e.printStackTrace();
				}

				return;
			case MESSAGE_VIBRATE:
				int key = message.arg1;
				long[] pattern = sVibrationPatterns.get(key);
				if (pattern != null) {
					mVibrator.vibrate(pattern, -1);
				}
				return;
			case MESSAGE_STOP_VIBRATE:
				mVibrator.cancel();
				return;
			}
		}
	};

	@Override
	public void onServiceConnected() {
		if (isInfrastructureInitialized) {
			return;
		}

		mContext = this;

		mVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
		setServiceInfo(AccessibilityServiceInfo.FEEDBACK_HAPTIC);
		isInfrastructureInitialized = true;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(LOG_TAG, "onDestroy");
		if (isInfrastructureInitialized) {

			// We are not in an initialized state anymore.
			isInfrastructureInitialized = false;
		}
		return false;
	}

	/**
	 * Sets the AccessibilityServiceInfo which informs the system how to handle
	 * this
	 * 
	 * @param feedbackType
	 *            The type of feedback this service will provide.
	 */
	private void setServiceInfo(int feedbackType) {
		AccessibilityServiceInfo info = new AccessibilityServiceInfo();
		// We are interested in notification accessibility events.
		info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
		// We want to provide specific type of feedback.
		info.feedbackType = feedbackType;
		// We want to receive events in a certain interval.
		info.notificationTimeout = EVENT_NOTIFICATION_TIMEOUT_MILLIS;

		setServiceInfo(info);
	}

	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {

		if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
			Log.i(LOG_TAG, mProvidedFeedbackType + " " + event.toString());

			SharedPreferences prefs = this.getSharedPreferences(preferences,
					Context.MODE_PRIVATE);
			int show_window = prefs.getInt("show_window", 0);

			Log.d(LOG_TAG, "package " + event.getPackageName());
			Log.d(LOG_TAG, "class: " + event.getClassName());
			
			if (show_window == 1 && !((String) event.getPackageName()).equalsIgnoreCase("com.android.phone")) {
				Message msg = mHandler.obtainMessage(MESSAGE_SHOWS_WINDOWS, 0,
						0);
				Bundle data = new Bundle();
				String pack = (String) event.getPackageName();
				String text = (String) event.getText().get(0);
				if(pack!= null || text!=null){
				data.putString("package", pack);
				data.putString("text",text );
				msg.setData(data);
				msg.sendToTarget();
				}else{
					Log.e(LOG_TAG, "Not possible send windows because there isnt' enought information");
				}
			}

			int vibrate = prefs.getInt("notification_vibrate", 0);
			if (vibrate == 1) {
				// how  want the device provide haptic feedback
				int typeVibrate = prefs.getInt("vibrate_pattern", 0); 
	

				Message msg = mHandler.obtainMessage(MESSAGE_VIBRATE,
						typeVibrate, 0);
				msg.sendToTarget();
			}

		}
	}

	@Override
	public void onInterrupt() {

		mHandler.obtainMessage(MESSAGE_STOP_VIBRATE).sendToTarget();

	}

}
