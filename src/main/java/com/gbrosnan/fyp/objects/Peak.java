package com.gbrosnan.fyp.objects;

public class Peak {
	
	private String type;
	private int index;
	private double value;
	
	public Peak(String type, int index, double value) {
		setType(type);
		setIndex(index);
		setValue(value);
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
