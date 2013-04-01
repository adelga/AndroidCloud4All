package com.cloud4all.minimatchmaker;

/*

CloudIntent
This class is a improved Intent class	

Copyright (c) 2013, Technosite R&D
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Technosite R&D nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


*/
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;

public class CloudIntent extends Intent {
	private static final String EXTRA_PARAMS = "params";
	private static final String EXTRA_EVENT = "idEvent";
	private static final String EXTRA_MODULE = "idModule";
	public String params;
	
	private JSONObject json;

	/*
	 * Static method to transform a normal received intent to a Specific Cloudintent
	 */
	public static CloudIntent intentToCloudIntent(Intent inte) throws Exception {
		int evento = inte.getIntExtra(EXTRA_EVENT, 0);
		int modulo = inte.getIntExtra(EXTRA_MODULE, 0);
		CloudIntent i;
		if (evento == 0 && modulo == 0) {
			i = null;

		} else {
			i = new CloudIntent(inte.getAction(), evento, modulo);
			i.setStringParams(inte.getStringExtra(EXTRA_PARAMS));
		}
		return i;

	}
	/*
	 * Constructor, need the action(Who should listen), the idEvent(What should do) and the idModulo(Who am I, the Intent origin)
	 */
	public CloudIntent(String action, int idEvento, int idModulo) {
		super(action);
		this.setFlags(FLAG_INCLUDE_STOPPED_PACKAGES| Intent.FLAG_DEBUG_LOG_RESOLUTION | Intent.FLAG_ACTIVITY_NEW_TASK);
//		this.setAction(action);
		this.putExtra(EXTRA_EVENT, idEvento);
		this.putExtra(EXTRA_MODULE, idModulo);
		

	}

	/*
	 * Return the event identifier, if there are any error return 0
	 */
	public int getIdEvent() {
		return this.getIntExtra(EXTRA_EVENT, 0);

	}

	/*
	 * Return the origin module identifier, if there are any error return 0
	 */
	public int getIdModule() {
		return this.getIntExtra(EXTRA_EVENT, 0);

	}

	/*
	 * To add a new Param (Key/value)
	 */
	public void setParams(String id, String value) throws JSONException {

		if (json == null) {

			writeJson(id, value);

		} else {

			writeNewJsonObject(json, id, value);
		}
		params = json.toString();
		this.putExtra(EXTRA_PARAMS, params);

	}

	private void setStringParams(String params) throws JSONException {
		
		
		JSONObject str= new JSONObject(params);
		
		JSONArray root =str.getJSONObject("jsonfile").getJSONArray("params");
		
		for(int i=0;i<root.length();i++){
			JSONObject obj= root.getJSONObject(i);
			this.setParams(obj.getString("id"), obj.getString("value"));
		
		}

	}

	private void writeJson(String id, String value) throws JSONException {

		json = new JSONObject();
		JSONObject jsonObjectParams = new JSONObject();
		JSONObject jsonObjectData = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		jsonObjectData.put("id", id);
		jsonObjectData.put("value", value);

		jsonArray.put(jsonObjectData);

		jsonObjectParams.put("params", jsonArray);
		json.put("jsonfile", jsonObjectParams);
		

	}

	private void writeNewJsonObject(JSONObject json, String id, String value)
			throws JSONException {

		JSONObject jsonObjectNewData = new JSONObject();

		jsonObjectNewData.put("id", id);
		jsonObjectNewData.put("value", value);

		json.getJSONObject("jsonfile").getJSONArray("params")
				.put(jsonObjectNewData);

	}
	
	/*
	 * Return the keys IDs list
	 */
	public String[] getArrayIds() throws JSONException {

		

		JSONArray jsonArray = json.getJSONObject("jsonfile").getJSONArray(
				"params");
		String[] arrayIds = new String[jsonArray.length()];
		for (int i = 0; i < jsonArray.length(); i++) {

			JSONObject result = jsonArray.getJSONObject(i);

			arrayIds[i] = result.getString("id");

		}
		
		
		return arrayIds;

	}

	/*
	 * Return an specific String value for a specific key
	 */
	public String getValue(String id) throws JSONException {

		String value;

		JSONArray jsonArray = json.getJSONObject("jsonfile").getJSONArray(
				"params");

		for (int i = 0; i < jsonArray.length(); i++) {

			if (id.equals(jsonArray.getJSONObject(i).getString("id"))) {

				value = jsonArray.getJSONObject(i).getString("value");

				return value;
			}
		}
		return null;

	}
	
	
	
	
	

}
