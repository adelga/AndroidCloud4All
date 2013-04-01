package com.cloud4all.environmentalreporter;

import android.media.MediaRecorder;
import android.os.Handler;
import android.util.Log;

/*

NoiseSensorEngine
This class manages environmental sound detection	

Copyright (c) 2013, Technosite R&D
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Technosite R&D nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


 */

public class NoiseSensorEngine {

	// ** Attributes

	private int _amplitude = 0;
	public float getAmplitude() {
		int tmp = (int) getFinalAmplitude();
		if (tmp!= 0) _amplitude = tmp;		
		return _amplitude;
	}

	private int _refreshTime = 1000;
	public void setRefreshTime(int timeInMS) {
		_refreshTime = timeInMS;
	}

	public int getRefreshTime() {
		return _refreshTime ;
	}

	// ** management methods

	public void startService() {
		startTimer();
	}

	public void stopService() {
		stopTimer();
	}

	public void startMicrophone() {
		start();		
	}

	public void stopMicrophone() {
		stop();		
	}

	// ** timer management

	private Handler mHandler = new Handler();
	private int timerStep = 0;
	private int timerMaxSteps = 0;
	private int timerInterval = 100;


	private void startTimer() {
		timerMaxSteps = _refreshTime / timerInterval;
		timerStep = 0;
		mHandler.removeCallbacks(timerEvent);
		mHandler.postDelayed(timerEvent, timerInterval);
	}

	private void stopTimer() {
		mHandler.removeCallbacks(timerEvent);
		stopMicrophone();
	}

	private Runnable timerEvent = new Runnable() {
		public void run() {
			timerStep++;
			if (timerStep >= timerMaxSteps) {
				stopMicrophone();
				startMicrophone();
				timerStep = 0;
			}  

			mHandler.removeCallbacks(timerEvent);
			mHandler.postDelayed(this, timerInterval);
		}
	};



	// ** Microphone record

	static final private double EMA_FILTER = 0.6;    
	private MediaRecorder mRecorder = null;  
	private double mEMA = 0.0;    

	public void start() {  
		if (mRecorder == null) {  
			mRecorder = new MediaRecorder();  
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);  
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);  
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);  
			mRecorder.setOutputFile("/dev/null");
			mRecorder.setMaxDuration(800);
			try {
				mRecorder.prepare();  
			} catch (Exception e) {
				Log.e("NoiseSensorService Error in startMicrophone", "Error preparing MediaRecorder object.\n"+ e);
			}
			mRecorder.start();  
			mEMA = 0.0;
			Log.i("NoiseSensorService startMicrophone", "Starting the microphone");
		}  
	}    

	public void stop() {  
		if (mRecorder != null) {  
			mRecorder.stop();   
			mRecorder.release();  
			mRecorder = null;
			Log.i("NoiseSensorService stopMicrophone", "Stopping the microphone");
		}  
	}    

	public double getBufferAmplitude() {  
		if (mRecorder != null)  return (mRecorder.getMaxAmplitude()/2700.0);  
		else  return 0;    
	}    

	public double getFinalAmplitude() {  
		double amp = getBufferAmplitude();  
		mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;  
		return mEMA ;  
	}   





}
