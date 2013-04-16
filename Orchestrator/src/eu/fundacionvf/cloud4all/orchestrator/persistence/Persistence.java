package eu.fundacionvf.cloud4all.orchestrator.persistence;

import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

@Root
public class Persistence {
	//@ElementList
	//ArrayList<Trigger> triggers = new ArrayList<Trigger>();
	//@ElementList
	//ArrayList<Process> processes = new ArrayList<Process>();
	@ElementArray
	private Trigger[] triggers;
	@ElementArray
	private Process[] processes;
	
	public Persistence(){}
	
	/*public ArrayList<Trigger> getTriggers() {
		return triggers;
	}*/
	public Trigger[] getTriggers(){
		return triggers;
	}

	/*public void setTriggers(int orderNumberTrigger,List<Trigger> triggers) {
		this.triggers = triggers;
	}*/

	/*public ArrayList<Process> getProcesses() {
		return processes;
	}*/
	public Process[] getProcesses(){
		return processes;
	}
	public void setProcess(Process process, int index){
	    this.processes[index] = process; // put the instance in the correct bucket
	}

/*	public void setProcesses(int orderNumberProcess,List<Process> processes) {
		this.processes = processes;
	}*/
	
	//UTIL METHODS
	public int findIdProccessByIdTrigger(int idTrigger){
		int result = -1;
		int i = 0;
		boolean founded = false;
		while(i<triggers.length && !founded){
			if(triggers[i].getIdTrigger() == idTrigger){
				result = triggers[i].getIdProcess();
				founded = true;
			}
			i++;
		}
		return result;
	}
	public Process getProcessByIdProcess(int idProcess){
		int i = 0;
		Process process = null;
		boolean founded = false;
		while(i<processes.length && !founded){
			if(processes[i].getIdProcess() == idProcess){
				process = processes[i];
				founded = true;
			}
			i++;
		}
		return process;
	}
	public Action[] getActionsByIdTrigger(int idTrigger){
		int idProccess = findIdProccessByIdTrigger(idTrigger);
		int i = 0;
		Action[] actions = null;
		boolean founded = false;
		while(i<processes.length && !founded){
			if(processes[i].getIdProcess() == idProccess){
				actions = processes[i].getListActions();
				founded = true;
			}
			i++;
		}
		return actions;
	}
	public boolean isRequest(int idTrigger){
		boolean request = false;
		int i=0;
		while(i<triggers.length && !request){
			if(triggers[i].getIdTrigger() == idTrigger){
				request = true;
			}
			i++;
		}
		return request;
	}
}
