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
		List<Integer> maximas = PeakDetect.discoverMaximas(averages, 25, 100);
		
		maximas = PeakDetect.filterOutFlatPeaks(averages, maximas, 100, 0.20);
		maximas = PeakDetect.filterOuterRangePeaks(averages, maximas, 0.30);
		
		List<Rep> reps = RepCreator.createRepList(averages, maximas);
		List<Rep> normalizedReps = RepCreator.normalizeReps(reps);	
		
		for(Rep rep : normalizedReps) {	
			List<Double> adjustedSize = AdjustArraySize.adjustTo(200, rep.getSamples());
			rep.setSamples(adjustedSize);	
		}			
		
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
