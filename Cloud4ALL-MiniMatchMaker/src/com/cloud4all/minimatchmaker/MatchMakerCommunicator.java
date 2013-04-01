package com.cloud4all.minimatchmaker;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/*

MatchMakerCommunicator
This class manages communications between MiniMatchMaker and MatchMaker in the cloud.
Currently this class makes a demo of rules without Internet connection.	

Copyright (c) 2013, Technosite R&D
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Technosite R&D nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


 */
public class MatchMakerCommunicator {

	public boolean sendJSONData(JSONObject data) {
		return false;
	}

	public JSONObject getRulesFromCloud() {
		// Create a JSON object with data
		JSONObject data = new JSONObject();
		JSONObject ruleBrightness = new JSONObject();
		JSONObject operation1Brightness = new JSONObject();
		JSONObject operation2Brightness = new JSONObject();
		JSONObject condition1Brightness = new JSONObject();
		JSONObject condition2Brightness = new JSONObject();
		JSONObject ruleNoise = new JSONObject();
		JSONObject operationNoise = new JSONObject();
		JSONObject conditionNoise = new JSONObject();


		try {
			ruleBrightness.put("name", "close the blind");
			ruleBrightness.put("type", "rule");
			ruleBrightness.put("conditions", Integer.valueOf(2));
			condition1Brightness.put("property", "brightness");
			condition1Brightness.put("type", "range");
			condition1Brightness.put("minimum value", Float.valueOf(500));
			condition1Brightness.put("maximum value", Float.valueOf(1000));
			ruleBrightness.put("condition1", condition1Brightness.toString());
			condition2Brightness.put("property", "noise");
			condition2Brightness.put("type", "range");
			condition2Brightness.put("minimum value", Float.valueOf(500));
			condition2Brightness.put("maximum value", Float.valueOf(1000));
			ruleBrightness.put("condition2", condition2Brightness.toString());
			operation1Brightness .put("type", "message");
			operation1Brightness .put("value", "Rule: close the blind.\nIs noisy and bright here.");
			operation2Brightness .put("type", "message");
			operation2Brightness .put("value", "Rule close the blind.\nMove the device to other place.");
			ruleBrightness.put("operations", Integer.valueOf(2));
			ruleBrightness.put("operation1", operation1Brightness.toString());
			ruleBrightness.put("operation2", operation2Brightness.toString());

			ruleNoise.put("name", "Go to bed.");
			ruleNoise.put("type", "rule");
			ruleNoise.put("conditions", Integer.valueOf(1));
			conditionNoise.put("property", "brightness");
			conditionNoise.put("type", "range");
			conditionNoise.put("minimum value", Float.valueOf(0));
			conditionNoise.put("maximum value", Float.valueOf(40));
			ruleNoise.put("condition1", conditionNoise.toString());

			operationNoise.put("type", "message");
			operationNoise.put("value", "Rule Go to bed.\nYou are on darkness. I think it's time to go to bed.");
			ruleNoise.put("operations", Integer.valueOf(1));
			ruleNoise.put("operation1", operationNoise.toString());

			data.put("type", "listOfRules");
			data.put("parameters", Integer.valueOf(2));
			data.put("parameter1", ruleBrightness.toString());
			data.put("parameter2", ruleNoise.toString());
			Log.i("MatchMaker communicator", "data success from the cloud");
			return data;
		} catch (JSONException e) {
			Log.e("Error MatchMaker communicator", "\n Error creating data from the cloud.\n" + e);
			return null;
		}
	}

}
