package com.cloud4all.minimatchmaker;

/*

Viewer
This class is a example of Mini Match Maker demo.	

Copyright (c) 2013, Technosite R&D
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Technosite R&D nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


 */

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.cloud4all.minimatchmaker.MiniMatchMakerService.MiniMatchMakerListener;

public class Viewer extends Activity  implements MiniMatchMakerListener {

	private TextView monitor = null;
	private TextView brightnessLabel = null;
	private TextView noiseLabel = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewer);
		monitor = (TextView) findViewById(R.id.monitorlabel);
		brightnessLabel = (TextView) findViewById(R.id.brightnesslabel); 
		noiseLabel = (TextView) findViewById(R.id.noiselabel);
		doBindService();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_viewer, menu);
		return true;
	}


	// ** Service connection

	private MiniMatchMakerService sMiniMatchMaker;

	private void registerListener() {
		if (sMiniMatchMaker!=null) {
			sMiniMatchMaker.registerListener(this);
			this.monitor.setText(monitor.getText() + "\nThis application has been registered as MiniMatchMaker listener.");
		}else {
			this.monitor.setText(monitor.getText() + "\nError\nThis application has not been registered as MiniMatchMaker listener.");
		}
	}

	private ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder binder) {
			sMiniMatchMaker = ((MiniMatchMakerService.MyBinder) binder).getService();
			if (sMiniMatchMaker != null) {
				registerListener();
				refreshInterface();
			}
		}

		public void onServiceDisconnected(ComponentName className) {
			sMiniMatchMaker = null;

		}
	};

	// starts the service connection using bindings
	void doBindService() {
		bindService(new Intent(this, MiniMatchMakerService.class), mConnection,
				Context.BIND_AUTO_CREATE);
	}

	// ** Triggers for demo

	private float brightnessValue = 100;
	private float noiseValue = 30;

	private boolean sendEnvironmentalData() {
		// Create a JSON object with data
		JSONObject data = new JSONObject();
		JSONObject sensorBrightness = new JSONObject();
		JSONObject sensorNoise = new JSONObject();
		try {
			sensorBrightness.put("name", "brightness");
			sensorBrightness.put("type", "float");
			sensorBrightness.put("value", Float.valueOf(brightnessValue));

			sensorNoise.put("name", "noise");
			sensorNoise.put("type", "float");
			sensorNoise.put("value", Float.valueOf(noiseValue));

			data.put("type", "Environment");
			data.put("parameters", Integer.valueOf(2));
			data.put("parameter1", sensorBrightness.toString());
			data.put("parameter2", sensorNoise.toString());
			sMiniMatchMaker.insertData(data);
			return true;
		} catch (JSONException e) {
			Log.e("Viewer error", "\nError creating data to send to Mini match maker.\n" +e);
			return false;
		}
	}

	// ** Events for interface

	public void refreshInterface() {
		this.brightnessLabel.setText(String.valueOf(brightnessValue));
		this.noiseLabel.setText(String.valueOf(noiseValue));
	}

	public void pressBrightnessUp(View view) {
		brightnessValue+=30;
		if (brightnessValue>1000) brightnessValue=1000;
		refreshInterface();
	}

	public void pressBrightnessDown(View view) {
		brightnessValue-=30;
		if (brightnessValue<0) brightnessValue=0;
		refreshInterface();
	}

	public void pressNoiseUp(View view) {
		noiseValue+=30;
		if (noiseValue>1000) noiseValue=1000;
		refreshInterface();
	}

	public void pressNoiseDown(View view) {
		noiseValue-=30;
		if (noiseValue<0) noiseValue=0;
		refreshInterface();
	}

	public void pressSend(View view) {
		if (sendEnvironmentalData() == false) this.monitor.setText(monitor.getText() + "\nError sending data to Mini match maker .");
	}

	// ** MiniMatchMaker event listener

	public void manageJSONMessage(JSONObject data) {
		try {
			String typeData = data.getString("type");
			if (typeData.equalsIgnoreCase("message")) {
				String textMessage = data.getString("value");
				this.monitor.setText(monitor.getText() + "\n\n"+ textMessage );
			}
		} catch (JSONException e) {
			this.monitor.setText(monitor.getText() + "\nError parsing data from Mini Match maker. JSON data is not understandable.");
			Log.e("Viewer error", "Error parsing JSON data \n" +e);
		}
	}

	public void MiniMatchMakerResponse(JSONObject data){
		try {
			String typeData = data.getString("type");
			if (typeData.equalsIgnoreCase("package")) {
				this.monitor.setText(""); // clear the monitor
				int n = Integer.valueOf(data.getString("operations"));
				for (int i=1;i<=n;i++) {
					String paramName = "operation" + String.valueOf(i);
					JSONObject JSONtmp = new JSONObject(data.getString(paramName));

					manageJSONMessage(JSONtmp);
				}
			} else if (typeData.equalsIgnoreCase("message")) {
				manageJSONMessage(data);					
			}
		} catch (JSONException e) {
			this.monitor.setText(monitor.getText() + "\nError parsing data from Mini Match maker. JSON data is not understandable.");
			Log.e("Viewer Error in MiniMatchMakerResponse", "Error in JSON from Mini Match Maker.\n" +e);
		}
		Log.i("Info", "\n\n\ndata received from Mini match maker");
	}

}