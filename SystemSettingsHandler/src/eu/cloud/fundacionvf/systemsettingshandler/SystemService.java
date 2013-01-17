package eu.cloud.fundacionvf.systemsettingshandler;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class SystemService extends Service {
	private final String TAG = "SystemSettingHandler";
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		Log.d(TAG, "ssssssssssssssssssssssssssssssssssss");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Bundle extras = intent.getExtras();
		Log.d(TAG, extras.getString("Params"));
		
		return START_STICKY;
		
		
	}
}
