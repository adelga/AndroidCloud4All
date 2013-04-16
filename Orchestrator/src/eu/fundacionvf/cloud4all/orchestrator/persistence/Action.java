package eu.fundacionvf.cloud4all.orchestrator.persistence;

import org.simpleframework.xml.Element;


public class Action {
	@Element(required = false)
	private int idaction;
	@Element
	private int ordernum;
	@Element
	private Request request;
	@Element(required = false)
	public Response response;
    
	public Action(){}
	
	public int getIdAction(){
		return idaction;
	}
	
	public void setIdAction(int idaction){
		this.idaction = idaction;
	}
	
	public int getOrderNumber() {
		return ordernum;
	}

	public void setOrderNumber(int ordernum) {
		this.ordernum = ordernum;
	}

	public Request getRequest(){
		return request;
	}
	
	public Response getResponse(){
		return response;
	}
}
