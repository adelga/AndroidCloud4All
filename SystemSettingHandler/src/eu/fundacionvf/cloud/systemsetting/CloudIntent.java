/*
CloudIntent class
This class represents a Intent with extends funcionality.	

Copyright (c) 2013, Vodafone Spain Foundation
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Vodafone Spain Foundation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package eu.fundacionvf.cloud.systemsetting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class CloudIntent extends Intent {
	private static final String EXTRA_PARAMS = "params";
	private static final String EXTRA_EVENT = "idEvent";
	private static final String EXTRA_ACTION = "idAction";
	public String params;

	private JSONObject json;

	public static CloudIntent intentToCloudIntent(Intent inte) throws Exception {
		int evento = inte.getIntExtra(EXTRA_EVENT, 0);
		int idAction = inte.getIntExtra(EXTRA_ACTION, 0);
		CloudIntent i;
		if (evento == 0 && idAction == 0) {
			i = null;

		} else {
			i = new CloudIntent(inte.getAction(), evento, idAction);
			i.setStringParams(inte.getStringExtra(EXTRA_PARAMS));
		}
		return i;

	}

	public CloudIntent(String action, int idEvento, int idAction) {
		super(action);
		if (Build.VERSION.SDK_INT < 12) {
			this.setFlags(Intent.FLAG_DEBUG_LOG_RESOLUTION | Intent.FLAG_ACTIVITY_NEW_TASK);
		} else {
			this.setFlags(FLAG_INCLUDE_STOPPED_PACKAGES | Intent.FLAG_DEBUG_LOG_RESOLUTION | Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		this.putExtra(EXTRA_EVENT, idEvento);
		this.putExtra(EXTRA_ACTION, idAction);


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
	public int getIdAction() {
		return this.getIntExtra(EXTRA_ACTION, 0);

	}

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
