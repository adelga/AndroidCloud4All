package eu.cloud.fundacionvf.systemsettingshandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	String storage= "fffefefefe";
	String filename= "pppp";
	final String actionHandler = "eu.cloud.fundacionvf.systemsettingshandler.ACTIVE";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    	Intent intent = new Intent();
		intent.setAction(actionHandler);
		intent.putExtra("IdEvent", 12);
		intent.putExtra("IdModule", 1);
		intent.putExtra("Params", storage + filename);
		Log.d("MAIN", "vamos a lanzar el intent");
		sendBroadcast(intent);
		Log.d("MAIN", "ya ta lanzado");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
