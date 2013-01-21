package example.cliente;

import org.json.JSONException;

import example.cliente.persistence.CommunicationPersistence;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					CloudIntent intent = new CloudIntent(CommunicationPersistence.ACTION_SYSTEM_SETTING_HANDLER,CommunicationPersistence.EVENT_CONFIGURE_SYSTEM_SETTINGS,CommunicationPersistence.MODULE_ORCHESTRATOR);

					intent.setParams("Parametro 1", "probando");

					intent.setParams("Parametro2", "probando");
					sendBroadcast(intent);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
