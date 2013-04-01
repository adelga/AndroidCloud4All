package com.cloud4all.minimatchmaker;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/*

MiniMatchMakerService
This class is a communication layout between Flow manager and Mini match maker's kernel.	

Copyright (c) 2013, Technosite R&D
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n� 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

�* Redistributions of source code must retain the above copyright notice, this�list of conditions and the following disclaimer. 
�* Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
�* Neither the name of Technosite R&D nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


 */

public class MiniMatchMakerService extends Service {

	public interface MiniMatchMakerListener {
		void MiniMatchMakerResponse(JSONObject data);
	}


	private final IBinder mBinder = new MyBinder();
	private MiniMatchMakerEngine engine = null;
	private MiniMatchMakerListener listener = null;

	@Override
	public void onCreate() {
		super.onCreate();
		engine = new MiniMatchMakerEngine(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();	
	}
		
		@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
			try {
				HashMap<String,String> list = new HashMap<String,String>();
				CloudIntent cloudinfo = CloudIntent.intentToCloudIntent(intent);
				int event = cloudinfo.getIdEvent();
				manageArgumentsInPetition(cloudinfo );
								switch (event) {
								case CommunicationPersistence.EVENT_ARE_REPORTER :
									list.put("are","yes");
																		sendCommunication(CommunicationPersistence.EVENT_ARE_REPORTER_RESPONSE,list );
					break;
				case CommunicationPersistence.EVENT_STORAGE_REPORTER  :
					list.put("message","OK");
					sendCommunication(CommunicationPersistence.EVENT_STORAGE_REPORTER_RESPONSE ,list );
					break;
				case CommunicationPersistence.EVENT_GET_CONFIGURATION   :
					list.put("type","complete");
					list.put("brightnessMode","0");
					list.put("brightness","5");
					list.put("fontSize","15");
					sendCommunication(CommunicationPersistence.EVENT_GET_CONFIGURATION_RESPONSE ,list );
					break;
					default :
						break;
				}
								// reset arguments from petition
								arguments = null;
						} catch (Exception e) {
				Log.e("MiniMatchMakerService error in onStartCommand", "Error managing the intent.\n"+e);
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
		MiniMatchMakerService getService() {
			return MiniMatchMakerService.this;
		}
	}

	public MiniMatchMakerEngine getEngine() {
		return engine;
	}

	// ** Listener

	public void registerListener(MiniMatchMakerListener listenerObject) {
		listener = listenerObject;
		Log.i("MiniMatchMakerService", "Object registered as listener of the service");
	}

	// ** Input method

	public void insertData(JSONObject data) {
		if (engine == null) engine = new MiniMatchMakerEngine(this);
		Log.i("MiniMatchMakerService", "Sending data to the engine.");
		engine.receiveData(data);
	}

	// ** Output method

	public void ResponseData(JSONObject data) {
		if (listener!=null) {
			Log.i("MiniMatchMakerService", "Sending data to the listener.");
			listener.MiniMatchMakerResponse(data);		
		}
	}

	// ** Methods for communication with Orquestator

	private HashMap<String,String> arguments = null;
	
	private void manageArgumentsInPetition(CloudIntent intent) {
		String[] args;
		try {
			arguments = new HashMap<String,String>();
			args = intent.getArrayIds();
			for (int i = 0; i < args.length; i++){
				String paramName = args[i];
				String paramValue = intent.getValue(args[i]);
				arguments.put(paramName, paramValue);
			}
					} catch (Exception e) {
			Log.e("MiniMatchMakerService error in ManagePettition", "Error in Intent management.\n" +e);
		}
	}

	private void sendCommunication(int CloudEvent, Map<String, String> params) {
		try {
			CloudIntent intent = new CloudIntent(CommunicationPersistence.ACTION_ORCHESTRATOR, CloudEvent,CommunicationPersistence.MODULE_MINI_MATCH_MAKER);
			// manage params
			Iterator<Map.Entry<String,String>> it = params.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> e = (Map.Entry<String, String>) it.next();
				intent.setParams(e.getKey(), e.getValue());	
			}
				
			
			Context ct = getApplicationContext();
			ct.sendBroadcast(intent);
		} catch (Exception e) {
			Log.e("MiniMatchMakerBroadcastManager error in sendCommunication", "Error sending broadcast.\n" +e);
		}
		}

	

	
}
