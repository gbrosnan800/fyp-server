package com.gbrosnan.fyp.preprocess.tests;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import junit.framework.TestCase;
import com.gbrosnan.fyp.preprocess.AdjustArraySize;

//@RunWith(SpringJUnit4ClassRunner.class)
/*@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/rest-servlet.xml" })*/
public class AdjustArraySizeTest extends TestCase {

	List<Double> testList;
	
	@Test
	public void adjustDownFrom220() {
		
		testList = createArray(220);
		testList = AdjustArraySize.adjustDownTo(200, testList);
		
		assertEquals(testList.size(), 200);
	}

	@Test
	public void adjustDownFrom250() {
		
		testList = createArray(250);
		testList = AdjustArraySize.adjustDownTo(200, testList);
		
		assertEquals(testList.size(), 200);
	}
	
	@Test
	public void adjustDownFrom275() {
		
		testList = createArray(275);
		testList = AdjustArraySize.adjustDownTo(200, testList);
		
		assertEquals(testList.size(), 200);
	}
	
	@Test
	public void adjustDownFrom295() {
		
		testList = createArray(295);
		testList = AdjustArraySize.adjustDownTo(200, testList);
		
		assertEquals(testList.size(), 200);
	}
	
	@Test
	public void adjustDownFrom300() {
		
		testList = createArray(300);
		testList = AdjustArraySize.adjustDownTo(200, testList);
		
		assertEquals(testList.size(), 200);
	}
	
	@Test
	public void adjustDownFrom400() {
		
		testList = createArray(500);
		testList = AdjustArraySize.adjustDownTo(200, testList);
		
		assertEquals(testList.size(), 200);
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
