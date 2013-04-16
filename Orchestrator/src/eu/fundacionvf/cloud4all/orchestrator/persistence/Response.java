package eu.fundacionvf.cloud4all.orchestrator.persistence;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;

public class Response {
	@Element
	private int idevent;
	@Element
	private String idmodule;
	@ElementArray
	//private ArrayList<Params> params = new ArrayList<Params>();
	public Param[] params;
   // @ElementArray
   // private Params[] params;
	public Response(){}
	public Response(int idaction,int idevent, String idmodule){
		this.idevent = idevent;
		this.idmodule = idmodule;
		params = new Param[1];
		params[0] = new Param("undefined", "undefined");
	}
	
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
