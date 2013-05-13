package eu.fundacionvf.cloud4all.orchestrator.matching_variables;


import java.util.StringTokenizer;

import android.util.Log;

import eu.fundacionvf.cloud4all.orchestrator.persistence.Session;
import eu.fundacionvf.cloud4all.orchestrator.persistence.Action;
import eu.fundacionvf.cloud4all.orchestrator.persistence.Request;
import eu.fundacionvf.cloud4all.orchestrator.persistence.Response;
import eu.fundacionvf.cloud4all.orchestrator.persistence.Param;
import eu.fundacionvf.cloud4all.orchestrator.persistence.TriggerEvent;
import eu.fundacionvf.cloud4all.orchestrator.persistence.TriggerEventParam;




public class Matching {
	
	int idProcess;
	Action action;
	Request request;
	Response response;
	Param [] params;
	String valueFromKey;
	
	
	String modeEvent;
	String typeEvent;
	String valueParams;
	String typeEventValue;
	String valueModeE;
	
	
	
	private static final String TAG = "CLOUD4ALL";
	
	public Matching(){
				
	}

	
	public String getValue(int idTrigger,int idAction,String value,Session session){
		  
		  Log.d("CLOUD4ALL","Estamos dentro value="+value);
		  		  
		  String delimiter = "].[";
		  
		  
		  if (value.contains(delimiter)) {
			  
			  Log.d("CLOUD4ALL", "parameters delimiter detected");
		  
			  int i=0;		  		  
			  StringTokenizer stEvent= new StringTokenizer(value, delimiter);
			  String parametersEvent[] = new String[stEvent.countTokens()];

				while (stEvent.hasMoreTokens()) {

					parametersEvent[i] = stEvent.nextToken();
					Log.d("CLOUD4ALL parameter number "+ i+ ":", " value "+ parametersEvent[i]);
					i++;
				}
		  
			  modeEvent = parametersEvent[0];
			  typeEvent = parametersEvent[1];
			  valueParams = parametersEvent[2]; 
			
			  i=0;
			  delimiter="=";
			  StringTokenizer stmodeEvent = new StringTokenizer(modeEvent, "=");
			  String parametersmodeEvent[] = new String[stmodeEvent.countTokens()];

				while (stmodeEvent.hasMoreTokens()) {
		
					parametersmodeEvent[i] = stmodeEvent.nextToken();
					Log.d("CLOUD4ALL parameter number "+ i+ ":", " value "+ parametersmodeEvent[i]);
					i++;
				}
			  
		      if(i==2){
		  
		    	  typeEventValue = parametersmodeEvent[0];
		    	  valueModeE = parametersmodeEvent[1];
		    	  Log.d("valor Modo Evento", valueModeE);
		      	  }else{
		      		  typeEventValue = parametersmodeEvent[0];
		      }
			  
			  Log.d("modo", modeEvent);
			  Log.d("tipo", typeEvent);
			  Log.d("valor", valueParams);
			  
			  
			  
			  idProcess = session.getIdProcessByIdAction(idAction);
			  		  
			  if (typeEventValue.equals("orderAction")) {
				  
				  action = session.getActionByOrderAction(Integer.parseInt(valueModeE), idProcess);
				  Log.d("CLOUD4ALL,: idprocess= "+idProcess+" La accion "+ action.getIdAction()+ ":", "es  " + valueFromKey);
					
					if(typeEvent.equals("request")){
						int z=0;
						request = action.getRequest();
						params = request.getParams();
						boolean founded = false;
						while (z < params.length && !founded){
										
							if (params [z].getKey().equals(valueParams)){
									
								valueFromKey = params[z].getValue();
								founded = true;
								
							}
							z++;
						}
				
						Log.d("CLOUD4ALL La palabra "+ i+ ":", "es  " + valueFromKey);
						return valueFromKey;
						
					} else if (typeEvent.equals("response")){
						int a=0;
						response = action.getResponse();
						params = response.getParams();
						boolean founded = false;
						while (a < params.length && !founded){
							 Log.d("parametro "+a, params [a].getKey());
							if (params [a].getKey().equals(valueParams)){
								Log.d("Identificado con parametro "+a, params [a].getKey());
								Log.d("Identificado con parametro "+a, params [a].getValue());
								valueFromKey = params[a].getValue();
								founded = true;
								
							}
							a++;
						}
				
						return valueFromKey;
						
					}
		  
			  }else if(typeEventValue.equals("idtrigger")){
				  
			  //Always typeEvent is request
					
					//session.getProcessByIdProcess(idProcess).getTriggerEvent().getIdTriggerEvent();
				  Log.i(TAG, "idProcess en matching:"+idProcess);
				  	int length=session.getProcessByIdAction(idAction).getTriggerEvent().getTriggerEventParam().length;
				  	int z=0;
				  	boolean founded = false;
					while (z < length && !founded){
						
						if (session.getProcessByIdAction(idAction).getTriggerEvent().getTriggerEventParam()[z].getKey().equals(valueParams)){
							
							valueFromKey = session.getProcessByIdAction(idAction).getTriggerEvent().getTriggerEventParam()[z].getValue();
							founded = true;
							
						}
						z++;
					}
		  	
					return valueFromKey;
	
			  }
		  }
		  	  
		  return value;
			
	}
}
