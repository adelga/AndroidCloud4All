package example.cliente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ClienteActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
				try{
				Log.e("Test Broadcast","lanzado!");
				Log.e("Test Broadcast","lanzado!");
				CloudIntent intent = new CloudIntent("examplebroadcast",2,5);
				intent.setParams("prueba", "probando");
				intent.setParams("prueba2", "lo pintara????");
				intent.setFlags(Intent.FLAG_DEBUG_LOG_RESOLUTION);
				sendBroadcast(intent);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
    }
}