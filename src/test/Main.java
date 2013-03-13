package test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cloud4all.Security.LoginReferences;
import cloud4all.UserListener.NFC.NdefLogin;
import cloud4all.UserListener.NFC.NfcWritingActivity;
import cloud4all.UserListener.NFC.R;
import cloud4all.qr.QRGenActivity;
import cloud4all.qr.QRScanActivity;

public class Main extends NfcWritingActivity
{
	private Button button;
	
	private String
		user = "user",
		pass = "12345";
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        button = (Button)findViewById(R.id.buttonnfc);
        button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
				msg = new NdefLogin(user, pass).createNdefMessage();
			}
		});
        
        button = (Button)findViewById(R.id.buttonqrgen);
        button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), QRGenActivity.class);
				intent.putExtra(LoginReferences.USERNAME, user);
				intent.putExtra(LoginReferences.PASSWORD, pass);
				startActivity(intent);
			}
		});
        
        button = (Button)findViewById(R.id.buttonqrread);
        button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(), QRScanActivity.class);
				startActivity(intent);
			}
		});
    }
}