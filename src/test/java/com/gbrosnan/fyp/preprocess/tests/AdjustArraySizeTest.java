package com.gbrosnan.fyp.preprocess.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import junit.framework.TestCase;

import com.gbrosnan.fyp.preprocess.AdjustArraySize;

public class AdjustArraySizeTest extends TestCase {

	
	
	@Test
	public void adjustDownTo() {
		
		List<Double> testList = createArray(220);
		testList = AdjustArraySize.adjustDownTo(200, testList);
		assertEquals(testList, 200);

		
	}
	
	
	private List<Double> createArray(int size) {
		
		size--;
		List<Double> list = new ArrayList<Double>();
		for(double point = 0 ; point < size ; point ++) {
			list.add(point);
		}
		return list;		
	}
	
}
