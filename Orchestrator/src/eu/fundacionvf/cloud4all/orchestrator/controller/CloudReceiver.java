package eu.fundacionvf.cloud4all.orchestrator.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CloudReceiver extends BroadcastReceiver {
	
	public static final String CLOUD_RECEIVER_ACTION = "CloudOrchestrator";
	public static final String TAG = "CLOUD4ALL";
	
	//private BroadcastReceiver cloudReceiver;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.e("Test Broadcast","RECIBIDO!");

		intent.setClass(context, EventsController.class);
		context.startService(intent);

	}


}
