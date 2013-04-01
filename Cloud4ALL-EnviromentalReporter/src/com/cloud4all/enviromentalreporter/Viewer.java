package com.cloud4all.enviromentalreporter;

/*

Viewer
This class is a example about how to use Enviromental reporter service.	

Copyright (c) 2013, Technosite R&D
All rights reserved.
The research leading to these results has received funding from the 

European Union's Seventh Framework Programme (FP7/2007-2013) under 

grant agreement n° 289016

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are 

met:

 * Redistributions of source code must retain the above copyright 

notice, this
   list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright 

notice, 
   this list of conditions and the following disclaimer in the 

documentation 
   and/or other materials provided with the distribution. 
 * Neither the name of Technosite R&D nor the names of its 

contributors may 
   be used to endorse or promote products derived from this software 

without 
   specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 

"AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 

THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 

PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 

LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 

CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 

GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 

HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT 

LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF 

THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


 */
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Viewer extends Activity {

	private TextView textResults = null;
	private Button btnRefresh= null;
	private Handler mHandler = new Handler();
	private boolean refreshFlag = false;
	private EnviromentalReporterEngine reporter = null;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewer);
		textResults = (TextView) findViewById(R.id.txtResults);
		btnRefresh = (Button) findViewById(R.id.btnRefresh);
		btnRefresh.setEnabled(false);
		doBindService();
		mHandler.removeCallbacks(showData);
		mHandler.postDelayed(showData, 1000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_viewer, menu);
		return true;
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacks(showData);
	}

	// ** Interface


	private Runnable showData = new Runnable() {
		public void run() {
			if (refreshFlag) {
				textResults.setText("Enviromental reporter\n\nBrightness: "+ String.valueOf( (int) reporter.getBrightness()) + "\nNoise: " + String.valueOf(reporter.getNoise()));

			}

			mHandler.removeCallbacks(showData);
			mHandler.postDelayed(this, 900);
		}
	};


	public void refreshControl(View view) {
		if (refreshFlag) {
			refreshFlag = false;
			btnRefresh.setText("Start");
			textResults.setText("Press the start button for more data");
			reporter.stopSensors();
		} else {
			refreshFlag = true;

			btnRefresh.setText("Stop");
			reporter.initSensors(this);
		}
	}

	// ** Service connection

	private EnviromentalReporterService sEnviromentalReporter;

	private ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder binder) {
			sEnviromentalReporter = ((EnviromentalReporterService.MyBinder) binder).getService();
			if (sEnviromentalReporter != null) {
				btnRefresh.setEnabled(true);
				textResults.setText("Service connected. Please, touch the Start button.");
				reporter = sEnviromentalReporter.getEngine();
			}
		}

		public void onServiceDisconnected(ComponentName className) {
			reporter.stopSensors();	
			sEnviromentalReporter = null;
			btnRefresh.setEnabled(false);
		}
	};

	// starts the service connection using bindings
	void doBindService() {
		bindService(new Intent(this, EnviromentalReporterService.class), mConnection,
				Context.BIND_AUTO_CREATE);
	}



}
