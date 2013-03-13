package cloud4all.qr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRScanActivity extends Activity
{

	private IntentIntegrator ii;
	
	
	private void backNoBarcode()
	{
		// Do something
	}
	
    protected void onActivityResult(int requestCode, int resultCode, Intent i) 
    {
    	if (requestCode!=RESULT_OK)
    	{
    		IntentResult res = IntentIntegrator.parseActivityResult(requestCode, resultCode, i);
    		if (res!=null && res.getContents()!=null)
    		{
    			String contents = res.getContents();
    			if (contents!=null)
    			{
    				Log.i("Found content in NFC tag!", contents);
    				Toast.makeText(getApplicationContext(), contents, Toast.LENGTH_LONG).show();
    				// TODO crear objeto login
    			}
    		}
    		else backNoBarcode();
    	}
    	else backNoBarcode();
    	finish();
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		setContentView(layoutResID);
		ii = new IntentIntegrator(this);
		ii.initiateScan();
	}
}
