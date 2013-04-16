package eu.fundacionvf.cloud4all.orchestrator.persistence;

import java.io.File;
import java.io.IOException;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import android.os.Environment;

public class PersistenceXML extends Persistence {
	private final static String FILENAME = Environment.getExternalStorageDirectory().getPath()+"/download/Persistence.xml";
	private final static String FILENAME_SESSION = Environment.getExternalStorageDirectory().getPath()+"/download/Session.xml";
	Persistence p = new Persistence();
	Session s = new Session();
	//Persistence s = new Persistence();
	Serializer serializer = new Persister();

	public PersistenceXML() {

		// Start XML Parser
		//Load Persistence XML
		File source = new File(FILENAME);
		if(!source.exists()){
			try {
				source.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			p = serializer.read(Persistence.class, source);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Load Session XML
		File sessionSource = new File(FILENAME_SESSION);
		if(!sessionSource.exists()){
			try {
				sessionSource.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try{
			s = serializer.read(Session.class, sessionSource);
		//	s = serializer.read(Persistence.class, sessionSource);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public Persistence getPersistence() {
		return p;
	}
	
	public Session getSession(){
		return s;
	}
	public void writeXML(Session s){
		File result = new File(FILENAME_SESSION);
		try {
			serializer.write(s, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
