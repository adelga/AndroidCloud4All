/*
NotificationActivityTransparent class
This class extends from Activity, this Activity is responsible for turn on the screen and shows a dialog with main notification information.

Copyright (c) 2013, Vodafone Spain Foundation
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Vodafone Spain Foundation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package eu.fundacionvf.cloud.accessibilityservicenotify.popup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import eu.fundacionvf.cloud.accessibilityservicenotify.R;

public class NotificationActivityTransparent extends Activity {

	/** Constant for the preferences */
	private String preferences = "AccessibilityServiceNotifyPreferences";

	boolean showPopup, touchValid, triggers, screenWasOff;
	Notification notif;
	RemoteViews remViews;
	String appname = "Unknow";
	String notText = " ";
	String backgroundcolor = "#FFFFFF";
	String textcolor = "#000000";
	String pack;
	View nView;
	ViewGroup pView;
	int window_width;
	int window_height;

	float X, lastX;

	AlertDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("NOTTR", "ONCREATE");

		screenWasOff = getIntent().getBooleanExtra("screenWasOff", false);
		notText = getIntent().getStringExtra("textNotification");
		if (notText == null) {
			notText = "Notify";
		}
		pack = getIntent().getStringExtra("textApp");
		Log.d("NOTTR", "pack: " +pack);
		appname = pack.substring(pack.lastIndexOf(".") + 1);
		Log.d("accServiceNotify", "nameee: " + appname);

		if (appname == null) {
			appname = "¿?";
		}
		Log.d("NOT", "notText" + notText);
		Log.d("NOT", "app name" + appname);

		getWindow().setFlags(LayoutParams.FLAG_TURN_SCREEN_ON,
				LayoutParams.FLAG_TURN_SCREEN_ON);
		getWindow().setFlags(LayoutParams.FLAG_SHOW_WHEN_LOCKED,
				LayoutParams.FLAG_SHOW_WHEN_LOCKED);

		super.onCreate(savedInstanceState);
	}

	@SuppressLint("NewApi")
	@Override
	protected void onResume() {
		super.onResume();
		Log.d("NOTTR", "ONCresume");

		SharedPreferences prefs = this.getSharedPreferences(preferences,
				Context.MODE_PRIVATE);
		window_width = prefs.getInt("window_width", 400);
		window_height = prefs.getInt("window_height", 500);
		textcolor = prefs.getString("text_color_notification", "#000000");
		backgroundcolor = prefs.getString("background_color_notification", "#FFFFFF");

		if (!preparePopup())
			return;
		try {

			showPopupButton();
		} catch (Exception e) {
			e.printStackTrace();
			finish();
		}
	}

	@SuppressLint("NewApi")
	private boolean preparePopup() {
		try {
			Log.d("NOTTR", "preparepopup");

			dialog = new AlertDialog.Builder(this).create();
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					finish();
				}
			});

		} catch (Exception e) {
			finish();
			return false;
		}
		return true;
	}

	@SuppressLint("NewApi")
	private void showPopupButton() {
		Log.d("NOTTR", "SHOWPOPUP");
		try {

			nView = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.messagebox, null);

			TextView txtView = (TextView) nView.findViewById(R.id.textView1);
			RelativeLayout rl = (RelativeLayout) nView
					.findViewById(R.id.RelativeLayout);
			TextView txtapp = (TextView) nView.findViewById(R.id.textView2);

			rl.setBackgroundColor(Color.parseColor(backgroundcolor));
			txtView.setTextColor(Color.parseColor(textcolor));
			txtapp.setTextColor(Color.parseColor(textcolor));

			txtView.setText(notText);
			txtapp.setText(appname + "  "
					+ getResources().getString(R.string.notification));

			


			
			Button btn = (Button) nView.findViewById(R.id.button1);
			btn.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					dialog.cancel();
					finish();

				}
			});

			Button btn2 = (Button) nView.findViewById(R.id.button2);
			btn2.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					try {
						Intent launchIntent = getPackageManager()
								.getLaunchIntentForPackage(pack);


						startActivity(launchIntent);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			dialog.setView(nView);


			dialog.show();

			dialog.getWindow().setLayout(window_width, window_height-100);
		} catch (Exception e) {
			e.printStackTrace();
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (screenWasOff) {
			Settings.System.putLong(getContentResolver(),
					Settings.System.SCREEN_OFF_TIMEOUT, 30);

		}

		if (dialog != null)
			dialog.dismiss();

	}

	@Override
	protected void onNewIntent(Intent intent) {

		if (!getIntent().equals(intent)) {
			finish();
			startActivity(intent);
		} else
			super.onNewIntent(intent);
	}

}
