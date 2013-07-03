/*
SystemSoundUtil class
This class implements the functionality to configure all general sounds, as default tones and System Sounds as lock Screen sound. 

Copyright (c) 2013, Vodafone Spain Foundation
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Vodafone Spain Foundation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 */

package eu.fundacionvf.cloud.systemsettingpreics.util;

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
import android.media.AudioManager;
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

	private final String tag = "SystemSettingTAG";

	private AudioManager audMng;

	private Context cntx;

	private String MESSAGE_OK = "OK";
	private String MESSAGE_ERROR = "ERROR_SOUND";

	public SystemSoundsUtil(Context cntx) {
		super();

		this.cntx = cntx;
		audMng = (AudioManager) cntx.getSystemService(Context.AUDIO_SERVICE);

	}

	/**
	 * Enable the sounds effects (key clicks, lid open ...) This method needs a
	 * instance of SystemSoundUtil because the context is necessary.
	 * 
	 * @param enable
	 *            The value is boolean (1 or 0).
	 * @return void
	 * @see AudioManager
	 */

	public void enableSoundEffects(String enable) {
		try {

			Log.d(tag,
					"Sound effects: "
							+ Settings.System.getString(
									cntx.getContentResolver(),
									Settings.System.SOUND_EFFECTS_ENABLED)
							+ "voy a meter " + enable);
			int efect = Integer.parseInt(enable);

			if (efect == 0 || efect == 1) {
				Settings.System.putInt(cntx.getContentResolver(),
						Settings.System.SOUND_EFFECTS_ENABLED, efect);
			} else {
				Log.d(tag, "WHY?? sound efects");
			}
		} catch (Exception e) {
			Log.e(tag, "Enable:  " + enable);
			e.printStackTrace();
		}
	}

	/**
	 * Configure the volume of notification. This method needs a instance of
	 * SystemSoundUtil because an AudioManager instance is necessary.
	 * 
	 * @param level
	 *            The volume between 0-7
	 * @return String, "OK" if change successfully , "ERROR" if not
	 * @see AudioManager
	 */
	public String changeNotificationVolume(String level) {
		try {
			int volume = Integer.parseInt(level);
			audMng.setStreamVolume(AudioManager.STREAM_NOTIFICATION, volume, 0);
			return MESSAGE_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_ERROR;
		}

	}

	/**
	 * Configure the volume of music. This method needs a instance of
	 * SystemSoundUtil because an AudioManager instance is necessary.
	 * 
	 * @param level
	 *            The volume between 0-15
	 * @return String, "OK" if change successfully , "ERROR" if not
	 * @see AudioManager
	 */
	public String changeMusicVolume(String level) {
		try {
			int volume = Integer.parseInt(level);
			audMng.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
			return MESSAGE_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_ERROR;
		}

	}

	/**
	 * Configure the volume of alarm. This method needs a instance of
	 * SystemSoundUtil because an AudioManager instance is necessary.
	 * 
	 * @param level
	 *            The volume between 0-7
	 * @return String, "OK" if change successfully , "ERROR" if not
	 * @see AudioManager
	 */
	public String changeAlarmVolume(String level) {
		try {
			int volume = Integer.parseInt(level);
			audMng.setStreamVolume(AudioManager.STREAM_ALARM, volume, 0);
			return MESSAGE_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_ERROR;
		}

	}

	/**
	 * Configure the volume of dtmf. This method needs a instance of
	 * SystemSoundUtil because an AudioManager instance is necessary.
	 * 
	 * @param level
	 *            The volume between 0-15
	 * @return String, "OK" if change successfully , "ERROR" if not
	 * @see AudioManager
	 */
	public String changeDTMFVolume(String level) {
		try {
			int volume = Integer.parseInt(level);
			audMng.setStreamVolume(AudioManager.STREAM_DTMF, volume, 0);
			return MESSAGE_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_ERROR;
		}

	}

	/**
	 * Configure the volume of ring tone. This method needs a instance of
	 * SystemSoundUtil because an AudioManager instance is necessary.
	 * 
	 * @param level
	 *            The volume between 0-7
	 * @return String, "OK" if change successfully , "ERROR" if not
	 * @see AudioManager
	 */
	public String changeRingVolume(String level) {
		try {
			int volume = Integer.parseInt(level);
			audMng.setStreamVolume(AudioManager.STREAM_RING, volume, 0);
			return MESSAGE_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_ERROR;
		}

	}

	/**
	 * Configure the volume of dtmf. This method needs a instance of
	 * SystemSoundUtil because an AudioManager instance is necessary.
	 * 
	 * @param level
	 *            The volume between 0-7
	 * @return String, "OK" if change successfully , "ERROR" if not
	 * @see AudioManager
	 */
	public String changeSystemVolume(String level) {
		try {
			int volume = Integer.parseInt(level);
			audMng.setStreamVolume(AudioManager.STREAM_SYSTEM, volume, 0);
			return MESSAGE_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_ERROR;
		}

	}

	/**
	 * Configure the volume of voice call. This method needs a instance of
	 * SystemSoundUtil because an AudioManager instance is necessary.
	 * 
	 * @param level
	 *            volume between 0-7
	 * @return "OK" if change successfully , "ERROR" if not
	 * @see AudioManager
	 */
	public String changeVoiceCallVolume(String level) {
		try {
			int volume = Integer.parseInt(level);
			audMng.setStreamVolume(AudioManager.STREAM_VOICE_CALL, volume, 0);
			return MESSAGE_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_ERROR;
		}

	}

	/**
	 * Configure the default notification Sound. This method needs a instance of
	 * SystemSoundUtil because the context is necessary. Coming soon, there will
	 * be special sounds in our server for different user hear needs.
	 * 
	 * @param urlsound
	 *            A file URL to change the default Notification Sound.
	 * @return void
	 * 
	 */
	public void changeNotificationSound(String urlsound) {
		String path = Environment.getExternalStorageDirectory()
				+ "/cloud4AllSound/";
		String fileName = "notificationsound.ogg";
		try {
			URL url = new URL(urlsound);
			File myDir = new File(path);
			myDir.mkdir();
			File fi = new File(path, fileName);
			fi.delete();

			File file = new File(path, fileName);



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


		} catch (Exception e) {

			e.printStackTrace();

		}

		try {

			File k = new File(path, "notificationsound.ogg"); // path is a file
																// to
																// /sdcard/media/ringtone

			SharedPreferences prefs = cntx.getSharedPreferences(preferences,
					Context.MODE_PRIVATE);

			ContentValues values = new ContentValues();
			values.put(MediaStore.MediaColumns.DATA, k.getAbsolutePath());
			values.put(MediaStore.MediaColumns.TITLE, urlsound);
			values.put(MediaStore.MediaColumns.SIZE, k.getTotalSpace());
			values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
			values.put(MediaStore.Audio.Media.ARTIST, "What");
			values.put(MediaStore.Audio.Media.DURATION, 230);
			values.put(MediaStore.Audio.Media.IS_RINGTONE, false);
			values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
			values.put(MediaStore.Audio.Media.IS_ALARM, false);
			values.put(MediaStore.Audio.Media.IS_MUSIC, false);

			String olduri = Settings.System.getString(
					this.cntx.getContentResolver(),
					Settings.System.NOTIFICATION_SOUND);


			Uri defaulturi = RingtoneManager.getActualDefaultRingtoneUri(cntx,
					RingtoneManager.TYPE_NOTIFICATION);
	
			SharedPreferences.Editor editor = prefs.edit();

			editor.putString("NOTIFICATION_SOUND", olduri);
			editor.commit();

			// Insert it into the database
			Uri uri = MediaStore.Audio.Media.getContentUriForPath(k
					.getAbsolutePath());
			Log.d(tag, " uri: " + uri.toString());

			Uri newUri = this.cntx.getContentResolver().insert(uri, values);
			Log.d(tag, "new uri: " + newUri.toString());

			// put tone in the default (It's the same that write in System
			// Settings,
			// but without need to change)
			RingtoneManager.setActualDefaultRingtoneUri(this.cntx,
					RingtoneManager.TYPE_NOTIFICATION, newUri);

		} catch (Exception e) {
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
				Log.e(tag, "can't restore, there isn't a calue");
			}
		} catch (Exception e) {
			Log.e(tag, "No se ha podido restaurar");

			e.printStackTrace();
		}

	}

	/**
	 * Configure the default ringtone Sound. This method needs a instance of
	 * SystemSoundUtil because the context is necessary. Coming soon, there will
	 * be special sounds in our server for different user hear needs.
	 * 
	 * @param urldound
	 *            , change the default Ringtone Sound for a file download on own
	 *            server
	 * @return void
	 * 
	 * 
	 */
	public void changeRingtoneSound(String urlsound) {
		String fileName = Environment.getExternalStorageDirectory()
				+ "/cloud4AllSound/" + "ringtonesound.ogg";

		try {
			URL url = new URL(urlsound); // you can write here any link
			File fi = new File(fileName);
			if (fi.exists()) {
				boolean b = fi.delete();
				Log.i(tag, "delete: " + b);

			} else {
				Log.i(tag, "no exists");
			}

			File file = new File(fileName);

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

		} catch (Exception e) {

			e.printStackTrace();

		}

		try {
			String path = Environment.getExternalStorageDirectory()
					+ "/cloud4AllSound/";
			File k = new File(path, "ringtonesound.ogg"); // path is a file to
															// /sdcard

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

			// Storage in SharedPreferences

			prefs.edit().putString("RINGTONE_SOUND", olduri);
			prefs.edit().commit();

			// Insert it into the database
			Uri uri = MediaStore.Audio.Media.getContentUriForPath(k
					.getAbsolutePath());
			Uri newUri = this.cntx.getContentResolver().insert(uri, values);

			if (newUri != null) {

				// put tone in the default (It's the same that write in System
				// Settings,
				// but without need to change)
				RingtoneManager.setActualDefaultRingtoneUri(this.cntx,
						RingtoneManager.TYPE_RINGTONE, newUri);
			} else {
				Log.d(tag, "new uri: null");

			}
		} catch (Exception e) {
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
				Log.e(tag, "cannot restore, there isnt any value");
			}
		} catch (Exception e) {
			Log.e(tag, "cannot restore");

			e.printStackTrace();
		}

	}

	/**
	 * Sets the setting for when the vibrate Notification should vibrate.
	 * 
	 * @param setting
	 *            , string The vibrate setting, one of VIBRATE_SETTING_ON,
	 *            VIBRATE_SETTING_OFF, or VIBRATE_SETTING_ONLY_SILENT.
	 * @return String, "OK" if change successfully , "ERROR" if not
	 * 
	 */

	public String changeVibrateNotification(String setting) {
		try {

			int vibrateSetting = Integer.parseInt(setting);
			if (vibrateSetting <= 2)
				audMng.setVibrateSetting(audMng.VIBRATE_TYPE_NOTIFICATION,
						vibrateSetting);
			else {
				return MESSAGE_ERROR;
			}
			return MESSAGE_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_ERROR;
		}
	}

	/**
	 * Sets the setting for when the vibrate Rigntone should vibrate.
	 * 
	 * @param setting
	 *            , string The vibrate setting, one of VIBRATE_SETTING_ON,
	 *            VIBRATE_SETTING_OFF, or VIBRATE_SETTING_ONLY_SILENT.
	 * @return String, "OK" if change successfully , "ERROR" if not
	 * 
	 * @see AudioManager
	 */

	public String changeVibrateRigntone(String setting) {
		try {
			int vibrateSetting = Integer.parseInt(setting);
			if (vibrateSetting <= 2) {
				audMng.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER,
						vibrateSetting);
				Log.d(tag,
						"vibrtate setting"
								+ audMng.getVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER));
			} else {
				return MESSAGE_ERROR;
			}
			return MESSAGE_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return MESSAGE_ERROR;
		}
	}

}
