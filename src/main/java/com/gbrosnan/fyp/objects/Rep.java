package com.gbrosnan.fyp.objects;
import java.util.List;

public class Rep {

	private int id;
	private int startPointIndex;
	private double startPointValue;
	private int endPointIndex;
	private double endPointValue;
	private List<Double> samples;
		
	public Rep (int id, int startPointIndex, double startPointValue, int endPointIndex, double endPointValue, List<Double> samples) {
		setId(id);
		setStartPointIndex(startPointIndex);
		setStartPointValue(startPointValue);
		setEndPointIndex(endPointIndex);
		setEndPointValue(endPointValue);
		setSamples(samples);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStartPointIndex() {
		return startPointIndex;
	}

	public void setStartPointIndex(int startPointIndex) {
		this.startPointIndex = startPointIndex;
	}

	public double getStartPointValue() {
		return startPointValue;
	}

	public void setStartPointValue(double startPointValue) {
		this.startPointValue = startPointValue;
	}

	public int getEndPointIndex() {
		return endPointIndex;
	}

	public void setEndPointIndex(int endPointIndex) {
		this.endPointIndex = endPointIndex;
	}

	public double getEndPointValue() {
		return endPointValue;
	}

	public void setEndPointValue(double endPointValue) {
		this.endPointValue = endPointValue;
	}

	public List<Double> getSamples() {
		return samples;
	}

	public void setSamples(List<Double> samples) {
		this.samples = samples;
	}
	
}

