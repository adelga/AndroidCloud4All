package eu.fundacionvf.cloud4all.orchestrator.persistence;

import org.json.JSONException;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

import eu.fundacionvf.cloud4all.orchestrator.util.CloudIntent;

@Root
public class Session {
		@ElementArray
		private Process[] processes;
		public Session(){}
		public void newSession(){
			processes = new Process[1];
		}
		
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
		public int getIdProcessByIdAction(int idAction){
			int i = 0;
			int j = 0;
			int  idProcess = 0;
			boolean founded = false;
			while(i<processes.length && !founded){
				while(j<processes[i].getListActions().length && !founded){
					if(processes[i].getListActions()[j].getIdAction() == idAction){
						idProcess = processes[i].getIdProcess();
						founded = true;
					}
					j++;
				}
				i++;
			}
		return idProcess;
		}
		public int indexOfProcess(int idProcess){
			int i = 0;
			boolean founded = false;
			while(i<processes.length && !founded){
				if(processes[i].getIdProcess() == idProcess){
					founded = true;
				}
				i++;
			}
			return i-1;
		}
		public boolean nextActionExist(int idAction,int idProcess){
			boolean nextAction = false;
			int i = 0;
			Process process = getProcessByIdProcess(idProcess);
			Action[] actions = process.getListActions();
			while(i<actions.length && !nextAction){
				if(actions[i].getIdAction() == idAction){
					if((i+1)<actions.length){
						nextAction = true;
					}
				}
			}
		return nextAction;
		}
		public int indexOfAction(int idAction,int idProcess){
			int i = 0;
			Process process = getProcessByIdProcess(idProcess);
			Action[] actions = process.getListActions();
			boolean founded = false;
			while(i<actions.length && !founded){
				if(actions[i].getIdAction() == idAction){
					founded = true;
				}
				i++;
			}
			return i-1;
		}
		public Action[] getActionsByIdProcess(int idProcess){
			int i = 0;
			Process process = getProcessByIdProcess(idProcess);
			Action[] actions = process.getListActions();
			return actions;
		}
		public Action getActionByIdAction(int idAction,int idProcess){
			int i = 0;
			Process process = getProcessByIdProcess(idProcess);
			Action[] actions = process.getListActions();
			Action action = null;
			boolean founded = false;
			while(i<actions.length && !founded){
				if(actions[i].getIdAction() == idAction){
					action = actions[i];
					founded = true;
				}
				i++;
			}
			return action;
		}
		public void addParameters(CloudIntent cloudinfo){
			try {
				boolean action = false;
				int idAction = cloudinfo.getIdAction();
				String[] listaIds = cloudinfo.getArrayIds();
				int i = 0;
				int j = 0;
				//Find action where idaction is in the structure
				while(i<processes.length && !action){
					while(j<processes[i].getListActions().length && !action){
					if(processes[i].getListActions()[j].getIdAction() == idAction){
						action = true;
					}
					j++;
					}
					i++;
				}
				if(processes[i-1].getListActions()[j-1].getResponse().getParams().length != listaIds.length){
					processes[i-1].getListActions()[j-1].getResponse().params = new Param[listaIds.length];
				}
				//Fill the params received
				for(int k = 0;k<processes[i-1].getListActions()[j-1].getResponse().getParams().length;k++){
					processes[i-1].getListActions()[j-1].getResponse().getParams()[k].setKey(listaIds[k]);
					processes[i-1].getListActions()[j-1].getResponse().getParams()[k].setValue(cloudinfo.getValue(listaIds[k]));
				}
				//processes[i-1].getListActions()[j-1].setIdAction(idActionReq);
				processes[i-1].getListActions()[j-1].getResponse().setIdEvent(cloudinfo.getIdEvent());
				String strAction = processes[i-1].getListActions()[j-1].getRequest().getIdModule();
				processes[i-1].getListActions()[j-1].getResponse().setIdModule(strAction);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public boolean haveProcesses(){
			if(processes == null){
				return false;
			}else{
				return true;
			}
			
		}
		public boolean processExist(int idProcess){
			boolean process = false;
			int i=0;
			while(i<processes.length && !process){
				if(processes[i].getIdProcess() == idProcess){
					process = true;
				}
				i++;
			}
			return process;
		}
		public void addProcess(Process process){
			//Add Response to every action in the process
			for(int i=0;i<process.getListActions().length;i++){
				process.getListActions()[i].response = new Response(0,0,"0");
			}
			int lon = processes.length+1;
			Process[] auxProcesses = new Process[lon];
			System.arraycopy(processes, 0, auxProcesses, 0, lon-1);
			auxProcesses[lon-1] = process;
			processes = new Process[lon];
			System.arraycopy(auxProcesses, 0, processes, 0, lon);
		}
}
