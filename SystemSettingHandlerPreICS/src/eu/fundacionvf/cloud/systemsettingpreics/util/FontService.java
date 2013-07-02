/*
FontService
This class extends from service, invoke some natives Android class to change the configuration font scale.

Copyright (c) 2013, Vodafone Spain Foundation
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Vodafone Spain Foundation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 */

package eu.fundacionvf.cloud.systemsettingpreics.util;

import java.lang.reflect.Method;
import java.util.List;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.util.Log;

public class FontService extends Service {
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		onHandleIntent(intent);

		return super.onStartCommand(intent, flags, startId);
	}

	String tag = "SystemSettingHandler FONT SERVICE";
	private String h = "updatePersistentConfiguration";
	private float scale = 1.0f;



	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub

		try {
			Log.d(tag, "scalado de fuente que leemos: "
					+ intent.getExtras().getFloat("scale"));
			scale=intent.getExtras().getFloat("scale");
			ActivityManager am = (ActivityManager) this
					.getSystemService(ACTIVITY_SERVICE);
			// get the info from the currently running task
			List<ActivityManager.RunningTaskInfo> taskInfo = am
					.getRunningTasks(1);

			Log.d("Service LOG", "CURRENT Activity ::"
					+ taskInfo.get(0).topActivity.getClassName());

			ComponentName componentInfo = taskInfo.get(0).topActivity;

			Object localObject1 = Class
					.forName("android.app.ActivityManagerNative")
					.getMethod("getDefault", new Class[0])
					.invoke(null, new Object[0]);
			Class localClass = localObject1.getClass();
			Log.d("Service LOG", "class " + localClass.toString());

			Configuration localConfiguration = (Configuration) localClass
					.getMethod("getConfiguration", new Class[0]).invoke(
							localObject1, new Object[0]);
			Log.d("Service LOG", "el font scale estaba a:  "
					+ localConfiguration.fontScale);

			localConfiguration.fontScale = scale;

			String str = "updateConfiguration";

			Class[] arrayOfClass2;
			arrayOfClass2 = new Class[1];
			arrayOfClass2[0] = Configuration.class;

			Method localMethod = null;

			Object localObject2 = localClass.getMethod(str, arrayOfClass2);

			Object[] arrayOfObject = new Object[1];
			arrayOfObject[0] = localConfiguration;
			((Method) localObject2).invoke(localObject1, arrayOfObject);

			 SharedPreferences.Editor localEditor =
			 this.getSharedPreferences("app_opts", 0).edit();
			 localEditor.putLong("lsfsut", System.currentTimeMillis());
			 localEditor.apply();

//			Log.d("Service LOg", "version api : " + VERSION.SDK_INT);
//			Class[] arrayOfClass1 = new Class[1];
//			arrayOfClass1[0] = Configuration.class;
//			try {
//				localMethod = localClass.getMethod(h, arrayOfClass1);
//			} catch (Exception e) {
//				Log.d("SERVICE LOG", h + "no se ha podido invocar");
//				e.printStackTrace();
//			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

}
