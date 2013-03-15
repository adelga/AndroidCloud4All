package eu.fundacionvf.cloud.systemsetting.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;

public class SystemSoundsUtil {
	private final static String preferences = "SystemSoundsPreferences";

	private final String tag = "SystemSettingTAG";

	private Context cntx;

	public SystemSoundsUtil(Context cntx) {
		super();

		this.cntx = cntx;
	}

	public void changeNotificationSound(String urlsound) {
		String fileName = Environment.getExternalStorageState()+ "/cloud4AllSound/" + "notificationsound.ogg";

		try{
            URL url = new URL(urlsound); //you can write here any link
            File file = new File(fileName);
			Log.d("PREFERENCES", "Run hilo");

				
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
	            Log.d("PREFERENCES", "Descargado fichero");
			
			
		}catch(Exception e){
			
			
			e.printStackTrace();
			
		}
		
		try{
			String path = Environment.getExternalStorageState()+ "/cloud4AllSound/";
			File k = new File(path, "notificationsound.ogg"); // path is a file to /sdcard/media/ringtone

		SharedPreferences prefs = cntx.getSharedPreferences(preferences,
				Context.MODE_PRIVATE);

		ContentValues values = new ContentValues();
		values.put(MediaStore.MediaColumns.DATA, k.getAbsolutePath());
		values.put(MediaStore.MediaColumns.TITLE, "My Song title");
		values.put(MediaStore.MediaColumns.SIZE, k.getTotalSpace());
		values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
		values.put(MediaStore.Audio.Media.ARTIST, "What");
		values.put(MediaStore.Audio.Media.DURATION, 230);
		values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
		values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
		values.put(MediaStore.Audio.Media.IS_ALARM, false);
		values.put(MediaStore.Audio.Media.IS_MUSIC, false);

		String olduri = Settings.System.getString(
				this.cntx.getContentResolver(), Settings.System.RINGTONE);
		Log.d(tag, "old uri: " + olduri.toString());

		// Storage in SharedPreferences

		prefs.edit().putString("NOTIFICATION_SOUND", olduri);
		prefs.edit().commit();

		// Insert it into the database
		Uri uri = MediaStore.Audio.Media.getContentUriForPath(k
				.getAbsolutePath());
		Log.d(tag, " uri: " + uri.toString());
		Uri newUri = this.cntx.getContentResolver().insert(uri, values);
		Log.d(tag, "new uri: " + newUri.toString());

		// put tone in the default (It's the same that write in System Settings,
		// but without need to change)
		RingtoneManager.setActualDefaultRingtoneUri(this.cntx,
				RingtoneManager.TYPE_NOTIFICATION, newUri);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void restoreNotificationSound() {
		try {
			SharedPreferences prefs = cntx.getSharedPreferences(preferences,
					Context.MODE_PRIVATE);
			String olduri = prefs.getString("NOTIFICATION_SOUND", "");
			Uri uri = Uri.parse(olduri);
			if (!olduri.equals("")) {
				// put tone in the default (It's the same that write in System
				// Settings, but without need to change)
				RingtoneManager.setActualDefaultRingtoneUri(this.cntx,
						RingtoneManager.TYPE_NOTIFICATION, uri);

			} else {
				Log.e(tag, "No se ha podido restaurar, no habia ningún valor");
			}
		} catch (Exception e) {
			Log.e(tag, "No se ha podido restaurar");

			e.printStackTrace();
		}

	}
	
	
	public void changeRingtoneSound(String urlsound) {
		String fileName = Environment.getExternalStorageState()+ "/cloud4AllSound/" + "ringtonesound.ogg";

		try{
            URL url = new URL(urlsound); //you can write here any link
            File file = new File(fileName);
			Log.d("PREFERENCES", "Run hilo");

				
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
	            Log.d("PREFERENCES", "Descargado fichero");
			
			
		}catch(Exception e){
			
			
			e.printStackTrace();
			
		}
		
		
		
		
		
		try{
			String path = Environment.getExternalStorageState()+ "/cloud4AllSound/";
			File k = new File(path, "ringtonesound.ogg"); // path is a file to /sdcard/media/ringtone

		SharedPreferences prefs = cntx.getSharedPreferences(preferences,
				Context.MODE_PRIVATE);

		ContentValues values = new ContentValues();
		values.put(MediaStore.MediaColumns.DATA, k.getAbsolutePath());
		values.put(MediaStore.MediaColumns.TITLE, "My Song title");
		values.put(MediaStore.MediaColumns.SIZE, k.getTotalSpace());
		values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
		values.put(MediaStore.Audio.Media.ARTIST, "What");
		values.put(MediaStore.Audio.Media.DURATION, 230);
		values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
		values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
		values.put(MediaStore.Audio.Media.IS_ALARM, false);
		values.put(MediaStore.Audio.Media.IS_MUSIC, false);

		String olduri = Settings.System.getString(
				this.cntx.getContentResolver(), Settings.System.RINGTONE);
		Log.d(tag, "old uri: " + olduri.toString());

		// Storage in SharedPreferences

		prefs.edit().putString("RINGTONE_SOUND", olduri);
		prefs.edit().commit();

		// Insert it into the database
		Uri uri = MediaStore.Audio.Media.getContentUriForPath(k
				.getAbsolutePath());
		Log.d(tag, " uri: " + uri.toString());
		Uri newUri = this.cntx.getContentResolver().insert(uri, values);
		Log.d(tag, "new uri: " + newUri.toString());

		// put tone in the default (It's the same that write in System Settings,
		// but without need to change)
		RingtoneManager.setActualDefaultRingtoneUri(this.cntx,
				RingtoneManager.TYPE_RINGTONE, newUri);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void restoreRingtoneSound() {
		try {
			SharedPreferences prefs = cntx.getSharedPreferences(preferences,
					Context.MODE_PRIVATE);
			String olduri = prefs.getString("RINGTONE_SOUND", "");
			Uri uri = Uri.parse(olduri);
			if (!olduri.equals("")) {
				// put tone in the default (It's the same that write in System
				// Settings, but without need to change)
				RingtoneManager.setActualDefaultRingtoneUri(this.cntx,
						RingtoneManager.TYPE_RINGTONE, uri);

			} else {
				Log.e(tag, "No se ha podido restaurar, no habia ningún valor");
			}
		} catch (Exception e) {
			Log.e(tag, "No se ha podido restaurar");

			e.printStackTrace();
		}

	}
	

	
	

}
