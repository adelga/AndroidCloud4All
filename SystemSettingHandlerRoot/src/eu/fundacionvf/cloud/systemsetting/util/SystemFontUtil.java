package eu.fundacionvf.cloud.systemsetting.util;

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
	 * Configure the scale font This method needs a instance of SystemFontUtil
	 * because the context is necessary
	 * 
	 * @param scale
	 *            , font size (1.0 it's normal, shouldn't more than 2.0)
	 * @return String, "OK" if all run properly, "ERROR": in other case
	 */
	public String changeFontScale(String scale) {
		//Comprobar que el scale sería como float
		try {
			float f = Float.parseFloat(scale);
			
			Settings.System.putString(cntx.getContentResolver(),
					Settings.System.FONT_SCALE, scale);
			return MESSAGE_OK;
		}catch(NumberFormatException ne){
			Log.e(TAG, "Scale not have the properly format");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return MESSAGE_ERROR;
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
				//Incluir espera
				String[] hin1 = { "su", "-c",
						"cp /system/fonts/Roboto-Regular.ttf /system/fonts/Roboto-Regular_org.ttf" };

				Runtime.getRuntime().exec(hin1);

				String[] hin2 = { "su", "-c",
						"cp " + url + " /system/fonts/Roboto-Regular.ttf" };

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

				String[] hin1 = { "su", "-c",
						"cp /system/fonts/Roboto-Regular.ttf /system/fonts/Roboto-Regular_org.ttf" };

				Runtime.getRuntime().exec(hin1).waitFor();

				downlName= "/mnt/sdcard/cloud4AllFont/newType.ttf";
				
				String[] hin2 = { "su", "-c",
						"cp " + downlName + " /system/fonts/Roboto-Regular.ttf" };

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
					"cp  /system/fonts/Roboto-Regular_org.ttf /system/fonts/Roboto-Regular.ttf" };

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
	 * @param local, if the font it's in local (0) or remote (1)
	 * @param url, the address of new font type
	 * @return String, "OK" if all run properly, "ERROR": in other case
	 */
	public String changeBoldFontType(int local, String url) {

		if (local == LOCAL_URL) {
			Log.d(TAG, "local URL");
			try {
				String[] hin1 = { "su", "-c",
						"cp /system/fonts/Roboto-Bold.ttf /system/fonts/Roboto-Bold_org.ttf" };

				Runtime.getRuntime().exec(hin1);

				String[] hin2 = { "su", "-c",
						"cp " + url + " /system/fonts/Roboto-Bold.ttf" };

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

				String[] hin1 = { "su", "-c",
						"cp /system/fonts/Roboto-Bold.ttf /system/fonts/Roboto-Bold_org.ttf" };

				Runtime.getRuntime().exec(hin1).waitFor();

				String[] hin2 = { "su", "-c",
						"cp " + downlName + " /system/fonts/Roboto-Bold.ttf" };

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
					"cp  /system/fonts/Roboto-Bold_org.ttf /system/fonts/Roboto-Bold.ttf" };

			Runtime.getRuntime().exec(hin1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}
	
	/**
	 * Configure the Italic font type. This method needs a instance of
	 * SystemFontUtil because the context is necessary REBOOT it's necessary
	 * 
	 * @param local, if the font it's in local (0) or remote (1)
	 * @param url, the address of new font type
	 * @return String, "OK" if all run properly, "ERROR": in other case
	 */
	public String changeItalicFontType(int local, String url) {

		if (local == LOCAL_URL) {
			Log.d(TAG, "local URL");
			try {
				String[] hin1 = { "su", "-c",
						"cp /system/fonts/Roboto-Italic.ttf /system/fonts/Roboto-Italic_org.ttf" };

				Runtime.getRuntime().exec(hin1);

				String[] hin2 = { "su", "-c",
						"cp " + url + " /system/fonts/Roboto-Italic.ttf" };

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

				String[] hin1 = { "su", "-c",
						"cp /system/fonts/Roboto-Italic.ttf /system/fonts/Roboto-Italic_org.ttf" };

				Runtime.getRuntime().exec(hin1).waitFor();

				String[] hin2 = { "su", "-c",
						"cp " + downlName + " /system/fonts/Roboto-Italic.ttf" };

				Runtime.getRuntime().exec(hin2);

				return "OK";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "ERROR";
			}
		}
	}
	
	
	public void restoreFontItalic() {
		try {

			String[] hin1 = { "su", "-c",
					"cp  /system/fonts/Roboto-Italic_org.ttf /system/fonts/Roboto-Italic.ttf" };

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
			String down= file.toURI().toString();
			Log.d("PREFERENCES", "Descargado fichero en "
					+ down.substring(5));

			return down.substring(5);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return null;
	}
}
