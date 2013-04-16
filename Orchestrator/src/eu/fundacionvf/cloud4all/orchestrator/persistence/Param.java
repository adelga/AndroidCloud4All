package eu.fundacionvf.cloud4all.orchestrator.persistence;

import org.simpleframework.xml.Element;

public class Param {
	@Element
	private String key;
	@Element
	private String value;
	public Param(){}
	public Param(String key,String value){
		this.key = key;
		this.value = value;
	}
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
