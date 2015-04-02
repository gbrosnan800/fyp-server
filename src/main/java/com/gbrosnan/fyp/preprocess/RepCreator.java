package com.gbrosnan.fyp.preprocess;

import com.gbrosnan.fyp.objects.*;

import java.util.ArrayList;
import java.util.List;

public class RepCreator {

private RepCreator() {}
	
	public static List<Rep> createRepList(List<Double> values, List<Integer> maximas) {
		
		List<Rep> reps = new ArrayList<Rep>();
		int id = 0;
		
		for(int maxima = 0 ; maxima < maximas.size() ; maxima ++) {
			
			if(maxima == 0) {
				reps.add(createRep(values, new int[] {0 , maximas.get(maxima), maximas.get(maxima + 1)}, ++id ));
			}
			else if(maxima == maximas.size() - 1) {
				reps.add(createRep(values, new int[] {maximas.get(maxima - 1) , maximas.get(maxima), values.size() - 1}, ++id ));
			}
			else {
				reps.add(createRep(values, new int[] {maximas.get(maxima - 1) , maximas.get(maxima), maximas.get(maxima + 1)}, ++id ));
			}
		}
		return reps;
	}
	
	public static List<Rep> createRepList3Axis(List<SensorSample> rawData, List<Double> averages, List<Integer> maximas) {
		
		List<Rep> reps = new ArrayList<Rep>();
		
		List<Double> xAxis = new ArrayList<Double>();
		List<Double> yAxis = new ArrayList<Double>();
		List<Double> zAxis = new ArrayList<Double>();
		
		for(SensorSample sample : rawData) {			
			xAxis.add(sample.getX());
			yAxis.add(sample.getY());
			zAxis.add(sample.getZ());
		}
		List<Integer> minimas = getMinimas(averages, maximas);
		
		return reps = create3AxisRepList(rawData, maximas, minimas);
	}
	
	private static List<Rep> create3AxisRepList(List<SensorSample> rawData, List<Integer> maximas, List<Integer> minimas) {
		
		List<Rep> reps = new ArrayList<Rep>();
		Rep rep;
		int start = 0, end = 0;
		
		for(int max = 0 ; max < maximas.size() ; max ++) {
			start = minimas.get(max);
			end = minimas.get(max + 1);
			rep = create3AxisRep(rawData, start, end);	
			reps.add(rep);
		}
		return reps;
	}
	
	private static Rep create3AxisRep(List<SensorSample> rawData, int start, int end) {
		
		List<Double> points = new ArrayList<Double>();
		
		for(int point = start ; point < end ; point ++) {
			points.add(rawData.get(point).getX());
		}
		for(int point = start ; point < end ; point ++) {
			points.add(rawData.get(point).getY());
		}
		for(int point = start ; point < end ; point ++) {
			points.add(rawData.get(point).getZ());
		}
		return new Rep(0, start, 0, end, 0, points);
	}
	
	public static List<Integer> getMinimas(List<Double> averages, List<Integer> maximas) {
		
		List<Integer> minimas = new ArrayList<Integer>();
		int nextMinima;
		
		for(int maxima = 0 ; maxima < maximas.size() ; maxima ++) {			
			if(maxima == 0) {				
				nextMinima = discoverLowestPoint(averages, 0, maximas.get(maxima));
			}
			else {
				nextMinima = discoverLowestPoint(averages, maximas.get(maxima - 1), maximas.get(maxima));
			}
			minimas.add(nextMinima);
		}
		nextMinima = discoverLowestPoint(averages, maximas.get(maximas.size() - 1), averages.size());
		minimas.add(nextMinima);
		return minimas;
		
	}
	
	public static List<Rep> normalizeReps(List<Rep> reps) {
		
		List<Rep> normalizedReps = new ArrayList<Rep>();
		List<Double> samples, normalizedSamples;
		Rep normalizedRep;
		double high, low;
		
		
		for(Rep rep : reps) {
			
			samples = rep.getSamples();		
			normalizedSamples = new ArrayList<Double>();
			double[] highAndLowValues = getHighAndLowValues(samples);
			high = highAndLowValues[0];
			low = highAndLowValues[1];
			for(double sample : samples) {
				double normalized = (sample - low) / (high - low);
				normalizedSamples.add(normalized);
			}		
			normalizedRep = new Rep(rep.getId(), rep.getStartPointIndex(), rep.getStartPointValue(), rep.getEndPointIndex(), rep.getEndPointValue(), normalizedSamples);
			normalizedReps.add(normalizedRep);
		}	
		return normalizedReps;
	}
	
	
	private static Rep createRep(List<Double> values, int[] maximas, int id) {
				
		List<Double> repValues = new ArrayList<Double>();
		int startPoint = discoverLowestPoint(values, maximas[0], maximas[1]);
		int endPoint = discoverLowestPoint(values, maximas[1], maximas[2]);
		
		for(int point = startPoint ; point <= endPoint ; point ++) {
			repValues.add(values.get(point));		
		}
		return new Rep(id, startPoint, values.get(startPoint),endPoint,  values.get(endPoint), repValues);
	}
	
	private static int discoverLowestPoint(List<Double> values, int start, int end) {
		
		int lowestPoint = 0;
		double lowestValue = 1000000;
		for(int point = start ; point < end ; point ++) {
			if(values.get(point) < lowestValue) {
				lowestValue = values.get(point);
				lowestPoint = point;
			}
		}
		return lowestPoint;
	}
	
	private static double[] getHighAndLowValues(List<Double> values) {

		double highest = -10000000;
		double lowest = 10000000;
				
		for(double value : values) {
			if(value > highest){
				highest = value;
			}
			if(value < lowest){
				lowest = value;
			}		
		}		
		return new double[] {highest, lowest};
	}
	
	
	
}
