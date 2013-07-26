package eu.fundacionvf.cloud.systemsettingpreicsroot.test;

import org.json.JSONException;

import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.test.ServiceTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import eu.fundacionvf.cloud.systemsettingpreicsroot.*;

public class SettingHandlerServiceTest extends
		ServiceTestCase<SettingHandlerService> {

	private static final String TAG = "SettingHandlerServiceTest";

	public SettingHandlerServiceTest() {
		super(SettingHandlerService.class);
		// TODO Auto-generated constructor stub
	}

	public SettingHandlerServiceTest(Class<SettingHandlerService> serviceClass) {
		super(serviceClass);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setUp() {
		try {
			super.setUp();
			Log.i(TAG, "setUp()");
			Intent intent = new Intent(this.getContext(),
					SettingHandlerService.class);
			startService(intent);
			assertNotNull(getService());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void tearDown() {
		try {
			super.tearDown();
			Log.i(TAG, "tearDown()");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@LargeTest
	public void testVolumes() {
		Log.i(TAG, "testHello");
		try {
			// Test if it run properly
			CloudIntent info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("alarm_volume", "7");
			assertEquals(getService().MESSAGE_OK, getService().configure(info));
			info = new CloudIntent("SystemSettingHandler", 3610, 215);
			info.setParams("music_volume", "15");
			assertEquals(getService().MESSAGE_OK, getService().configure(info));
			info = new CloudIntent("SystemSettingHandler", 3610, 215);
			info.setParams("dtmf_volume", "15");
			assertEquals(getService().MESSAGE_OK, getService().configure(info));
			info = new CloudIntent("SystemSettingHandler", 3610, 215);
			info.setParams("notification_volume", "7");
			assertEquals(getService().MESSAGE_OK, getService().configure(info));
			info = new CloudIntent("SystemSettingHandler", 3610, 5558);
			info.setParams("ring_volume", "7");
			assertEquals(getService().MESSAGE_OK, getService().configure(info));
			info = new CloudIntent("SystemSettingHandler", 3610, 587);
			info.setParams("system_volume", "7");
			assertEquals(getService().MESSAGE_OK, getService().configure(info));
			info = new CloudIntent("SystemSettingHandler", 3610, 2145);
			info.setParams("voice_call_volume", "5");
			assertEquals(getService().MESSAGE_OK, getService().configure(info));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SmallTest
	public void testAlarmVolumeError() {
		Log.i(TAG, "testErrorVolukmes");
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("alarm_volume", "ji");
			assertEquals("ERROR:  / changeAlarm /", getService()
					.configure(info));
			// Test if return errors properly
			info = new CloudIntent("SystemSettingHandler", 3610, 215);
			info.setParams("alarm_volume", "10");
			assertEquals("ERROR:  / changeAlarm /", getService()
					.configure(info));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SmallTest
	public void testNotificationVolumeError() {
		Log.i(TAG, "testErrorVolukmes");
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("notification_volume", "4548745");
			assertEquals("ERROR:  / changeNotification /", getService()
					.configure(info));
			// Test if return errors properly
			info = new CloudIntent("SystemSettingHandler", 3610, 215);
			info.setParams("notification_volume", "-89");
			assertEquals("ERROR:  / changeNotification /", getService()
					.configure(info));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SmallTest
	public void testRingVolumeError() {
		Log.i(TAG, "testErrorVolukmes");
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("ring_volume", "pppp");
			assertEquals("ERROR:  / changeRing /", getService().configure(info));
			// Test if return errors properly
			info = new CloudIntent("SystemSettingHandler", 3610, 215);
			info.setParams("ring_volume", "8");
			assertEquals("ERROR:  / changeRing /", getService().configure(info));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SmallTest
	public void testMusicVolumeError() {
		Log.i(TAG, "testErrorVolukmes");
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("music_volume", "pppp");
			assertEquals("ERROR:  / changeMusic /", getService()
					.configure(info));
			// Test if return errors properly
			info = new CloudIntent("SystemSettingHandler", 3610, 215);
			info.setParams("music_volume", "-7");
			assertEquals("ERROR:  / changeMusic /", getService()
					.configure(info));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SmallTest
	public void testDTMFVolumeError() {
		Log.i(TAG, "testErrorVolukmes");
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("dtmf_volume", "stringggggg");
			assertEquals("ERROR:  / changeDTMF /", getService().configure(info));
			// Test if return errors properly
			info = new CloudIntent("SystemSettingHandler", 3610, 215);
			info.setParams("dtmf_volume", "-7");
			assertEquals("ERROR:  / changeDTMF /", getService().configure(info));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SmallTest
	public void testVoiceVolumeError() {
		Log.i(TAG, "testErrorVolukmes");
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("voice_call_volume", "pppp");
			assertEquals("ERROR:  / changeVoice /", getService()
					.configure(info));
			// Test if return errors properly
			info = new CloudIntent("SystemSettingHandler", 3610, 215);
			info.setParams("voice_call_volume", "7");
			assertEquals("ERROR:  / changeVoice /", getService()
					.configure(info));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SmallTest
	public void testEnableSoundsEffects() {
		Log.i(TAG, "testEnableSoundEffects");
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("sound_effects", "string not int");
			assertEquals("ERROR:  / changeEnableSounds /", getService()
					.configure(info));
			// Test if return errors properly
			info = new CloudIntent("SystemSettingHandler", 3610, 215);
			info.setParams("sound_effects", "7");
			assertEquals("ERROR:  / changeEnableSounds /", getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandler", 3610, 215);
			info.setParams("sound_effects", "0");
			assertEquals(getService().MESSAGE_OK, getService().configure(info));
			info = new CloudIntent("SystemSettingHandler", 3610, 215);
			info.setParams("sound_effects", "1");
			assertEquals("OK", getService().configure(info));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SmallTest
	public void testRingtoneSound() {
		Log.i(TAG, "testEnableSoundEffects");
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("ringtone_sound", "stringggggggggg");
			assertEquals("ERROR:  / changeRingtoneSound /", getService()
					.configure(info));
			// Test if return errors properly
			info = new CloudIntent("SystemSettingHandler", 3610, 215);
			info.setParams("ringtone_sound", "/sdcard/prueba.ogg");
			assertEquals("ERROR:  / changeRingtoneSound /", getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandler", 3610, 215);
			info.setParams("ringtone_sound",
					"http://www.fundacionvf.es/prueba.ogg");
			assertEquals(getService().MESSAGE_OK, getService().configure(info));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SmallTest
	public void testNotificationSound() {

		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("notification_sound", "stringggggggggg");
			assertEquals("ERROR:  / changeNotificationSound /", getService()
					.configure(info));
			// Test if return errors properly
			info = new CloudIntent("SystemSettingHandler", 3610, 215);
			info.setParams("notification_sound", Environment
					.getExternalStorageDirectory().getPath() + "/prueba.ogg");
			assertEquals("ERROR:  / changeNotificationSound /", getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandler", 3610, 215);
			info.setParams("notification_sound",
					"http://www.fundacionvf.es/whistle.ogg");
			assertEquals(getService().MESSAGE_OK, getService().configure(info));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SmallTest
	public void testBrightness() {

		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("brightness_mode","0");
			info.setParams("brightness", "187");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			// Test if return errors properly
			info = new CloudIntent("SystemSettingHandler", 3610, 215);
			info.setParams("brightness_mode","1");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("brightness_mode","0");
			info.setParams("brightness", "stringgg");
			assertEquals("ERROR:  / changeBrightness /", getService()
					.configure(info));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SmallTest
	public void testScreenOff() {
	
		try {
			// Test 
			CloudIntent info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("screen_time_off","20");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			// Test if return errors properly
			info = new CloudIntent("SystemSettingHandler", 3610, 215);
			info.setParams("screen_time_off","dffffff");
			assertEquals("ERROR:  / changeScreenTime /", getService()
					.configure(info));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@SmallTest
	public void testDimScreen() {
		Log.i(TAG, "testdim");
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("dim_screen", "0");
			assertEquals(Build.VERSION.SDK_INT < 17 ? getService().MESSAGE_OK : "ERROR:  / changeDim /" , getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("dim_screen", "1");
			assertEquals(Build.VERSION.SDK_INT < 17 ? getService().MESSAGE_OK : "ERROR:  / changeDim /" , getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandler", 3470,
					215);
			info.setParams("dim_screen","ssss");
			assertEquals("ERROR:  / changeDim /", getService()
					.configure(info));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SmallTest
	public void testHapticFeedback() {
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("haptic_feedback", "0");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("haptic_feedback", "1");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("haptic_feedback","ssss");
			assertEquals("ERROR:  / changeHaptic /", getService()
					.configure(info));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SmallTest
	public void testAutoRotation() {
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandler", 5410,
					215);
			info.setParams("auto_rotation", "0");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("auto_rotation", "1");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("auto_rotation","hhhuguf");
			assertEquals("ERROR:  / changeAutoRotation /", getService()
					.configure(info));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SmallTest
	public void testDefaultRotation() {
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("default_rotation", "0");
			assertEquals(Build.VERSION.SDK_INT>11? getService().MESSAGE_OK: "ERROR:  / changeDefaultRotation /", getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("default_rotation", "3");
			assertEquals(Build.VERSION.SDK_INT>11? getService().MESSAGE_OK: "ERROR:  / changeDefaultRotation /", getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("default_rotation","ssss");
			assertEquals("ERROR:  / changeDefaultRotation /", getService()
					.configure(info));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@SmallTest
	public void testFontScale() {
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("font_scale", "0.75");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("font_scale", "2.0");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandler", 3610,
					215);
			info.setParams("font_scale","ssss");
			assertEquals("ERROR:  / changeFontScale /", getService()
					.configure(info));
		} catch (Exception e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

}
