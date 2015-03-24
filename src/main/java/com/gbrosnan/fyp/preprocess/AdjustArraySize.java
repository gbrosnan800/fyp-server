package com.gbrosnan.fyp.preprocess;

import java.util.ArrayList;
import java.util.List;

public final class AdjustArraySize {

	private AdjustArraySize() {}
	
	public static List<Double> adjustTo(int size, List<Double> originalList) {
		
		List<Double> adjustedList;
		System.out.println("oringal size === " + originalList.size());
		if(originalList.size() > size) {
			adjustedList = adjustDownTo(size, originalList);
		}
		else if(originalList.size() < size) {
			adjustedList = adjustUpwardsTo(size, originalList);
		}
		else {
			return originalList;
		}
		System.out.println("sizeadjust size = " + adjustedList.size());
		return adjustedList;
	}
	
	public static List<Double> adjustDownTo(int size, List<Double> originalList) {
		
		int originalSize = originalList.size();
		int adjustment = originalSize - size;
		int frequency = originalSize / adjustment;
	    int frequencyNext = originalList.size() - 1;
		
		while(originalList.size() > (size + 1)) {
			frequencyNext -= frequency;
			if(frequencyNext > 0) {
				originalList.remove(frequencyNext);					
			}			
		}
		if(originalList.size() > size) {
			originalList.remove(size - 1);
		}
		return originalList;
	}
	
	public static List<Double> adjustUpwardsTo(int size, List<Double> originalList) {
		
		
		List<Double> adjustedList = new ArrayList<Double>();
		int originalSize = originalList.size();
		int adjustment = size - originalSize;
		int frequency = originalSize/adjustment;
		if(frequency == 0) {
			return originalList;
		}
		int frequencyNext = 0;
		double value = 0;
		if(originalSize == 59) { 
			System.out.println("upwards");
			System.out.println("adjustment == " + adjustment + "  freq= " + frequency);
		}
		
		
		while(originalList.size() < size) {
			frequencyNext += frequency;
			if(frequencyNext >= originalList.size()) {
				frequencyNext = originalList.size() - 1;
			}	
			value = (originalList.get(frequencyNext - 1) + originalList.get(frequencyNext)) / 2; 
			originalList.add(frequencyNext, value);
		}
		return originalList;
	}
	
	
}
