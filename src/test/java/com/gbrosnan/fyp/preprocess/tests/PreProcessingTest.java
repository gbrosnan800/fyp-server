package com.gbrosnan.fyp.preprocess.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gbrosnan.fyp.preprocess.PreProcessingHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/rest-servlet.xml" })
public class PreProcessingTest {

	PreProcessingHandler handler = new PreProcessingHandler();
	
	List<Double> testList;
	
	@Test
	public void get1RM() {
		
		int numOfReps = 6; 
		double weightLifted = 14;
		
		int oneRM = handler.get1RM(numOfReps, weightLifted);
		
		assertEquals(16, oneRM);
		
	}
	
}
