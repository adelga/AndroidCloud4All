package cloud4all.UserListener.NFC;

public class NfcKeys
{
	
	public static final String
		bcast = "cloud4all",
		mode = "mode",
		id = "ID",
		err = "err";
	
	public static final int
		errOK = 0,
		errIO = -1,
		errProtected = -2,
		errStorageSpace = -3,
		errNotSupported = -4;
}
