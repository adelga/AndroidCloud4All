/*
 * system_setting_handler for Android version since 4.0 (API 14, ICS).
 * 
 * It's necesary put the apk generate in /system/app to get access to all configuration files
 */

package eu.fundacionvf.cloud.systemsetting;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;
import eu.fundacionvf.cloud.systemsetting.persistence.CommunicationPersistence;
import eu.fundacionvf.cloud.systemsetting.util.SystemSettingUtil;
import eu.fundacionvf.cloud.systemsetting.util.SystemSoundsUtil;

public class SettingHandlerService extends Service {

	private String MESSAGE_OK = "OK";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		Log.i("SettingsHandler", "OnCreate: Policy Changed");
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
			Log.e("Receiver", "startComand");
			CloudIntent cloudinfo = CloudIntent.intentToCloudIntent(intent);
			int event = cloudinfo.getIdEvent();
			switch (event) {
			case CommunicationPersistence.EVENT_CONFIGURE_SYSTEM_SETTINGS:
				configure(cloudinfo);
				response(MESSAGE_OK);
				break;
			case CommunicationPersistence.EVENT_RESTORE_SYSTEM_SETTINGS:
				retore(cloudinfo);
				response(MESSAGE_OK);
				break;

			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private void retore(CloudIntent cloudinfo) {
		try{
		SystemSoundsUtil sound = new SystemSoundsUtil(this);
		
		String notificationsound= cloudinfo.getValue("notifcation_sound");
		if(notificationsound!=null){
			sound.restoreNotificationSound();
		}
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void response(String msg) {
		try {
			CloudIntent intent = new CloudIntent(
					CommunicationPersistence.ACTION_ORCHESTRATOR,
					CommunicationPersistence.EVENT_CONFIGURE_SYSTEM_SETTINGS_RESPONSE,
					CommunicationPersistence.MODULE_SYSTEM_SETTING_HANDLER);

			intent.setParams("message", msg);
			sendBroadcast(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void configure(CloudIntent cloudinfo) {
		// TODO Auto-generated method stub
		String[] listaids;
		try {
			listaids = cloudinfo.getArrayIds();

			for (int i = 0; i < listaids.length; i++) {
				Log.e("Receiver", "Parametros: id: " + listaids[i]
						+ ", value: " + cloudinfo.getValue(listaids[i]));

			}

			SystemSettingUtil system = new SystemSettingUtil(this);

			String brillo = cloudinfo.getValue("brightness");
			String brilloModo = cloudinfo.getValue("brightnessMode");
			if (brillo != null && brilloModo != null) {
				system.changeBrightness(Integer.parseInt(brilloModo),
						Integer.parseInt(brillo));
			}

			SystemSoundsUtil sound = new SystemSoundsUtil(this);

			String notificationsound = cloudinfo.getValue("notification_sound");
			if (notificationsound != null) {
				sound.changeNotificationSound(notificationsound);
			}

		} catch (JSONException e) {
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
