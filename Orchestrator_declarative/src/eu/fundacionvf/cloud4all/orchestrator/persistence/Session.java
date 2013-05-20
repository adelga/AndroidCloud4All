/*
Session class
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

import org.json.JSONException;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

import android.util.Log;

import eu.fundacionvf.cloud4all.orchestrator.util.CloudIntent;

@Root
public class Session {
	@ElementArray
	private Process[] processes;
	
	public Session() {
	}

	public void newSession() {
		processes = new Process[1];
	}

	public Process[] getProcesses() {
		return processes;
	}

	public void setProcess(Process process, int index) {
		this.processes[index] = process; 
	}
	
	
	// UTIL METHODS
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
	
	public int getMaxIdAction(){
		int idAction = 0;
		for(int i=0; i<processes.length; i++){
			for(int j=0; j<processes[i].getListActions().length;j++){
				if(idAction < processes[i].getListActions()[j].getIdAction()){
				idAction = processes[i].getListActions()[j].getIdAction();
				}
			}
		}
		return idAction;
	}

	public int getIdProcessByIdAction(int idAction) {
		int i = 0;
		int j = 0;
		int idProcess = 0;
		boolean found = false;
		while (i < processes.length && !found) {
			j = 0;
			while (j < processes[i].getListActions().length && !found) {
				if (processes[i].getListActions()[j].getIdAction() == idAction) {
					idProcess = processes[i].getIdProcess();
					found = true;
					Log.i("CLOUD4ALL", "idProcess encontrado:"+idProcess);
				}
				j++;
			}
			i++;
		}
		return idProcess;
	}

	public Process getProcessByIdAction(int idAction) {
		int i = 0;
		int j = 0;
		int idProcess = 0;
		boolean found = false;
		
		while (i < processes.length && !found) {
			j = 0;
			while (j < processes[i].getListActions().length && !found) {
				if (processes[i].getListActions()[j].getIdAction() == idAction) {
					idProcess = processes[i].getIdProcess();
					found = true;
				}
				j++;
			}
			i++;
		}
		Log.i("CLOUD4ALL", "getProcessByIdAction - indice proceso antes return:"+(i-1));
		return processes[i - 1];
	}

	public int indexOfProcess(int idAction) {
		int i = 0;
		int idProcess = getIdProcessByIdAction(idAction);
		boolean found = false;
		while (i < processes.length && !found) {
			if (processes[i].getIdProcess() == idProcess) {
				found = true;
			}
			i++;
		}
		return i - 1;
	}
	//Only for firstAction
	public int indexOfProcessByIdProcess(int idProcess){
		int i = 0;
		boolean found = false;
		while(i < processes.length && !found){
			if(processes[i].getIdProcess() == idProcess){
				found = true;
			}
			i++;
		}
		return i-1;
	}

	public boolean nextActionExist(int idAction) {
		Log.i("NextActionExist", "idAction:" + idAction);
		boolean nextAction = false;
		int i = 0;
		Process process = getProcessByIdAction(idAction);
		Action[] actions = process.getListActions();
		while (i < actions.length && !nextAction) {
			if (actions[i].getIdAction() == idAction) {
				if ((i + 1) < actions.length) {
					nextAction = true;
				}
			}
			i++;
		}
		return nextAction;
	}

	public int indexOfAction(int idAction) {
		int i = 0;
		Process process = getProcessByIdAction(idAction);
		Action[] actions = process.getListActions();
		boolean found = false;
		while (i < actions.length && !found) {
			if (actions[i].getIdAction() == idAction) {
				found = true;
			}
			i++;
		}
		return i - 1;
	}

	public Action[] getActionsByIdProcess(int idAction) {
		int i = 0;
		Process process = getProcessByIdAction(idAction);
		Action[] actions = process.getListActions();
		return actions;
	}

	public Action getActionByIdAction(int idAction, int idProcess) {
		int i = 0;
		Process process = getProcessByIdProcess(idProcess);
		Action[] actions = process.getListActions();
		Action action = null;
		boolean founded = false;
		while (i < actions.length && !founded) {
			if (actions[i].getIdAction() == idAction) {
				action = actions[i];
				founded = true;
			}
			i++;
		}
		return action;
	}

	public Action getActionByOrderAction(int orderNum, int idProcess) {
		int i = 0;
		Process process = getProcessByIdProcess(idProcess);
		Action[] actions = process.getListActions();
		Action action = null;
		boolean founded = false;
		while(i < actions.length && !founded){
			if (actions[i].getOrderNumber() == orderNum) {
				action = actions[i];
				founded = true;
			}
			i++;
		}
		return action;
	}
	
	public void addParameters(CloudIntent cloudinfo) {
		try {
			boolean action = false;
			int idAction = cloudinfo.getIdAction();
			String[] listaIds = cloudinfo.getArrayIds();
			int i = 0;
			int j = 0;
			// Find action where idaction is in the structure
			while (i < processes.length && !action) {
				j = 0;
				while (j < processes[i].getListActions().length && !action) {
					if (processes[i].getListActions()[j].getIdAction() == idAction) {
						action = true;
					}
					j++;
				}
				i++;
			}
			if(action){
			if (processes[i - 1].getListActions()[j - 1].getResponse().getParams().length != listaIds.length) {
				processes[i - 1].getListActions()[j - 1].getResponse().params = new Param[listaIds.length];
			}
			// Fill the params received
			for (int k = 0; k < processes[i - 1].getListActions()[j - 1].getResponse().getParams().length; k++) {
				processes[i - 1].getListActions()[j - 1].getResponse().getParams()[k].setKey(listaIds[k]);
				processes[i - 1].getListActions()[j - 1].getResponse().getParams()[k].setValue(cloudinfo.getValue(listaIds[k]));
			}
			// processes[i-1].getListActions()[j-1].setIdAction(idActionReq);
			processes[i - 1].getListActions()[j - 1].getResponse().setIdEvent(cloudinfo.getIdEvent());
			String strAction = processes[i - 1].getListActions()[j - 1].getRequest().getIdModule();
			processes[i - 1].getListActions()[j - 1].getResponse().setIdModule(strAction);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean haveProcesses() {
		if (processes == null) {
			return false;
		} else {
			return true;
		}

	}

	public boolean processExist(int idProcess) {
		boolean process = false;
		int i = 0;
		while (i < processes.length && !process) {
			if (processes[i].getIdProcess() == idProcess) {
				process = true;
			}
			i++;
		}
		return process;
	}

	public void addProcess(Process process) {
		// Add Response to every action in the process
		for (int i = 0; i < process.getListActions().length; i++) {
			process.getListActions()[i].response = new Response(0, 0, "0");
		}
		int lon = processes.length + 1;
		Process[] auxProcesses = new Process[lon];
		System.arraycopy(processes, 0, auxProcesses, 0, lon - 1);
		auxProcesses[lon - 1] = process;
		processes = new Process[lon];
		System.arraycopy(auxProcesses, 0, processes, 0, lon);
	}
}
