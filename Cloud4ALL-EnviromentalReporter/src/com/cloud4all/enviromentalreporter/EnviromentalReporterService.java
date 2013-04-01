package com.cloud4all.enviromentalreporter;
/*


EnviromentalReporterService
This class is the interface for the Enviromental reporter connnection service.
Use binding connection service to use it.	

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

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class EnviromentalReporterService extends Service{

	// ** Service management

	private final IBinder mBinder = new MyBinder();
	private EnviromentalReporterEngine enviromentalReporterEngine = null;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

	public class MyBinder extends Binder {
		EnviromentalReporterService getService() {
			return EnviromentalReporterService.this;
		}
	}

	// ** Method for engine

	public EnviromentalReporterEngine getEngine() {
		enviromentalReporterEngine = new EnviromentalReporterEngine(getApplicationContext());
		return enviromentalReporterEngine ; 
	}

	// ** Methods for results

	// method for getting data from the Enviromental reporter
	public String getResults(EnviromentalReporter.TypeSensor typeOfData) {
		enviromentalReporterEngine = new EnviromentalReporterEngine(getApplicationContext());
		String results = null;
		switch (typeOfData) {
		case BRIGHTNESS :
			results = String.valueOf(enviromentalReporterEngine.getBrightness());
			break;
		case NOISE :
			results = String.valueOf(enviromentalReporterEngine.getNoise());
			break;
		}
		return results;
	}

	// method for getting data from the Enviromental reporter using a custom context
	public String getResultsWithContext(Context context, EnviromentalReporter.TypeSensor typeOfData) {
		enviromentalReporterEngine = new EnviromentalReporterEngine(context);
		String results = null;
		switch (typeOfData) {
		case BRIGHTNESS:
			results = String.valueOf(enviromentalReporterEngine.getBrightness());
			break;
		case NOISE :
			results = String.valueOf(enviromentalReporterEngine.getNoise());
			break;
		}
		return results;
	}

}
