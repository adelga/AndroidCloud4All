package com.cloud4all.devicereporter;

/*

Viewer
This class is a sample code to use Device reporter service and how to show all data from the Device reporter.	

	Copyright (c) 2013, Technosite R&D
	All rights reserved.
The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, 
   this list of conditions and the following disclaimer in the documentation 
   and/or other materials provided with the distribution. 
 * Neither the name of Technosite R&D nor the names of its contributors may 
   be used to endorse or promote products derived from this software without 
   specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


 */
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class Viewer extends Activity  {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewer);
		doBindService();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_viewer, menu);
		return true;
	}

	/*
	 * Methods for Logger engine to show results
	 */


	public boolean outputOnConsole = true;
	public boolean outputOnScreen = true;


	public void showLogMessage(String data) {
		if (this.outputOnConsole) {
			Log.i("Debug information", "\n - " + data); 
		}
		if (this.outputOnScreen) {
			TableLayout tl = (TableLayout)findViewById(R.id.logTable);
			TableRow tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			TextView td = new TextView(this);
			td.setText(data);
			td.setFocusable(true);
			// Adding new row
			tr.addView(td);
			tl.addView(tr,new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
		}
	}

	public void showMapResults(Map<String, String> mapParam) {
		Map<String,String> mapData = new TreeMap<String, String>(mapParam);
		String logString = "";
		Iterator<Map.Entry<String,String>> it = mapData.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> e = (Map.Entry<String, String>) it.next();
			logString = e.getKey() + ": " + e.getValue();
			this.showLogMessage(logString);
		}
	}

	// Shows data from Device reporter
	public void showDeviceReporterResults() {
		showMapResults((Map<String,String>) sDeviceReporter.getResults()); 
	}

	// ** Service connection

	private DeviceReporterService sDeviceReporter;

	private ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder binder) {
			sDeviceReporter = ((DeviceReporterService.MyBinder) binder).getService();
			if (sDeviceReporter != null) showDeviceReporterResults();
		}

		public void onServiceDisconnected(ComponentName className) {
			sDeviceReporter = null;
		}
	};

	// starts the service connection using bindings
	void doBindService() {
		bindService(new Intent(this, DeviceReporterService.class), mConnection,
				Context.BIND_AUTO_CREATE);
	}

}