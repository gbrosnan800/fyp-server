package com.gbrosnan.fyp.preprocess;

import java.util.ArrayList;
import java.util.List;

public class PeakDetect {
	
	private static List<Double> values;
	
	private PeakDetect(){}
		
	public static List<Integer> discoverMaximas(List<Double> valuesProvided, int start, int range) {
		
		values = valuesProvided;
		List<Integer> maximas = new ArrayList<Integer>();
		int startPoint = 0, endPoint = 0, valueListSize = values.size();
		
		for(int currentPoint = start ; currentPoint < valueListSize ; currentPoint ++) {
			startPoint = getStartPoint(currentPoint, range/2);
			endPoint = getEndPoint(currentPoint, range/2, valueListSize);
			if(isMaxima(currentPoint, startPoint, endPoint)) {
				maximas.add(currentPoint);
			}
		}
		return maximas;
	}
	
	public static List<Integer> discoverMinimas(List<Double> valuesProvided, int start, int range) {
		
		values = valuesProvided;
		List<Integer> minimas = new ArrayList<Integer>();
		int startPoint = 0, endPoint = 0, valueListSize = values.size();
		
		for(int currentPoint = start ; currentPoint < valueListSize ; currentPoint ++) {
			startPoint = getStartPoint(currentPoint, range/2);
			endPoint = getEndPoint(currentPoint, range/2, valueListSize);
			if(isMinima(currentPoint, startPoint, endPoint)) {
				minimas.add(currentPoint);
			}
		}
		return minimas;
	}
	
	public static List<Integer> filterOutFlatPeaks(List<Double> valuesProvided, List<Integer> maximas, int range, double percentageDrop) {
		
		List<Integer> filteredList = new ArrayList<Integer>();
		double heightRange = getHeightRangeWithinMiddleThird(valuesProvided);
		double maximaValue, startPointValue, endPointValue, dropRangeValue;
		int startPoint, endPoint;
		
		for(int maxima : maximas) {
			startPoint = getStartPoint(maxima, range);
			endPoint = getEndPoint(maxima, range, valuesProvided.size() - 1);
			startPointValue = valuesProvided.get(startPoint);
			endPointValue = valuesProvided.get(endPoint);
			maximaValue = valuesProvided.get(maxima);
			dropRangeValue = maximaValue - (heightRange * percentageDrop);			
			if(dropRangeValue > startPointValue && dropRangeValue > endPointValue) {
				filteredList.add(maxima);		
			}
		}
		return filteredList;
	}
	
	public static List<Integer> filterOuterRangePeaks(List<Double> valuesProvided, List<Integer> maximas,  double percentageRange) {
		
		List<Integer> filteredList = new ArrayList<Integer>();
		double heightRange = getHeightRangeWithinMiddleThird(valuesProvided);		
		int[] midRange = getMidThirdRange(valuesProvided.size());		
		double heighestMaximaInRange = -10000000;
		
		for(int maxima : maximas) {
			double value = valuesProvided.get(maxima);		
			if( maxima > midRange[0] && maxima < midRange[1] &&  value > heighestMaximaInRange) {
				heighestMaximaInRange = valuesProvided.get(maxima);
			}
		}
		double maxHeight = heighestMaximaInRange + (heightRange * percentageRange);
		double minHeight = heighestMaximaInRange - (heightRange * percentageRange);
		
		for(int maxima : maximas) {
			if(valuesProvided.get(maxima) > minHeight && valuesProvided.get(maxima) < maxHeight) {
				filteredList.add(maxima);
			}
		}
		return filteredList;
	}
	
	private static int getStartPoint(int point, int halfScope) {
		
		int start = point - halfScope;
		if(start < 0) {
			start = 0;
		}
		return start;
	}

	private static int getEndPoint(int point, int halfScope, int valueListSize) {
		
		int end = point + halfScope;
		if(end > valueListSize) {
			end = valueListSize;
		}
		return end;
	}
	
	private static boolean isMaxima(int currentPoint, int startPoint, int endPoint) {
		
		boolean maxima = true;
		for(int neighbour = startPoint; neighbour < endPoint ; neighbour ++) {
			if(values.get(currentPoint) < values.get(neighbour)) {
				maxima = false;
				break;
			}
		}		
		return maxima;
	}
	
	private static boolean isMinima(int currentPoint, int startPoint, int endPoint) {
		
		boolean maxima = true;
		for(int neighbour = startPoint; neighbour < endPoint ; neighbour ++) {
			if(values.get(currentPoint) > values.get(neighbour)) {
				maxima = false;
				break;
			}
		}		
		return maxima;
	}
	
	private static double getHeightRangeWithinMiddleThird(List<Double> valuesProvided) {

		double highest = -10000000;
		double lowest = 10000000;
		int[] midThirdRange = getMidThirdRange(valuesProvided.size());
				
		for(int point = midThirdRange[0] ; point < midThirdRange[1] ; point ++) {
			if(valuesProvided.get(point) > highest){
				highest = valuesProvided.get(point);
			}
			if(valuesProvided.get(point) < lowest){
				lowest = valuesProvided.get(point);
			}		
		}
		return highest - lowest;
	}
	
	private static int[] getMidThirdRange(int listSize) {
		
		int start = listSize / 3;
		int finish = (listSize / 3) * 2;
		return new int[] {start, finish};
	}

}
