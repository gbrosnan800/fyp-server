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
		
		ProcessedExercise processedExercise = new ProcessedExercise();
		List<Double> averages = setAverages(exerciseRaw.getSensorSampleList());
		
		List<Integer> maximas = PeakDetect.discoverMaximas(averages, 25, 100);
		maximas = PeakDetect.filterOutFlatPeaks(processedExercise.getAverages(), maximas, 50, 0.25);
		maximas = PeakDetect.filterOuterRangePeaks(processedExercise.getAverages(), maximas, 0.25);	
		List<Rep> reps = RepCreator.createRepList(processedExercise.getAverages(), maximas);
		List<Rep> normalizedReps = RepCreator.normalizeReps(reps);
		
		processedExercise.setAverages(averages);
		processedExercise.setExtractedReps(reps);
		processedExercise.setNormalisedReps(normalizedReps);
		processedExercise.setExerciseDetected("not_sent_to_ann");
		
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
