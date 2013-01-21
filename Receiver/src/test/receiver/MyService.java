package test.receiver;

import org.json.JSONException;

import test.receiver.persistence.CommunicationPersistence;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

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
			Log.e("Receiver", "startComand");
			CloudIntent cloudinfo = CloudIntent.intentToCloudIntent(intent);
			int event = cloudinfo.getIdEvent();
			switch (event) {
			case CommunicationPersistence.EVENT_CONFIGURE_SYSTEM_SETTINGS:
				configurar(cloudinfo);
				break;

			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private void configurar(CloudIntent cloudinfo) {
		// TODO Auto-generated method stub
		String[] listaids;
		try {
			listaids = cloudinfo.getArrayIds();

			for (int i = 0; i < listaids.length; i++){
				Log.e("Receiver", "Parametros: id: "+ listaids[i] + ", value: "+ cloudinfo.getValue(listaids[i]));
				
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
