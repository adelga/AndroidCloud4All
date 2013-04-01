package com.cloud4all.minimatchmaker;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/*

MiniMatchMakerEngine
This class is the kernel of MiniMatchMaker.
This class manages rules and queries. 	

Copyright (c) 2013, Technosite R&D
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Technosite R&D nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


 */

public class MiniMatchMakerEngine {

	private 	 MatchMakerRuleList rules = new MatchMakerRuleList();
	private List<MatchMakerPropertyState> properties = new ArrayList<MatchMakerPropertyState>();
	private MiniMatchMakerService service = null;

	public MiniMatchMakerEngine(MiniMatchMakerService value) {
		service = value;
	}

	public void receiveData(JSONObject data) {
		managePackage(data);
		JSONObject result = null;
		result = rules.checkRulesWithProperties(properties );
		if (result!= null) {
			service.ResponseData(result);
		}
	}

	// ** Properties management

	private boolean changeProperty(MatchMakerPropertyState property) {
		for (int i=0;i<properties.size();i++) {
			MatchMakerPropertyState tmp = (MatchMakerPropertyState) properties.get(i);
			if (tmp.name.equalsIgnoreCase(property.name)) {
				Log.i("MiniMatchMakerEngine", "Property to change is found.");
				tmp.stringValue = String.valueOf(property.stringValue);
				return true;
			}
		}
		return false;
	}

	private void addProperty(JSONObject data) {
		MatchMakerPropertyState state = new MatchMakerPropertyState(data);
		if (changeProperty(state) == false) {
			properties.add(state);
			Log.i("MiniMatchMakerEngine", "Adding a property");
		}
	}

	private void managePackage(JSONObject data) {
		try {
			String typePackage = data.getString("type");
			if (typePackage.equalsIgnoreCase("environment")) {
				Log.i("MiniMatchMakerEngine", "Managing a environment package");
				int n = Integer.valueOf(data.getString("parameters"));
				for (int c=1;c<=n;c++) {
					String paramName = "parameter" + String.valueOf(c);
					JSONObject JSONtmp = new JSONObject(data.getString(paramName ));
					addProperty(JSONtmp );		
				}
			}
		}catch (JSONException e) {
			Log.e("MiniMatchMakerEngine Error in managePackage", "Error processing a property.\n"+e);
		}
		Log.i("MiniMatchMakerEngine in managePackage", "There are "+ String.valueOf(properties.size()) + " stored properties in Mini Match Maker.");
	}


}
