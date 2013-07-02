/*
SystemSoundUtil class
This class implements the funcionality to configure all general sounds, as default tones and System Sounds as lock Screen sound. 

Copyright (c) 2013, Vodafone Spain Foundation
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Vodafone Spain Foundation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 */

package eu.fundacionvf.cloud.systemsettingpreicsroot.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
/**
 * This class contains utility methods.
 *
 * @author adelga38@corp.vodafone.es (Alberto Delgado García)
 */
public class SystemSoundsUtil {
	private final static String preferences = "SystemSoundsPreferences";

	private String MESSAGE_OK = "OK";
	private String MESSAGE_ERROR = "ERROR_SOUND";

	private final String tag = "SystemSettingTAG";

	private Context cntx;

	private static final String LOCK_SOUND = "/system/media/audio/ui/Lock.ogg";
	private static final String UNLOCK_SOUND = "/system/media/audio/ui/Unlock.ogg";
	private static final String LOW_BATTERY_SOUND = "/system/media/audio/ui/LowBattery.ogg";
	private static final String EFFECT_TICK_SOUND = "/system/media/audio/ui/Effect_Tick.ogg";

	private static final String LOCK_SOUND_COPY = "/system/media/audio/ui/Lock_org.ogg";
	private static final String UNLOCK_SOUND_COPY = "/system/media/audio/ui/Unlock_org.ogg";
	private static final String LOW_BATTERY_SOUND_COPY = "/system/media/audio/ui/LowBattery_org.ogg";
	private static final String EFFECT_TICK_SOUND_COPY = "/system/media/audio/ui/Effect_Tick_org.ogg";

	// private static final String UNO_250 = "Tono1-250.ogg";
	// private static final String DOS_250 = "Tono2-250.ogg";
	// private static final String TRES_250 = "Tono3-250.ogg";
	// private static final String UNO_600= "Tono1-600.ogg";
	// private static final String DOS_600 = "Tono2-600.ogg";
	// private static final String UNO_1000 = "Tono1-1000.ogg";
	// private static final String DOS_1000 = "Tono2-1000.ogg";
	


	public SystemSoundsUtil(Context cntx) {
		super();

		this.cntx = cntx;
	}

	
	/**
	 * Configure the default lock Sound. This method needs a instance of
	 * SystemSoundUtil because the context is necessary.The available sounds are in assets, and now are pure tones at specific frecuency (250 Hz, 600 Hz, 1000Hz), and this pure tones can play once, twice or three times.  
	 * 
	 * @param asset, String of the asset file name.
	 *            
	 * @return message, return a String with OK or ERROR
	 * 
	 */
	public String changeLockSound(String asset) {
//		try {
//
//			String[] hin1 = { "su", "-c",
//					"cp " + LOCK_SOUND + " " + LOCK_SOUND_COPY };
//
//			Runtime.getRuntime().exec(hin1).waitFor();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		try {
			AssetManager assetManager = cntx.getAssets();
			String[] files = null;

			files = assetManager.list("");

			boolean exists = false;
			for (String filename : files) {
				Log.d("ASSETS", "name " + filename);
				if (filename.equalsIgnoreCase(asset)) {
					exists = true;
					break;
				}
			}
			if (exists) {
				FileUtil f = new FileUtil(cntx);
				f.copyAsset(asset, LOCK_SOUND);
				return MESSAGE_OK;
			} else {
				Log.d("ASSETS", "don't exist this asset for sound");

				return MESSAGE_ERROR;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return MESSAGE_ERROR;
	}
	

	public void restoreLockSound() {
		try {

			String[] hin1 = { "su", "-c",
					"cp " + LOCK_SOUND_COPY + " " + LOCK_SOUND };

			Runtime.getRuntime().exec(hin1).waitFor();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}
	
	
	/**
	 * Configure the default unlock Sound. This method needs a instance of
	 * SystemSoundUtil because the context is necessary.The available sounds are in assets, and now are pure tones at specific frecuency (250 Hz, 600 Hz, 1000Hz), and this pure tones can play once, twice or three times.  
	 * 
	 * @param asset, String of the asset file name.
	 *            
	 * @return message, return a String with OK or ERROR
	 * 
	 */
	public String changeUnlockSound(String asset) {
		try {

			String[] hin1 = { "su", "-c",
					"cp " + UNLOCK_SOUND + " " + UNLOCK_SOUND_COPY };

			Runtime.getRuntime().exec(hin1).waitFor();

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			AssetManager assetManager = cntx.getAssets();
			String[] files = null;

			files = assetManager.list("");

			boolean exists = false;
			for (String filename : files) {
				Log.d("ASSETS", "name " + filename);
				if (filename.equalsIgnoreCase(asset)) {
					exists = true;
					break;
				}
			}
			if (exists) {
				FileUtil f = new FileUtil(cntx);
				f.copyAsset(asset, UNLOCK_SOUND);
				return MESSAGE_OK;
			} else {
				Log.d("ASSETS", "don't exist this asset for sound");

				return MESSAGE_ERROR;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return MESSAGE_ERROR;
	}
	

	public void restoreUnlockSound() {
		try {

			String[] hin1 = { "su", "-c",
					"cp " + UNLOCK_SOUND_COPY + " " + UNLOCK_SOUND };

			Runtime.getRuntime().exec(hin1).waitFor();

		} catch (Exception e) {
		
			e.printStackTrace();

		}

	}
	
	/**
	 * Configure the default low Battery Sound. This method needs a instance of
	 * SystemSoundUtil because the context is necessary.The available sounds are in assets, and now are pure tones at specific frecuency (250 Hz, 600 Hz, 1000Hz), and this pure tones can play once, twice or three times.  
	 * 
	 * @param asset, String of the asset file name.
	 *            
	 * @return message, return a String with OK or ERROR
	 * 
	 */
	public String changeLowBatterySound(String asset) {
		try {

			String[] hin1 = { "su", "-c",
					"cp " + LOW_BATTERY_SOUND + " " + LOW_BATTERY_SOUND_COPY };

			Runtime.getRuntime().exec(hin1).waitFor();

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			AssetManager assetManager = cntx.getAssets();
			String[] files = null;

			files = assetManager.list("");

			boolean exists = false;
			for (String filename : files) {
				Log.d("ASSETS", "name " + filename);
				if (filename.equalsIgnoreCase(asset)) {
					exists = true;
					break;
				}
			}
			if (exists) {
				FileUtil f = new FileUtil(cntx);
				f.copyAsset(asset, LOW_BATTERY_SOUND);
				return MESSAGE_OK;
			} else {
				Log.d("ASSETS", "don't exist this asset for sound");

				return MESSAGE_ERROR;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return MESSAGE_ERROR;
	}
	

	public void restoreLowBatterySound() {
		try {

			String[] hin1 = { "su", "-c",
					"cp " + LOW_BATTERY_SOUND_COPY + " " + LOW_BATTERY_SOUND };

			Runtime.getRuntime().exec(hin1).waitFor();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}
	
	/**
	 * Configure the default tick Sound. This method needs a instance of
	 * SystemSoundUtil because the context is necessary.The available sounds are in assets, and now are pure tones at specific frecuency (250 Hz, 600 Hz, 1000Hz), and this pure tones can play once, twice or three times.  
	 * 
	 * @param asset, String of the asset file name.
	 *            
	 * @return message, return a String with OK or ERROR
	 * 
	 */
	public String changeTickSound(String asset) {
		try {

			String[] hin1 = { "su", "-c",
					"cp " + EFFECT_TICK_SOUND + " " + EFFECT_TICK_SOUND_COPY };

			Runtime.getRuntime().exec(hin1).waitFor();

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			AssetManager assetManager = cntx.getAssets();
			String[] files = null;

			files = assetManager.list("");

			boolean exists = false;
			for (String filename : files) {
				Log.d("ASSETS", "name " + filename);
				if (filename.equalsIgnoreCase(asset)) {
					exists = true;
					break;
				}
			}
			if (exists) {
				FileUtil f = new FileUtil(cntx);
				f.copyAsset(asset, EFFECT_TICK_SOUND);
				return MESSAGE_OK;
			} else {
				Log.d("ASSETS", "don't exist this asset for sound");

				return MESSAGE_ERROR;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return MESSAGE_ERROR;
	}
	

	public void restoreTickSound() {
		try {

			String[] hin1 = { "su", "-c",
					"cp " + EFFECT_TICK_SOUND_COPY + " " + EFFECT_TICK_SOUND };

			Runtime.getRuntime().exec(hin1).waitFor();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}
	

}
