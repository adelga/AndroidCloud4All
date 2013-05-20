/*
Persistence class
This class is part of the Persistence Object

Copyright (c) 2013, Vodafone Spain Foundation
All rights reserved.

The research leading to these results has received funding from the European Union's Seventh Framework Programme (FP7/2007-2013) under grant agreement n° 289016

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of Vodafone Spain Foundation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 */
package eu.fundacionvf.cloud4all.orchestrator.persistence;

import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

/**
 *This class is part of the Persistence Object	
 *@author Vodafone Spain Foundation
 *@version 1.0
 */
@Root
public class Persistence {
	@ElementArray
	private Trigger[] triggers;
	@ElementArray
	private Process[] processes;

	public Persistence() {
	}

	public Trigger[] getTriggers() {
		return triggers;
	}

	/**Get Array of processes 
	 * @return return Array of processes
	 */
	public Process[] getProcesses() {
		return processes;
	}

	public void setProcess(Process process, int index) {
		this.processes[index] = process; // put the instance in the correct
											// bucket
	}

	// UTIL METHODS
	public int findIdProccessByIdTrigger(int idTrigger) {
		int result = -1;
		int i = 0;
		boolean founded = false;
		while (i < triggers.length && !founded) {
			if (triggers[i].getIdTrigger() == idTrigger) {
				result = triggers[i].getIdProcess();
				founded = true;
			}
			i++;
		}
		return result;
	}

	public Process getProcessByIdProcess(int idProcess) {
		int i = 0;
		Process process = null;
		boolean founded = false;
		while (i < processes.length && !founded) {
			if (processes[i].getIdProcess() == idProcess) {
				process = processes[i];
				founded = true;
			}
			i++;
		}
		return process;
	}

	public Action[] getActionsByIdTrigger(int idTrigger) {
		int idProccess = findIdProccessByIdTrigger(idTrigger);
		int i = 0;
		Action[] actions = null;
		boolean founded = false;
		while (i < processes.length && !founded) {
			if (processes[i].getIdProcess() == idProccess) {
				actions = processes[i].getListActions();
				founded = true;
			}
			i++;
		}
		return actions;
	}

	public boolean isRequest(int idTrigger) {
		boolean request = false;
		int i = 0;
		while (i < triggers.length && !request) {
			if (triggers[i].getIdTrigger() == idTrigger) {
				request = true;
			}
			i++;
		}
		return request;
	}
}
