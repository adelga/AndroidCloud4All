/*
Unlock class
This class extends from Activity, and it's called when the user want see the app which launch the notification, if the device doesn't have lock screen, this activity la 	

Copyright (c) 2013, Vodafone Spain Foundation
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Vodafone Spain Foundation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/
package eu.fundacionvf.cloud.accessibilityservicenotify.popup;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Notification;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.WindowManager.LayoutParams;

public class Unlock extends Activity {

	Notification notif;
	WaitForUnlock wFU;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(LayoutParams.FLAG_DISMISS_KEYGUARD);
		wFU = new WaitForUnlock();
		wFU.execute();
	}
		
	private class WaitForUnlock extends AsyncTask<Void,Void,Boolean>{
		@Override
		protected Boolean doInBackground(Void... params) {
			while( ((KeyguardManager)getSystemService(KEYGUARD_SERVICE)).inKeyguardRestrictedInputMode() ){
				try{
					wait(100);
				}catch(Exception e){
					
				}
				if( !((PowerManager)getSystemService(POWER_SERVICE)).isScreenOn() )
					return false;
			}
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result){
			finish();
			if( result.booleanValue() ){
				try{
					
				}catch(Exception e){
					
				}
			}
		}
	}
}
