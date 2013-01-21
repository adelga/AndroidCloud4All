package test.receiver;

import java.io.File;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.sax.StartElementListener;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		Log.e("Test Broadcast","RECIBIDO!");



		
		intent.setClass(context, MyService.class);
		context.startService(intent);
		
		
	}

}
