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

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}

}
