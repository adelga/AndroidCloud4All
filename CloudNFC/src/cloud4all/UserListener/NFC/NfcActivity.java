package cloud4all.UserListener.NFC;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;

public abstract class NfcActivity extends Activity
{

	protected NfcAdapter nfcAdapter;
	protected abstract void resolveIntent(Intent intent);
	
}