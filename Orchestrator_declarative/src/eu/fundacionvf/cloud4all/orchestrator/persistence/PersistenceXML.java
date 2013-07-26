/*
PersistenceXML class
This class manage the read and write of persistence and session file

Copyright (c) 2013, Vodafone Spain Foundation
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n� 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Vodafone Spain Foundation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 */
package eu.fundacionvf.cloud4all.orchestrator.persistence;

import java.io.File;
import java.io.IOException;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import android.os.Environment;

public class PersistenceXML extends Persistence {
	private final static String FILENAME = Environment.getExternalStorageDirectory().getPath()+ "/download/Persistence.xml";
	private final static String FILENAME_SESSION = Environment.getExternalStorageDirectory().getPath() + "/download/Session.xml";
	Persistence p = new Persistence();
	Session s = new Session();
	Serializer serializer = new Persister();

	public PersistenceXML() {

		// Start XML Parser
		// Load Persistence XML
		File source = new File(FILENAME);
		if (!source.exists()) {
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
		// Load Session XML
		File sessionSource = new File(FILENAME_SESSION);
		if (!sessionSource.exists()) {
			try {
				sessionSource.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			s = serializer.read(Session.class, sessionSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Persistence getPersistence() {
		return p;
	}

	public Session getSession() {
		return s;
	}

	public void writeXML(Session s) {
		File result = new File(FILENAME_SESSION);
		try {
			serializer.write(s, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}