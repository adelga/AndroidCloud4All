package eu.fundacionvf.cloud.systemsettingroot.test;

import org.json.JSONException;

import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.test.ServiceTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import eu.fundacionvf.cloud.systemsettingroot.CloudIntent;
import eu.fundacionvf.cloud.systemsettingroot.SettingHandlerService;

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

	
	
	@SmallTest
	public void testEnableAccessibility() {
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("enable_accessibility", "0");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("enable_accessibility", "1");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("enable_accessibility","dfdsfert");
			assertEquals("ERROR:  / EnableAccessibility /", getService()
					.configure(info));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@SmallTest
	public void testAccService() {
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("add_accessibility_service", "com.google.android.marvin.talkback/com.google.android.marvin.talkback.TalkBackService");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("enable_accessibility", "1");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@SmallTest
	public void testRemoveAccService() {
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("remove_accessibility_service", "com.google.android.marvin.talkback/com.google.android.marvin.talkback.TalkBackService");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("enable_accessibility", "1");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SmallTest
	public void testDefaultIme() {
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("default_ime", "ca.idi.tekla/.ime.TeclaIME");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SmallTest
	public void testTTSEngine() {
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("tts_engine", "com.google.android.tss");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		

	
	@SmallTest
	public void testEnableTouchMode() {
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("touch_mode", "0");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("touch_mode", "1");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("touch_mode","dfdsfert");
			assertEquals("ERROR:  / TouchMode /", getService()
					.configure(info));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SmallTest
	public void testTTSPich() {
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("tts_pitch", "100");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("tts_pitch", "300");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("tts_pitch","dfdsfert");
			assertEquals("ERROR:  / TTSPitch /", getService()
					.configure(info));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@SmallTest
	public void testTTSRate() {
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("tts_rate", "300");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("tts_rate", "100");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("tts_rate","588");
			assertEquals("ERROR:  / TTSRate /", getService()
					.configure(info));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@MediumTest
	public void testFontScale() {
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("font_scale", "0.75");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("font_scale", "2.0");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("font_scale","ssss");
			assertEquals("ERROR:  / changeFontScale /", getService()
					.configure(info));
		} catch (Exception e) {
			e.printStackTrace();
		}
}
	@MediumTest
	public void testLockSound() {
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("lock_sound", "Tono3-250.ogg");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandlerRoot", 4570,
					215);
			info.setParams("lock_sound", "jjugtcsd");
			assertEquals("ERROR:  / LockSound /", getService()
					.configure(info));

		} catch (Exception e) {
			e.printStackTrace();
		}
}
	
	@MediumTest
	public void testunLockSound() {
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("unlock_sound", "Tono2-600.ogg");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandlerRoot", 4570,
					215);
			info.setParams("unlock_sound", "jjugtcsd");
			assertEquals("ERROR:  / UnlockSound /", getService()
					.configure(info));

		} catch (Exception e) {
			e.printStackTrace();
		}
}
	
	@MediumTest
	public void testTickkSound() {
		try {
			// Test if return errors properly
			CloudIntent info = new CloudIntent("SystemSettingHandlerRoot", 3610,
					215);
			info.setParams("tick_sound", "Tono1-1000.ogg");
			assertEquals(getService().MESSAGE_OK, getService()
					.configure(info));
			info = new CloudIntent("SystemSettingHandlerRoot", 4570,
					215);
			info.setParams("tick_sound", "jjugtcsd");
			assertEquals("ERROR:  / TickSound /", getService()
					.configure(info));

		} catch (Exception e) {
			e.printStackTrace();
		}
}
	
}
