package cloud4all.UserListener.NFC;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.util.Log;

public class NfcWritingActivity extends NfcActivity
{
	
	protected NdefMessage msg;
	private PendingIntent pi = null;

	@Override
	protected void onResume()
	{
		super.onResume();
		nfcAdapter.enableForegroundDispatch(this, pi, null, null);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		nfcAdapter.disableForegroundDispatch(this);
	}

	@Override
	public void onNewIntent(Intent intent)
	{
		resolveIntent(intent);
	}
	
	private int writeNdefTag(Ndef ndef)
	{
		int ret = NfcKeys.errOK;
		try
		{
		    ndef.connect();
		    if (ndef.isWritable())
		    {	// Check if the tag can handle all the data
		    	int size = msg.toByteArray().length;
		    	if (size > ndef.getMaxSize()) ret = NfcKeys.errStorageSpace;
		    	else ndef.writeNdefMessage(msg);
		    }
		    else ret = NfcKeys.errProtected; // Tag is write-protected
		    ndef.close();
		    return ret;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return NfcKeys.errIO;
		}
	}
	
	private int formatTag(NdefFormatable ndeff)
	{
		try
		{
		    ndeff.connect();
		    ndeff.format(msg);
		    ndeff.close();
		    return NfcKeys.errOK;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return NfcKeys.errIO;
		}
	}
	
	private int writeMessageOnTag(Tag tag)
	{
		Ndef ndef = Ndef.get(tag);
		if (ndef!=null) return writeNdefTag(ndef);
		else
		{
			NdefFormatable ndeff = NdefFormatable.get(tag);
			if (ndeff!=null) return formatTag(ndeff);
			else { return NfcKeys.errNotSupported; } // Tag is not compatible
		}
	}

	protected void resolveIntent(Intent intent)
	{
		String action = intent.getAction();
		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) ||
			NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action) ||
			NfcAdapter.ACTION_TECH_DISCOVERED.equals(action))
		{
			if (msg!=null)
			{
				Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
				int res = writeMessageOnTag(tag);
				if (res==NfcKeys.errOK) { Log.i("Cloud4All Nfc", "Writing OK"); }
				else { Log.i("Cloud4All Nfc", "Writing Failed"); }
			}
		}
		finish();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		Intent intent = new Intent(this, getClass());
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		pi = PendingIntent.getActivity(this, 0, intent, 0);
	}
	
}