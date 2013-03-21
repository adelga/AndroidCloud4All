/*
EventsController class
This class have the functionality for orchestrate the requests.

Copyright (c) 2013, Vodafone Spain Foundation
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Vodafone Spain Foundation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/
package eu.fundacionvf.cloud4all.orchestrator.controller;

import eu.fundacionvf.cloud4all.orchestrator.util.CloudIntent;
import eu.fundacionvf.cloud4all.orchestrator.util.CommunicationPersistence;
import android.os.IBinder;
import android.app.Service;
import android.content.Intent;
import android.util.Log;

public class EventsController extends Service {

	private static final String TAG = "CLOUD4ALL";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		try {
			Log.i(TAG, "Orchestrator startComand");
			CloudIntent cloudinfo = CloudIntent.intentToCloudIntent(intent);
			int event = cloudinfo.getIdEvent();
			Log.i(TAG, "Evento recibido:" + event);
			switch (event) {
			case CommunicationPersistence.EVENT_CONFIGURE_SYSTEM_SETTINGS:
				Log.i(TAG, "Configurar Controller");
				configuration(cloudinfo);
				break;
			case CommunicationPersistence.EVENT_RESTORE_SYSTEM_SETTINGS:
				Log.i(TAG, "Restore Setting Handler");
				restore(cloudinfo);
				break;
			case CommunicationPersistence.EVENT_GET_FEATURES:
				Log.i(TAG, "Get Features - Device Reporter");
				getFeatures(cloudinfo);
				break;
			case CommunicationPersistence.EVENT_GET_FEATURES_RESPONSE:
				Log.i(TAG, "Device Reporter Response");
				getConfiguration(cloudinfo);
				break;
			case CommunicationPersistence.EVENT_GET_CONFIGURATION:
				Log.i(TAG, "MiniMatch Maker access...");
				getConfiguration(cloudinfo);
				break;
			case CommunicationPersistence.EVENT_GET_CONFIGURATION_RESPONSE:
				Log.i(TAG, "Response from MinimatchMaker");
				getConfigurationResponse(cloudinfo);
				break;
			case CommunicationPersistence.EVENT_USER_DETECTED:
				Log.i(TAG,"Event received from NFC");
				//Launch Device Reporter
				getFeatures(cloudinfo);
				break;
			default:
				Log.i(TAG, "No hace nada");
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private void configuration(CloudIntent cloudinfo) {
		// TODO Auto-generated method stub
		String[] listaids;

		try {
			Log.i("CLOUD4ALL", "Botón pulsado");
			CloudIntent intent = new CloudIntent(
					CommunicationPersistence.ACTION_SYSTEM_SETTING_HANDLER,
					CommunicationPersistence.EVENT_CONFIGURE_SYSTEM_SETTINGS,
					CommunicationPersistence.MODULE_ORCHESTRATOR);

			listaids = cloudinfo.getArrayIds();

			for (int i = 0; i < listaids.length; i++) {
				Log.i(TAG, "Index:" + i);
				Log.i(TAG, "Orchestrator. Parametros: id: " + listaids[i]
						+ ", value: " + cloudinfo.getValue(listaids[i]));
				intent.setParams(listaids[i], cloudinfo.getValue(listaids[i]));
			}
			sendBroadcast(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void restore(CloudIntent cloudinfo) {
		// TODO Auto-generated method stub
		String[] listaids;
		try {
			Log.i("CLOUD4ALL", "Botón pulsado");
			CloudIntent intent = new CloudIntent(
					CommunicationPersistence.ACTION_SYSTEM_SETTING_HANDLER,
					CommunicationPersistence.EVENT_RESTORE_SYSTEM_SETTINGS,
					CommunicationPersistence.MODULE_ORCHESTRATOR);
			listaids = cloudinfo.getArrayIds();

			for (int i = 0; i < listaids.length; i++) {
				Log.i(TAG, "Index:" + i);
				Log.i(TAG, "Orchestrator. Parametros: id: " + listaids[i]
						+ ", value: " + cloudinfo.getValue(listaids[i]));
				intent.setParams(listaids[i], cloudinfo.getValue(listaids[i]));
			}
			sendBroadcast(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getFeatures(CloudIntent cloudinfo) {
		try {
			CloudIntent intent = new CloudIntent(
					CommunicationPersistence.ACTION_DEVICE_REPORTER,
					CommunicationPersistence.EVENT_GET_FEATURES,
					CommunicationPersistence.MODULE_ORCHESTRATOR);

			intent.setParams("ReporterType", "ROOT");

			sendBroadcast(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getConfiguration(CloudIntent cloudinfo){
		try {
			CloudIntent intent = new CloudIntent(
					CommunicationPersistence.ACTION_MINI_MATCH_MAKER,
					CommunicationPersistence.EVENT_GET_CONFIGURATION,
					CommunicationPersistence.MODULE_ORCHESTRATOR);

			intent.setParams("ReporterType", "ROOT");

			sendBroadcast(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    private void getConfigurationResponse(CloudIntent cloudinfo){
    	String[] listaids;

			Log.i("CLOUD4ALL", "Botón pulsado");
			CloudIntent intent = new CloudIntent(
					CommunicationPersistence.ACTION_SYSTEM_SETTING_HANDLER,
					CommunicationPersistence.EVENT_CONFIGURE_SYSTEM_SETTINGS,
					CommunicationPersistence.MODULE_ORCHESTRATOR);

		try{
			listaids = cloudinfo.getArrayIds();

			for (int i = 0; i < listaids.length; i++) {
				Log.i(TAG, "Index:" + i);
				Log.i(TAG, "Orchestrator. Parametros: id: " + listaids[i]
						+ ", value: " + cloudinfo.getValue(listaids[i]));
				intent.setParams(listaids[i], cloudinfo.getValue(listaids[i]));
			}
			sendBroadcast(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}

}
