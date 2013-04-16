package eu.fundacionvf.cloud4all.orchestrator.persistence;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;

public class Process {
	@Element
	private int idprocess;
	//@ElementList
	//ArrayList<Action> actions = new ArrayList<Action>();
	@ElementArray
	private Action[] actions;

	public Process(){}
	
	public int getIdProcess() {
		return idprocess;
	}

	public void setIdProcess(int idprocess) {
		this.idprocess = idprocess;
	}

	public Action[] getListActions(){
		return actions;
	}
	/*public List<Action> getListActions() {
		return actions;
	}

	public void setListActions(Action act) {

		if (actions == null
				|| !(actions.contains(act.getOrderNumber()))) {

			this.actions.add((act.getOrderNumber()) - 1, act);

		}

	}*/
}
