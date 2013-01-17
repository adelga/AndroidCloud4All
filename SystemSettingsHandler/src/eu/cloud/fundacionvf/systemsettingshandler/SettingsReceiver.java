package eu.cloud.fundacionvf.systemsettingshandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SettingsReceiver extends BroadcastReceiver {
	private final String action = "eu.cloud.android.HANDLER";
	private final String TAG = "SystemSettingHandler";
	
	final int CHANGESYSTEMSETTINGS = 12;
	final int RESTORESYSTEMSETTINGS = 13;

	private int ORCHESTRATOR=1;
	
	private Context packageContext;

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		try {
			packageContext=arg0;
			Log.d(TAG, "action: " + arg1.getAction());
			Bundle extras = arg1.getExtras();
			int event = extras.getInt("IdEvent");
			Log.d(TAG, "evento: " + event);
			int module = extras.getInt("IDModule");
			if (module==ORCHESTRATOR ) {
				switch (event) {
				case CHANGESYSTEMSETTINGS:
					Intent intent= new Intent(packageContext, SystemService.class);
					intent.putExtra("Params", extras.getString("Params"));
					packageContext.startService(intent);
					break;

				default:
					break;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
