package cloud4all.qr;

import android.app.Activity;
import android.os.Bundle;

import cloud4all.Security.LoginReferences;

import com.google.zxing.integration.android.IntentIntegrator;

public class QRGenActivity extends Activity
{

	private IntentIntegrator ii;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ii = new IntentIntegrator(this);
		
		Bundle content = getIntent().getExtras();
		if (content!=null)
		{
			String
				user = content.getString(LoginReferences.USERNAME),
				pass = content.getString(LoginReferences.PASSWORD);
			// TODO create format login
			ii.shareText(user + "\n" + pass);
		}
		finish();
	}
}
