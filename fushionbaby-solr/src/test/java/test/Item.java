package test;

import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

public class Item {
	@Field
	String id;
	@Field
	List<String> title;
	@Field
	List<String> IKType;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getTitle() {
		return title;
	}
	public void setTitle(List<String> title) {
		this.title = title;
	}
	public List<String> getIKType() {
		return IKType;
	}
	public void setIKType(List<String> iKType) {
		IKType = iKType;
	}
	
	
}
