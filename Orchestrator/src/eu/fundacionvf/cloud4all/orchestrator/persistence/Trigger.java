package eu.fundacionvf.cloud4all.orchestrator.persistence;

import org.simpleframework.xml.Element;

public class Trigger {
	@Element
	private int idtrigger;
	//@ElementList
	//ArrayList<Integer> process = new ArrayList<Integer>();
    @Element
    private int process;
	public int getIdTrigger() {
		return idtrigger;
	}

	public void setIdTrigger(int idtrigger) {
		this.idtrigger = idtrigger;
	}

/*	public List<Integer> getListProcess() {
		return process;
	}

	public void setListProcess(int idprocess) {

		if (!process.contains(idprocess)) {
			this.process.add(idprocess);
		}

	}*/
	public int getIdProcess(){
		return process;
	}
}
