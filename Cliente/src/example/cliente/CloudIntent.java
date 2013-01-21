package example.cliente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.util.Log;

public class CloudIntent extends Intent {
	private static final String EXTRA_PARAMS = "params";
	private static final String EXTRA_EVENT = "idEvent";
	private static final String EXTRA_MODULE = "idModule";
	public String params;
	
	private JSONObject json;

	public static CloudIntent intentToCloudIntent(Intent inte) {
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

	public CloudIntent(String action, int idEvento, int idModulo) {
		super(action);
//		this.setAction(action);
//		this.putExtra(EXTRA_EVENT, idEvento);
//		this.putExtra(EXTRA_MODULE, idModulo);
		this.setFlags(Intent.FLAG_DEBUG_LOG_RESOLUTION);

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

	public void setParams(String id, String value) throws JSONException {

		if (json == null) {

			writeJson(id, value);

		} else {

			writeNewJsonObject(json, id, value);
		}
		params = json.toString();
		this.putExtra(EXTRA_PARAMS, params);
		Log.e("JSON", "String: " + json.toString());

	}

	private void setStringParams(String params) {
		this.params = params;

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

		String[] arrayIds = null;

		JSONArray jsonArray = json.getJSONObject("jsonfile").getJSONArray(
				"params");

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
