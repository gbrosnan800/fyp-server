package com.gbrosnan.fyp.objects;

public class CSVRequest {
	
	private String collectionName;
	private int id;
	
	public CSVRequest() {}
	
	public CSVRequest(String collectionName, int id) {
		setCollectionName(collectionName);
		setId(id);
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCollectionName() {
		return collectionName;
	}
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	
	
}
