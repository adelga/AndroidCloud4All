package cloud4all.UserListener.NFC;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import cloud4all.Security.HashUtils;

public class NdefLogin extends NdefTag
{

	/**
	 * The username to be recorded into an NFC tag.
	 */
	private String user;
	
	/**
	 * The password of the user to be recorded into an NFC tag.
	 * Its formatted as a 40 hexadecimal characters SHA1 String hash.
	 */
	private String pass;
	
	/**
	 * Creates an instance of the LoginNdef allowing to create
	 * NdefMessages containing a pair pairs (username, password)
	 * @param username The username of the user.
	 * @param password The plain password of the user. Will be hashed.
	 */
	public NdefLogin(String username, String password)
	{
		user = username;
		pass = HashUtils.SHA1(password);
	}

	/**
	 * Provides a NdefMessage structure with a pair (username, password).
	 * @return The NdefMessage.
	 */
	@Override
	public NdefMessage createNdefMessage()
	{
        byte[]
             id = new byte[0],
             mime = MIME_TYPE.getBytes(),
             u = user.getBytes(),
             p = pass.getBytes();
        NdefRecord userRec = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, mime, id, u);
        NdefRecord passRec = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, mime, id, p);
        NdefRecord[] recs = { userRec, passRec };
        NdefMessage msg = new NdefMessage(recs);
        return msg;
	}

}
