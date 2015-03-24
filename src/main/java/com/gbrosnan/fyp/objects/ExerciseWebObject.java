package com.gbrosnan.fyp.objects;

import java.util.List;

public class ExerciseWebObject {
	
	private List<Double> averages;
	private List<Integer> maximas;
	private List<Integer> minimas;
	private int start;
	private double height;
	private int range;
	
	
	public List<Double> getAverages() {
		return averages;
	}
	public void setAverages(List<Double> averages) {
		this.averages = averages;
	}
	public List<Integer> getMaximas() {
		return maximas;
	}
	public void setMaximas(List<Integer> maximas) {
		this.maximas = maximas;
	}
	public List<Integer> getMinimas() {
		return minimas;
	}
	public void setMinimas(List<Integer> minimas) {
		this.minimas = minimas;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}

}
