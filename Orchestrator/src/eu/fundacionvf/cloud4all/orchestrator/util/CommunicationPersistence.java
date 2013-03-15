package eu.fundacionvf.cloud4all.orchestrator.util;

public class CommunicationPersistence {
	
	//ACTION Strings
	public final static String ACTION_ORCHESTRATOR= "cloudOrchestrator";
	public final static String ACTION_DEVICE_REPORTER= "cloudDeviceReporter";
	public final static String ACTION_NFC_USER_LISTENER= "cloudNFCListener";
	public final static String ACTION_SYSTEM_SETTING_HANDLER ="cloudSystemSetting";

	//EVENT Strings
	public final static int EVENT_CONFIGURE_SYSTEM_SETTINGS = 110;
	public final static int EVENT_CONFIGURE_SYSTEM_SETTINGS_RESPONSE = 111;
	
	
	public final static int EVENT_RESTORE_SYSTEM_SETTINGS = 115;
	public final static int EVENT_RESTORE_SYSTEM_SETTINGS_RESPONSE = 116;
	
	public final static int EVENT_GET_FEATURES = 130;
	public final static int EVENT_GET_FEATURES_RESPONSE = 131;
	
	

	//MODULE Strings
	public final static int MODULE_ORCHESTRATOR = 1;
	public final static int MODULE_DEVICE_REPORTER = 2;
	
	public final static int MODULE_PRUEBAS = 3;

}
