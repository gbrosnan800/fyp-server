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
				reps.add(createRep(values, new int[] {0 , maxima, maxima + 1}, id++ ));
			}
			else if(maxima == maximas.size() - 1) {
				reps.add(createRep(values, new int[] {maxima -1 , maxima, maximas.size()}, id++ ));
			}
			else {
				reps.add(createRep(values, new int[] {maxima -1 , maxima, maxima + 1}, id++ ));
			}
		}
		return reps;
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
		
		int lowestPoint = 10000000;
		for(int point = start ; point < values.size() ; point ++) {
			if(values.get(point) < lowestPoint) {
				lowestPoint = point;
			}
		}
		return lowestPoint;
	}
	
}
