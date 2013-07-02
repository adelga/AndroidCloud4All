/*
SystemFontUtil
This class implements the functionality to modify the font settings, as the font scale and the tipografy.

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
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
/**
 * This class contains utility methods.
 *
 * @author adelga38@corp.vodafone.es (Alberto Delgado García)
 */
public class SystemFontUtil {

	private static final int LOCAL_URL = 0;
	private static final int REMOTE_URL = 1;
	private static final String TAG = "SystemFontUtil";
	private static final String MESSAGE_ERROR = "ERROR_FONT";
	private static final String MESSAGE_OK = "OK";

	private Context cntx;

	public SystemFontUtil(Context cntx) {
		super();

		this.cntx = cntx;
	}

	/**
	 * Configure the regular font type. This method needs a instance of
	 * SystemFontUtil because the context is necessary REBOOT it's necessary
	 * 
	 * @param local
	 *            , if the font it's in local (0) or remote (1)
	 * @param url
	 *            , the address of new font type
	 * @return String, "OK" if all run properly, "ERROR": in other case
	 */
	public String changeRegularFontType(int local, String url) {

		if (local == LOCAL_URL) {
			Log.d(TAG, "local URL");
			try {
				File fnt = new File("/system/fonts/DroidSams_org.ttf");

				if (!fnt.exists()) {
					try {

						String[] hin1 = { "su", "-c",
								"cp /system/fonts/DroidSans.ttf /system/fonts/DroidSans_org.ttf" };

						Runtime.getRuntime().exec(hin1).waitFor();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				String[] hin2 = { "su", "-c",
						"cp " + url + " /system/fonts/DroidSans.ttf" };

				Runtime.getRuntime().exec(hin2);

				return "OK";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "ERROR";
			}
		} else {
			Log.d(TAG, "remote URL");
			try {
				String downlName = downloadFile(url);
				if (downlName == null) {
					return "ERROR";
				}
				File fnt = new File("/system/fonts/DroidSams_org.ttf");

				if (!fnt.exists()) {
					try {

						String[] hin1 = { "su", "-c",
								"cp /system/fonts/DroidSans.ttf /system/fonts/DroidSans_org.ttf" };

						Runtime.getRuntime().exec(hin1).waitFor();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				String[] hin2 = { "su", "-c",
						"cp " + downlName + " /system/fonts/DroidSans.ttf" };

				Runtime.getRuntime().exec(hin2);

				return "OK";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "ERROR";
			}
		}
	}

	public void restoreFontRegular() {
		try {

			String[] hin1 = { "su", "-c",
					"cp  /system/fonts/DroidSans_org.ttf /system/fonts/DroidSans.ttf" };

			Runtime.getRuntime().exec(hin1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	/**
	 * Configure the bold font type. This method needs a instance of
	 * SystemFontUtil because the context is necessary REBOOT it's necessary
	 * 
	 * @param local
	 *            , if the font it's in local (0) or remote (1)
	 * @param url
	 *            , the address of new font type
	 * @return String, "OK" if all run properly, "ERROR": in other case
	 */
	public String changeBoldFontType(int local, String url) {

		if (local == LOCAL_URL) {
			Log.d(TAG, "local URL");
			try {
				File fnt = new File("/system/fonts/DroidSams-Bold_org.ttf");
				if (!fnt.exists()) {
					try {
						String[] hin1 = { "su", "-c",
								"cp /system/fonts/DroidSans-Bold.ttf /system/fonts/DroidSans-Bold_org.ttf" };

						Runtime.getRuntime().exec(hin1).waitFor();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				String[] hin2 = { "su", "-c",
						"cp " + url + " /system/fonts/DroidSans-Bold.ttf" };

				Runtime.getRuntime().exec(hin2);

				return "OK";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "ERROR";
			}
		} else {
			Log.d(TAG, "remote URL");
			try {
				String downlName = downloadFile(url);
				if (downlName == null) {
					return "ERROR";
				}
				// comprobar que no este la original
				File fnt = new File("/system/fonts/DroidSams-Bold_org.ttf");
				if (!fnt.exists()) {
					try {
						String[] hin1 = { "su", "-c",
								"cp /system/fonts/DroidSans-Bold.ttf /system/fonts/DroidSans-Bold_org.ttf" };

						Runtime.getRuntime().exec(hin1).waitFor();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				String[] hin2 = { "su", "-c",
						"cp " + downlName + " /system/fonts/DroidSans-Bold.ttf" };

				Runtime.getRuntime().exec(hin2);

				return "OK";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "ERROR";
			}
		}
	}

	public void restoreFontBold() {
		try {

			String[] hin1 = { "su", "-c",
					"cp  /system/fonts/DroidSans-Bold_org.ttf /system/fonts/DroidSans-Bold.ttf" };

			Runtime.getRuntime().exec(hin1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	private String downloadFile(String urlFont) {
		String path = Environment.getExternalStorageDirectory()
				+ "/cloud4AllFont/";
		String fileName = "newType.ttf";
		try {
			URL url = new URL(urlFont);
			File myDir = new File(path);
			myDir.mkdir();
			File file = new File(path, fileName);
			Log.d("PREFERENCES", "Run hilo");
			Log.i("PREFERENCES", "filename:" + fileName);

			/* Open a connection to that URL. */
			URLConnection ucon = url.openConnection();

			/*
			 * Define InputStreams to read from the URLConnection.
			 */
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			/*
			 * Read bytes to the Buffer until there is nothing more to read(-1).
			 */
			ByteArrayBuffer baf = new ByteArrayBuffer(5000);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			/* Convert the Bytes read to a String. */
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baf.toByteArray());
			fos.close();
			String down = file.toURI().toString();
			Log.d("PREFERENCES", "Descargado fichero en " + down.substring(5));

			return down.substring(5);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return null;
	}
}
