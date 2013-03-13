package cloud4all.UserListener.NFC;

import android.nfc.NdefMessage;

public abstract class NdefTag
{

	/**
	 * Generic Mime Type for the record.
	 */
	public static final String MIME_TYPE = "cloud4all/cloud4all.userlistener.nfc";
	
	/**
	 * Abstraction of the NdefMessage creation for generic fields.
	 */
	public abstract NdefMessage createNdefMessage();

}
