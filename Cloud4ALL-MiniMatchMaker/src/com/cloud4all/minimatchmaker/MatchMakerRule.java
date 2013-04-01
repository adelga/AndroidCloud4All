package com.cloud4all.minimatchmaker;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/*

MatchMakerRule
This class represents a rule for Match maker environment. This stores conditions and operations.

Copyright (c) 2013, Technosite R&D
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Technosite R&D nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


 */
public class MatchMakerRule {

	public String name= null; // name of the rule
	public List<MatchMakerBasicCondition> conditions = new ArrayList<MatchMakerBasicCondition>();
	public List<JSONObject> operations = new ArrayList<JSONObject>();

	private int successLevel = 0;
	private int maxSuccessLevel = 0;

	public MatchMakerBasicCondition createConditionFromJSON(JSONObject data) {
		MatchMakerBasicCondition  result = null;
		try {
			String propertyName = data.getString("property");
			String type = data.getString("type");
			if (type.equalsIgnoreCase("boolean")) {
				result = new MatchMakerBooleanCondition(propertyName , Boolean.valueOf(data.getString("value")));
			} else if (type.equalsIgnoreCase("range")) {
				result = new MatchMakerRangeCondition(propertyName , Float.valueOf(data.getString("minimum value")), Float.valueOf(data.getString("maximum value")));
			}		
		} catch (JSONException e) { 
			Log.e("MatchMakerRule error in createConditionFromJSON", "\n Error creating a condition from JSON data.\n"+e);
		}
		if (result!=null) Log.i("MatchMakerRule in createConditionFromJSON", "\nCondition created from JSON data.\n");
		else Log.e("MatchMakerRule error in createConditionFromJSON", "\n Error creating a condition for rule from JSON. A null result appears");
		return result;
	}

	public void addCondition(MatchMakerBasicCondition condition) {
		if (condition != null) {
			conditions.add(condition);
			Log.i("MatchMakerRule in addCondition", "Adding a condition to the rule "+ this.name);
		} else {
			Log.e("MatchMakerRule error in addCondition", "Error adding a null condition to the rule "+ this.name);
		}
	}

	public void addOperation(JSONObject operation) {
		if (operation != null) {
			operations.add(operation);
			Log.i("MatchMakerRule in addOperation", "Adding a operation to the rule "+ this.name);
		} else {
			Log.e("MatchMakerRule error in addOperation", "Error adding a null operation to the rule "+ this.name);
		}
	}

	public MatchMakerRule() {

	}


	public MatchMakerRule(JSONObject data) {
		super();
		try {
			this.name = data.getString("name");
			int operations= Integer.valueOf(data.getString("conditions"));
			int conditions = Integer.valueOf(data.getString("conditions"));
			Log.i("MatchMakerRule constructor", "Creating "+name+" rule from JSON data with " + String.valueOf(conditions) + " conditions and " + String.valueOf(operations) + " operations.");
			for (int ii=1;ii<=operations;ii++) {
				String paramName = "operation" + String.valueOf(ii);
				JSONObject tmpOperation = new JSONObject(data.getString(paramName));
				this.addOperation(tmpOperation);
			}

			for (int i=1;i<=conditions;i++) {
				String paramName = "condition" + String.valueOf(i);
				JSONObject tmpCondition = new JSONObject(data.getString(paramName));
				MatchMakerBasicCondition  tmp = createConditionFromJSON(tmpCondition);
				this.addCondition(tmp);
			}
		}catch (JSONException e) {
			Log.e("MatchMakerRule Error in constructor", "Error parsing a rule in JSON.\n"+e);
		}
	}

	public boolean checkRule(List<MatchMakerPropertyState> data) {
		maxSuccessLevel = conditions.size(); 	
		successLevel = 0; // reset the counter
		for (int o=0;o<data.size();o++)
			for (int c=0;c<maxSuccessLevel;c++) {
				String tmpConditionName = this.conditions.get(c).property; 
				String tmpOperatorName = data.get(o).name;
				if (tmpOperatorName.equalsIgnoreCase(tmpConditionName)) {
					// ** comprobar 
					MatchMakerPropertyState tmpOperator = (MatchMakerPropertyState ) data.get(o);
					MatchMakerBasicCondition tmpCondition = this.conditions.get(c);
					if (tmpOperator.checkWithMatchMakerCondition(tmpCondition)) successLevel++;
				}
			}
		if (successLevel == maxSuccessLevel) return true;
		else return false;
	}


	public static MatchMakerRule createMatchMakerRule(JSONObject data) {
		MatchMakerRule rule = new MatchMakerRule();
		try {
			rule.name = data.getString("name");
			int operations= Integer.valueOf(data.getString("conditions"));
			int conditions = Integer.valueOf(data.getString("conditions"));
			Log.i("MatchMakerRule in createMatchMakerRule", "Creating "+ rule.name+" rule from JSON data with " + String.valueOf(conditions) + " conditions and " + String.valueOf(operations) + " operations.");
			for (int ii=1;ii<=operations;ii++) {
				String paramName = "operation" + String.valueOf(ii);
				JSONObject tmpOperation = new JSONObject(data.getString(paramName));
				rule.addOperation(tmpOperation);
			}

			for (int i=1;i<=conditions;i++) {
				String paramName = "condition" + String.valueOf(i);
				JSONObject tmpCondition = new JSONObject(data.getString(paramName));
				MatchMakerBasicCondition  tmp = rule.createConditionFromJSON(tmpCondition);
				rule.addCondition(tmp);
			}
		}catch (JSONException e) {
			Log.e("MatchMakerRule Error in createMatchMakerRule", "Error parsing a rule in JSON.\n"+e);
		}
		if (rule == null) Log.e("MatchMakerRule Error in createMatchMakerRule", "Error creating a rule. Null result generated.");
		else Log.i("MatchMakerRule in createMatchMakerRule", "Created "+ rule.name+" rule");
		return rule;
	}

}
