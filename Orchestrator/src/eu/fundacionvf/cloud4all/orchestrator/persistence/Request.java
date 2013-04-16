package eu.fundacionvf.cloud4all.orchestrator.persistence;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;

public class Request {

	@Element
	private int idevent;
	@Element
	private String idmodule;
	@ElementArray
	//private ArrayList<Params> params = new ArrayList<Params>();
	private Param[] params;
   // @ElementArray
   // private Params[] params;
	public Request(){}
	
	public int getIdEvent() {
		return idevent;
	}

	public void setIdEvent(int idevent) {
		this.idevent = idevent;
	}

	public String getIdModule() {
		return idmodule;
	}

	public void setIdModule(String idmodule) {
		this.idmodule = idmodule;
	}

	public Param[] getParams() {
		return params;
	}
    
/*	public void setParams(int orderNumberParams, Params prms) {

		if (!params.contains(prms)) {
			this.params.add(orderNumberParams, prms);
		}
	}*/
}
