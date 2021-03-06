/*
SystemFontUtil
his class implements the functionality to modify the font scale in Android 2.x devices.

Copyright (c) 2013, Vodafone Spain Foundation
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n� 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Vodafone Spain Foundation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 */

package eu.fundacionvf.cloud.systemsettingpreics.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * This class contains utility methods.
 * 
 * @author adelga38@corp.vodafone.es (Alberto Delgado Garc�a)
 */
public class SystemFontUtil {

	Context cntx;

	public SystemFontUtil(Context cntx) {

		this.cntx = cntx;
	}

	/**
	 * Configure the scale font This method needs a instance of SystemFontUtil
	 * because the context is necessary
	 * 
	 * @param scale
	 *            , font size (1.0 it's normal, shouldn't more than 2.0)
	 * @return String, "OK" if all run properly, "ERROR": in other case
	 */
	public String changeFontScale(String scale) {
		try {
			Log.d("SystemSettingHandler", "scale: " + scale);
			Intent i = new Intent("eu.fundacionvf.ActionStartService");
			float esc = Float.parseFloat(scale);
			if (0.5 < esc && esc <= 2.0) {
				i.putExtra("scale", esc);
				cntx.sendBroadcast(i);
				return "OK";
			} else {
				return "ERROR";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR";
		}

	}
}
