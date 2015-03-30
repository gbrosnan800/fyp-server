package com.gbrosnan.fyp.objects;

import java.util.List;

public class CSVRequestList {

	private int maximaStart;
	private int maximaRange;
	private double flatPeakHeight;
	private int flatPeakRange;
	private double outerRangeHeight;
	private List<CSVRequest> csvRequestList;
	private String fileName;
	
	public CSVRequestList() {}
	
	public CSVRequestList(int maximaStart, int maximaRange, double flatPeakHeight, int flatPeakRange, double outerRangeHeight, List<CSVRequest> csvRequestList, String fileName) {
		setMaximaStart(maximaStart);
		setMaximaRange(maximaRange);
		setFlatPeakHeight(flatPeakHeight);
		setFlatPeakRange(flatPeakRange);
		setOuterRangeHeight(outerRangeHeight);
		setCsvRequestList(csvRequestList);
		setFileName(fileName);
	}

	public int getMaximaStart() {
		return maximaStart;
	}

	public void setMaximaStart(int maximaStart) {
		this.maximaStart = maximaStart;
	}

	public int getMaximaRange() {
		return maximaRange;
	}

	public void setMaximaRange(int maximaRange) {
		this.maximaRange = maximaRange;
	}

	public double getFlatPeakHeight() {
		return flatPeakHeight;
	}

	public void setFlatPeakHeight(double flatPeakHeight) {
		this.flatPeakHeight = flatPeakHeight;
	}

	public int getFlatPeakRange() {
		return flatPeakRange;
	}

	public void setFlatPeakRange(int flatPeakRange) {
		this.flatPeakRange = flatPeakRange;
	}

	public double getOuterRangeHeight() {
		return outerRangeHeight;
	}

	public void setOuterRangeHeight(double outerRateHeight) {
		this.outerRangeHeight = outerRateHeight;
	}

	public List<CSVRequest> getCsvRequestList() {
		return csvRequestList;
	}

	public void setCsvRequestList(List<CSVRequest> csvRequestList) {
		this.csvRequestList = csvRequestList;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	
}
