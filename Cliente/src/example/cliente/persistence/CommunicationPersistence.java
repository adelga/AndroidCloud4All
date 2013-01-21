package example.cliente.persistence;

public class CommunicationPersistence {

	public final static String ACTION_ORCHESTRATOR= "cloudOrchestrator";
	public final static String ACTION_DEVICE_REPORTER= "cloudDeviceReporter";
	public final static String ACTION_NFC_USER_LISTENER= "cloudNFCListener";
	public final static String ACTION_SYSTEM_SETTING_HANDLER ="cloudSystemSetting";
	
	
	public final static int EVENT_CONFIGURE_SYSTEM_SETTINGS = 11;
	
	public final static int MODULE_ORCHESTRATOR = 1;
	public final static int MODULE_DEVICE_REPORTER = 2;
	
}
