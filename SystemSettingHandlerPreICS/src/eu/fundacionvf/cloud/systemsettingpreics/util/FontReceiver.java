/*
FontReceiver
This class listen the special action which is launched when it get want to change the font scale and create an SystemAlarm to mantain the scale font.

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

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class FontReceiver extends BroadcastReceiver {
	
	private static final long REPEAT_TIME = 1000 * 10;


	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("MYRECEIVER", "on receive " +  intent.getAction());
		float scale= intent.getExtras().getFloat("scale");
		Log.d("MYRECEIVER", "on receive " +  scale);
		AlarmManager service = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, StartFontServiceReceiver.class);
		i.putExtra("scale", scale);
		PendingIntent pending = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
		Calendar cal = Calendar.getInstance();
		// Start 30 seconds after boot completed
		cal.add(Calendar.SECOND, 2);
		//
		// Fetch every 30 seconds
		// InexactRepeating allows Android to optimize the energy consumption
		service.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), REPEAT_TIME, pending);

		// service.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
		// REPEAT_TIME, pending);
	}
}