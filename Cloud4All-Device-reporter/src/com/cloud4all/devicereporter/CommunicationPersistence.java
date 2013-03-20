package com.cloud4all.devicereporter;

/*

CommunicationPersistence
This class provides several constants for persistence communication	

Copyright (c) 2013, Technosite R&D
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Technosite R&D nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


*/
public class CommunicationPersistence {


	public final static String ACTION_ORCHESTRATOR= "cloudOrchestrator";
	public final static String ACTION_DEVICE_REPORTER= "cloudDeviceReporter";
	public final static String ACTION_NFC_USER_LISTENER= "cloudNFCListener";
	public final static String ACTION_SYSTEM_SETTING_HANDLER ="cloudSystemSetting";
	public final static String ACTION_MINI_MATCH_MAKER ="cloudMiniMatchMaker";
	public final static String ACTION_HAPTIC_FEEDBACK_HANDLER ="cloudHapticFeedback";
	public final static String ACTION_HTTP_CLIENT ="cloudHttpClient";

	public final static int EVENT_CONFIGURE_SYSTEM_SETTINGS = 110;
	public final static int EVENT_CONFIGURE_SYSTEM_SETTINGS_RESPONSE = 111;

	public final static int EVENT_RESTORE_SYSTEM_SETTINGS = 115;
	public final static int EVENT_RESTORE_SYSTEM_SETTINGS_RESPONSE = 116;
	
	public final static int EVENT_VALIDATE_USER = 170;
	public final static int EVENT_VALIDATE_USER_RESPONSE = 171;
	
	public final static int EVENT_GET_FEATURES = 130;
	public final static int EVENT_GET_FEATURES_RESPONSE = 131;
	
	public final static int EVENT_CONFIGURE_HAPTIC_FEEDBACK = 160;
	public final static int EVENT_CONFIGURE_HAPTIC_FEEDBACK_RESPONSE = 161;
	
	public final static int EVENT_RESTORE_HAPTIC_FEEDBACK = 165;
	public final static int EVENT_RESTORE_HAPTIC_FEEDBACK_RESPONSE = 166;
	
	public final static int EVENT_GET_CONFIGURATION = 227;
	public final static int EVENT_GET_CONFIGURATION_RESPONSE = 228;
	
	public final static int EVENT_GET_REPORTER = 170;
	public final static int EVENT_GET_REPORTER_RESPONSE = 171;
	
	public final static int EVENT_ARE_REPORTER = 220;
	public final static int EVENT_ARE_REPORTER_RESPONSE = 221;
	
	public final static int EVENT_USER_DETECTED = 140;
	
	public final static int EVENT_USER_LOGOUT = 141;
	
	public final static int EVENT_STORAGE_REPORTER = 225;
	public final static int EVENT_STORAGE_REPORTER_RESPONSE = 226;
	

	public final static int MODULE_ORCHESTRATOR = 21;
	public final static int MODULE_DEVICE_REPORTER = 13;
	public final static int MODULE_SYSTEM_SETTING_HANDLER = 11;
	public final static int MODULE_USER_LISTENER = 14;
	public final static int MODULE_MINI_MATCH_MAKER = 22;
	public final static int MODULE_HAPTIC_FEEDBACK_HANDLER = 16;
	public final static int MODULE_HTTP_CLIENT = 17;


}
