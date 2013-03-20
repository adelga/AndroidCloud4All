package com.cloud4all.devicereporter;
/*

DeviceReporterService
This class is the interface for the Device reporter connnection service.
Use binding connection service to use it.	

	Copyright (c) 2013, Technosite R&D
	All rights reserved.
The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, 
   this list of conditions and the following disclaimer in the documentation 
   and/or other materials provided with the distribution. 
 * Neither the name of Technosite R&D nor the names of its contributors may 
   be used to endorse or promote products derived from this software without 
   specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


 */
import java.util.Map;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class DeviceReporterService extends Service{
	
	// ** Service management
	
	private final IBinder mBinder = new MyBinder();
	private DeviceReporterEngine deviceReporterEngine;

	@Override
	public void onCreate() {
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
			int event = cloudinfo.getIdEvent();
			Log.i("CLOUD", "Evento:"+event);
			switch (event) {
			case CommunicationPersistence.EVENT_GET_FEATURES :
				managePetition(cloudinfo ); 
				break;
				default :
					break;
			}
					} catch (Exception e) {
			Log.e("DeviceReporterService error in onStartCommand", "Error managing the intent.\n"+e);
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}
	
	public class MyBinder extends Binder {
		DeviceReporterService getService() {
			return DeviceReporterService.this;
		}
	}

	// ** Methods for results

	private void managePetition(CloudIntent intent) {
		String[] args;
		try {
			args = intent.getArrayIds();
			for (int i = 0; i < args.length; i++){
				String paramName = args[i];
				Log.i("CLOUD","paranName:"+paramName);
				if (paramName.equalsIgnoreCase("ReporterType")) {
					String paramValue = intent.getValue(args[i]);
					Log.i("CLOUD","paramValue:"+paramValue);
					if (paramValue.equalsIgnoreCase("ROOT") ) {
						saveResultsToJSONFile(getResults());
						sendResults();			
					}
				}
			}
					} catch (Exception e) {
			Log.e("DeviceReporterService error in ManagePettition", "Error in Intent management.\n" +e);
		}
		
		
		
	}
	
	private String resultFilePath = null;
	
	private void sendResults() {
		try {
			CloudIntent intent = new CloudIntent(CommunicationPersistence.ACTION_ORCHESTRATOR,CommunicationPersistence.EVENT_GET_FEATURES_RESPONSE,CommunicationPersistence.MODULE_DEVICE_REPORTER);
			intent.setParams("ReporterType", "ROOT");
			intent.setParams("ReporterPath", resultFilePath );
			Context ct = getApplicationContext();
			ct.sendBroadcast(intent);
		} catch (Exception e) {
			Log.e("DeviceReporterBroadcastManager error in sendResults", "Error sending broadcast.\n" +e);
		}
		}

	// method for getting data from the Device reporter
	public Map<String, String> getResults() {
		deviceReporterEngine = new DeviceReporterEngine(getApplicationContext());
		return deviceReporterEngine.getResults();
	}

	// method for getting data from the Device reporter using a custom context
	public Map<String, String> getResultsWithContext(Context context) {
		deviceReporterEngine = new DeviceReporterEngine(context);
		return deviceReporterEngine.getResults();
	}

	
	// Method for saving Device reporter data in a JSON file
	private void saveResultsToJSONFile(Map<String, String> data) {

		resultFilePath = "/dev/null";
	}
}
