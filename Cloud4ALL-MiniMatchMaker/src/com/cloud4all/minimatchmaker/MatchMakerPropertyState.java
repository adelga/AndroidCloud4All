package com.cloud4all.minimatchmaker;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


/*

MatchMakerPropertyState
This class represents a environmental data included in Mini Match Maker	

Copyright (c) 2013, Technosite R&D
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Technosite R&D nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


 */

public class MatchMakerPropertyState {

	public enum TypeProperty { NONE, FLOAT, INTEGER, BOOLEAN, STRING };

	public String name = null;
	public TypeProperty type = TypeProperty.NONE;
	public String stringValue = null;

	public MatchMakerPropertyState(JSONObject data) {
		try {
			name = data.getString("name");
			stringValue = data.getString("value");
			String tmpType = data.getString("type");
			if (tmpType.equalsIgnoreCase("float")) type= TypeProperty.FLOAT;
			if (tmpType.equalsIgnoreCase("integer")) type= TypeProperty.INTEGER;
			if (tmpType.equalsIgnoreCase("boolean")) type= TypeProperty.BOOLEAN;
			if (tmpType.equalsIgnoreCase("string")) type= TypeProperty.STRING;
			Log.i("MatchMakerPropertyState", "Property created");
		} catch (JSONException e) {
			Log.e("MatchMakerProperty Error in constructor", "The property couldn't be created. JSON problem.\n"+e);
		}
	}

	public boolean checkWithMatchMakerCondition(MatchMakerBasicCondition  condition) {
		boolean result = false;
		if (condition.typeCondition == MatchMakerBasicCondition.TypeCondition.BOOLEAN) {
			MatchMakerBooleanCondition tmp = (MatchMakerBooleanCondition) condition;
			result = tmp.checkCondition(Boolean.valueOf(this.stringValue));
		} else if (condition.typeCondition == MatchMakerBasicCondition.TypeCondition.RANGE) {
			MatchMakerRangeCondition tmp = (MatchMakerRangeCondition) condition;
			result = tmp.checkCondition(Float.valueOf(this.stringValue));
		}
		return result;
	}

}
