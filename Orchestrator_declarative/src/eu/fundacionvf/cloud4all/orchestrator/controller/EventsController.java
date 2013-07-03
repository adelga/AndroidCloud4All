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

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import eu.fundacionvf.cloud4all.orchestrator.matching_variables.Matching;
import eu.fundacionvf.cloud4all.orchestrator.persistence.Action;
import eu.fundacionvf.cloud4all.orchestrator.persistence.Param;
import eu.fundacionvf.cloud4all.orchestrator.persistence.Persistence;
import eu.fundacionvf.cloud4all.orchestrator.persistence.PersistenceXML;
import eu.fundacionvf.cloud4all.orchestrator.persistence.Request;
import eu.fundacionvf.cloud4all.orchestrator.persistence.Response;
import eu.fundacionvf.cloud4all.orchestrator.persistence.Session;
import eu.fundacionvf.cloud4all.orchestrator.persistence.Trigger;
import eu.fundacionvf.cloud4all.orchestrator.persistence.TriggerEvent;
import eu.fundacionvf.cloud4all.orchestrator.persistence.TriggerEventParam;
import eu.fundacionvf.cloud4all.orchestrator.util.CloudIntent;

public class EventsController extends Service {
	
	private static final String TAG = "CLOUD4ALL";
	private static final String ZERO_STRING = "0";
	private static final int ZERO_INTEGER = 0;
	public int idTrigger_active;
	public int idAction_active;
	PersistenceXML pXML;
	Persistence persistence;
	Session session;
	eu.fundacionvf.cloud4all.orchestrator.persistence.Process process;
	
	Matching matching = new Matching();
	private int idAction;
    
	//VARIABLES FOR HANDLER
	private Looper mServiceLooper;
	private ServiceHandler mServiceHandler;

	// Handler that receives messages from the thread
	private final class ServiceHandler extends Handler {
		public ServiceHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			onHandleIntent((Intent) msg.obj);
			// Stop the service using the startId, so that we don't stop
			// the service in the middle of handling another job
			stopSelf(msg.arg1);

		}

		//Method to manage the intents
		protected void onHandleIntent(Intent intent) {
			// TODO Auto-generated method stub
			try {
				pXML = new PersistenceXML();
				persistence = pXML.getPersistence();
				session = pXML.getSession();
				CloudIntent cloudinfo = CloudIntent.intentToCloudIntent(intent);
				Log.i(TAG, "Parametros recibidos");
				Log.i(TAG,"recibidos recibidos: "+ cloudinfo.getStringExtra("params"));
				Log.i(TAG, "recibidos idaction: "+cloudinfo.getIdAction());
				int idEvent = cloudinfo.getIdEvent();
				int idProcess = persistence.findIdProccessByIdTrigger(idEvent);
				
		
				// CASE REQUEST
				if (persistence.isRequest(idEvent)) {
					Log.i(TAG, "Request event...");
					
					idTrigger_active=idEvent;
					Log.i(TAG, cloudinfo.getStringExtra("params"));
					process=persistence.getProcessByIdProcess(idProcess);
					

					String[] key=cloudinfo.getArrayIds();
					
					TriggerEvent triggerevent=new TriggerEvent();
					triggerevent.setIdTriggerEvent(idEvent);
					triggerevent.setTriggerEventParam(key.length);
					TriggerEventParam[] param=new TriggerEventParam[key.length];
										

					for(int j=0;j<key.length;j++){
						Log.i(TAG, key[j]);
						Log.i(TAG, cloudinfo.getValue(key[j]));
						param[j]=new TriggerEventParam(key[j],cloudinfo.getValue(key[j]));
						triggerevent.setTriggerEventParam(param[j], j);
						
		
					}
						
					
					process.setTriggerEvent(triggerevent);
									
					if (!session.haveProcesses()) {						
						session.newSession();
						session.setProcess(process, 0);
						for (int i = 0; i < session.getProcesses().length; i++) {
							for (int j = 0; j < session.getProcesses()[i].getListActions().length; j++) {
								session.getProcesses()[i].getListActions()[j].response = new Response(ZERO_INTEGER, ZERO_INTEGER, ZERO_STRING);
							}
						}
						pXML.writeXML(session);
						// En este caso deberia ejecutar el paso de variables
						startFirstAction(persistence.getActionsByIdTrigger(idEvent),idProcess);
					} else {
						if (session.processExist(idProcess)) {
							
							session.getProcessByIdProcess(idProcess).getTriggerEvent().setIdTriggerEvent(idEvent);
							session.getProcessByIdProcess(idProcess).getTriggerEvent().setTriggerEventParam(key.length);
												
							for(int j=0;j<key.length;j++){
								session.getProcessByIdProcess(idProcess).getTriggerEvent().setTriggerEventParam(param[j], j);							
							}
							
							pXML.writeXML(session);
							startFirstAction(persistence.getActionsByIdTrigger(idEvent),cloudinfo.getIdAction());
						} else {
							session.addProcess(process);
							pXML.writeXML(session);
							startFirstAction(persistence.getActionsByIdTrigger(idEvent),cloudinfo.getIdAction());
						}
					}
				}
				// CASE RESPONSE
				else {
					Log.i(TAG, "Response Event");
					// Store the params received for the origin module
					session.addParameters(cloudinfo);
					pXML.writeXML(session);
					startNextAction(cloudinfo.getIdAction());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
		// Start up the thread running the service. Note that we create a
		// separate thread because the service normally runs in the process's
		// main thread, which we don't want to block. We also make it
		// background priority so CPU-intensive work will not disrupt our UI.
		
		HandlerThread thread = new HandlerThread("ServiceStartArguments",Process.THREAD_PRIORITY_BACKGROUND);
		thread.start();

		// Get the HandlerThread's Looper and use it for our Handler
		mServiceLooper = thread.getLooper();
		mServiceHandler = new ServiceHandler(mServiceLooper);

		//Create the random idAction
		int M = 1;
		int N = 100;
		idAction = (int) Math.floor(Math.random() * (N - M + 1) + M); // Valor entre M y N, ambos incluidos.

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		// For each start request, send a message to start a job and deliver the
		// start ID so we know which request we're stopping when we finish the
		// job
		
		Message msg = mServiceHandler.obtainMessage();
		msg.arg1 = startId;
		msg.obj = intent;
		mServiceHandler.sendMessage(msg);

		// If we get killed, after returning from here, restart
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {

	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "Se realiza un onDestroy");
	}

	private void startFirstAction(Action[] actions, int actionRec) {
		// TODO Auto-generated method stub
		int indexProcess = session.indexOfProcess(actionRec);
		try {
			idAction++;
			idAction_active=idAction;
			Log.i(TAG, "idaction to send:" + idAction);
			session.getProcesses()[indexProcess].getListActions()[0].setIdAction(idAction);
			pXML.writeXML(session);
			CloudIntent intent = new CloudIntent(actions[0].getRequest().getIdModule(), actions[0].getRequest().getIdEvent(),idAction);
			Param[] params = actions[0].getRequest().getParams();			
			String value;
			for (int j = 0; j < params.length; j++) {
				Log.i(TAG, "idTrigger active:"+idTrigger_active);
				Log.i(TAG, "idAction active:"+idAction_active);
				Log.i(TAG, "param:"+params[j].getValue());
				value=matching.getValue(idTrigger_active,idAction_active,params[j].getValue(),session);
				Log.i(TAG, "param value:"+value);
				intent.setParams(params[j].getKey(),value);
			}
			Log.i(TAG, "param intent getExtra:"+intent.getStringExtra("params"));
			sendBroadcast(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void startNextAction(int actionRec) {
		// TODO Auto-generated method stub
		Log.i(TAG, "idaction to send:" + actionRec);
		int idProcess = session.getIdProcessByIdAction(actionRec);
		if (session.nextActionExist(actionRec)) {
			Log.i(TAG, "Start next action");
			Action[] actions = session.getActionsByIdProcess(actionRec);
			int indexProcess = session.indexOfProcess(actionRec);
			int indexAction = session.indexOfAction(actionRec);
			try {
				idAction++;
				idAction_active=idAction;
				session.getProcesses()[indexProcess].getListActions()[indexAction + 1].setIdAction(idAction);
				pXML.writeXML(session);
				CloudIntent intent = new CloudIntent(actions[indexAction + 1].getRequest().getIdModule(), actions[indexAction + 1].getRequest().getIdEvent(), idAction);
				Param[] params = actions[indexAction + 1].getRequest().getParams();
				String value;
				for (int j = 0; j < params.length; j++) {
					Log.i(TAG, "idTrigger active:"+idTrigger_active);
					Log.i(TAG, "idAction active:"+idAction_active);
					Log.i(TAG, "param:"+params[j].getValue());
					value=matching.getValue(idTrigger_active,idAction_active,params[j].getValue(),session);
					Log.i(TAG, "param value:"+value);
					intent.setParams(params[j].getKey(), value);
				}
				Log.i(TAG, "param intent getExtra:"+intent.getStringExtra("params"));
				sendBroadcast(intent);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}

}
