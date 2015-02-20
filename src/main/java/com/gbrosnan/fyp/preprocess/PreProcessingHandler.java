package com.gbrosnan.fyp.preprocess;

import java.util.ArrayList;
import java.util.List;

import com.gbrosnan.fyp.objects.ExerciseRaw;
import com.gbrosnan.fyp.objects.ProcessedExercise;
import com.gbrosnan.fyp.objects.Rep;
import com.gbrosnan.fyp.objects.SensorSample;

public class PreProcessingHandler {
	
	public PreProcessingHandler () {}
	
	public ProcessedExercise preprocessRawExerciseForANN(ExerciseRaw exerciseRaw) {
		
		List<Double> averages = setAverages(exerciseRaw.getSensorSampleList());
		System.out.println("averages size = " + averages.size());
		List<Integer> maximas = PeakDetect.discoverMaximas(averages, 25, 100);
		System.out.println("maximas1 size = " + maximas.size());
		maximas = PeakDetect.filterOutFlatPeaks(averages, maximas, 50, 0.25);
		System.out.println("maximas2 size = " + maximas.size());
		maximas = PeakDetect.filterOuterRangePeaks(averages, maximas, 0.25);
		System.out.println("maximas3 size = " + maximas.size());
		List<Rep> reps = RepCreator.createRepList(averages, maximas);
		System.out.println("reps size = " + reps.size());
		
		List<Rep> normalizedReps = RepCreator.normalizeReps(reps);
		System.out.println("holaaa");
		System.out.println("n size = " + normalizedReps.size());
		System.out.println("norm = " + normalizedReps.get(5).getSamples().size());
		
		
		
		ProcessedExercise processedExercise = new ProcessedExercise(exerciseRaw.getUsername(), exerciseRaw.getExerciseName(), exerciseRaw.getDate(), exerciseRaw.getSensorSampleList(), 
				averages, reps, normalizedReps, "not_sent_to_ann");

		return processedExercise;
	}
	

	
	private List<Double> setAverages(List<SensorSample> rawSensorSamples) {
		
		List<Double> averages = new ArrayList<Double>();
		double x, y, z, average;
		for(SensorSample sample : rawSensorSamples) {
			x = sample.getX();
			y = sample.getY();
			z = sample.getZ();
			average = (x + y + z) / 3;
			averages.add(average);	
		}
		return averages;
	}

}
