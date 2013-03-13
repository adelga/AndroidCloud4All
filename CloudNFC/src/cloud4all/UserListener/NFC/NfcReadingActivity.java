package cloud4all.UserListener.NFC;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

public class NfcReadingActivity extends NfcActivity
{

	protected void resolveIntent(Intent intent)
	{
		String action = intent.getAction();
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action))
		{
			Parcelable[] rawMsg = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			if (rawMsg!=null)
			{
				NdefMessage msg = (NdefMessage)rawMsg[0];
				NdefRecord[] recs = msg.getRecords();
				for (NdefRecord rec : recs)
				{
					String text = new String(rec.getPayload());
					Log.i("Found content in NFC tag!", text);
					// TODO crear objeto login
				}	
			}
			finish();
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		resolveIntent(getIntent());
	}

}