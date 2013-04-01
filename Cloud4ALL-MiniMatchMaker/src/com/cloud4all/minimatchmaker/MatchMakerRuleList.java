package com.cloud4all.minimatchmaker;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class MatchMakerRuleList {

	private List<MatchMakerRule> ruleList = new ArrayList<MatchMakerRule>();

	public int getNumberOfRules() {
		if (ruleList == null) return 0;
		else return ruleList.size(); 
	}

	public MatchMakerRuleList() {
	}

	// ** Adding rules


	public void addRule(JSONObject rule) {
		try {
			String type = rule.getString("type");
			if (type.equalsIgnoreCase("rule")) {
				MatchMakerRule tmpRule = MatchMakerRule.createMatchMakerRule(rule);
				try {
					ruleList.add(tmpRule);
				} catch (Exception e) {
					Log.e("MatchMakerRuleList in addRule", "Error adding a rule to the array.\nException: " +e);
				}
				Log.i("MatchMakerRule", "There are " + String.valueOf(getNumberOfRules()) + " rules in the list.");
			}
		} catch (JSONException e) {
			Log.e("MatchMakerRuleList Error in addRule", "JSON error adding a rule.\n"+e);
		}
	}

	public JSONObject checkRulesWithProperties(List<MatchMakerPropertyState> list) {
		JSONObject result = null;


		if (getNumberOfRules() > 0) {
			try {
				Log.i("MatchMakerRule", "Checking properties on rules.\nThere are " + String.valueOf(getNumberOfRules()) + " rules in the list.");
				result = new JSONObject();
				result.put("type", "package");
				int numResults = 0;
				for (int i=0;i<ruleList.size();i++) {
					MatchMakerRule tmpRule = (MatchMakerRule) ruleList.get(i);
					if (tmpRule.checkRule(list) == true) {
						for (int o=0;o<tmpRule.operations.size();o++) {
							numResults++;
							JSONObject tmpOperation = tmpRule.operations.get(o); 
							String paramName = "operation" + String.valueOf(numResults);
							result.put(paramName, tmpOperation .toString());
						}
					}
				}
				result.put("operations", String.valueOf(numResults));	
			} catch (JSONException e) {
				Log.e("MatchMakerRuleList error in checkRulesWithProperties", "\nError managing stored JSON rules.\n" +e);
			}
		} else {
			// Get rules from Cloud
			Log.i("MatchMakerRuleList", "Asking the Cloud for rules.");
			// message notification
			result = new JSONObject();
			try {
				result.put("type", "message");
				result.put("value", "There is no rule in Mini Match Maker\nMini Match Maker will connect to Match Maker in the Cloud for rules.\nTry again.");
			} catch (JSONException e) {
				Log.e("MatchMakerRuleList Error in checkRulesWithProperties", "JSON error creating message.\n" +e);
			}

			// Ask MatchMaker in Cloud for rules
			MatchMakerCommunicator com = new MatchMakerCommunicator();
			JSONObject cloudRules = com.getRulesFromCloud();
			try {
				// store rules in Mini Match maker
				String typePackage = cloudRules.getString("type");
				if (typePackage.equalsIgnoreCase("listofrules")) {
					Log.i("MatchMakerRuleList", "Receiving rules from the Cloud...");
					int n = Integer.valueOf(cloudRules.getString("parameters"));
					for (int i=1;i<=n;i++) {
						String paramName = "parameter" + String.valueOf(i);
						JSONObject tmpRule = new JSONObject(cloudRules.getString(paramName));
						Log.i("MatchMakerRuleList", "Adding " + paramName + " rule.\n JSONString: " + tmpRule.toString());
						addRule(tmpRule);
					}
				}
			} catch(JSONException e) {
				Log.e("MatchMakerRuleList  Error in checkRulesWithProperties", "JSON error loading rules from cloud.\n" +e);
			}
		}
		return result;
	}

}
