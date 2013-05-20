/*
Action class
This class is part of the Persistence Object	

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

import org.simpleframework.xml.Element;

/**
 *This class is part of the Persistence Object	
 *@author Vodafone Spain Foundation
 *@version 1.0
 */
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
	
	/**
	 * getIdAction method
	 * @return Return idaction from Action object
	 */
	public int getIdAction(){
		return idaction;
	}
	/**setIdAction method
	 * @param idaction set idaction in Action object
	 */
	public void setIdAction(int idaction){
		this.idaction = idaction;
	}
	
	/**getOrderNumber method
	 * @return Return ordernum from Action object
	 */
	public int getOrderNumber() {
		return ordernum;
	}
    /**setOrderNumber method
     * @param ordernum set ordernum in Action object
     */
	public void setOrderNumber(int ordernum) {
		this.ordernum = ordernum;
	}
    /**
     * getRequest method
     * @return Return request from Action object
     */
	public Request getRequest(){
		return request;
	}
	/**getResponse method
	 * @return Return response from Action object
	 */
	public Response getResponse(){
		return response;
	}
}
