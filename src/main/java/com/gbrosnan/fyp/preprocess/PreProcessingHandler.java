package com.gbrosnan.fyp.preprocess;

import java.util.ArrayList;
import java.util.List;

import com.gbrosnan.fyp.objects.ExerciseRaw;
import com.gbrosnan.fyp.objects.ProcessedExercise;
import com.gbrosnan.fyp.objects.Rep;
import com.gbrosnan.fyp.objects.Routine;
import com.gbrosnan.fyp.objects.SensorSample;

public class PreProcessingHandler {
	
	public PreProcessingHandler () {}
	
	public ProcessedExercise preprocessRawExerciseForANN(ExerciseRaw exerciseRaw) {
		
		List<Double> averages = setAverages(exerciseRaw.getSensorSampleList());
		List<Integer> maximas = PeakDetect.discoverMaximas(averages, 25, 100);
		
		maximas = PeakDetect.filterOutFlatPeaks(averages, maximas, 100, 0.20);
		//maximas = PeakDetect.filterOuterRangePeaks(averages, maximas, 0.30);
		
		List<Rep> reps = RepCreator.createRepList(averages, maximas);
		List<Rep> normalizedReps = RepCreator.normalizeReps(reps);	
		
		for(Rep rep : normalizedReps) {	
			List<Double> adjustedSize = AdjustArraySize.adjustTo(200, rep.getSamples());
			rep.setSamples(adjustedSize);	
		}			
		
		ProcessedExercise processedExercise = new ProcessedExercise(exerciseRaw.getUsername(), exerciseRaw.getExerciseName(), exerciseRaw.getDate(), exerciseRaw.getSensorSampleList(), 
				averages, maximas, reps, normalizedReps, "not_sent_to_ann");

		return processedExercise;
	}
	
	public ProcessedExercise preprocessRawExerciseForANN3Axis(ExerciseRaw exerciseRaw) {
		
		List<Double> averages = setAverages(exerciseRaw.getSensorSampleList());
		List<Integer> maximas = PeakDetect.discoverMaximas(averages, 25, 100);
		
		maximas = PeakDetect.filterOutFlatPeaks(averages, maximas, 100, 0.20);
				
		List<Rep> reps3Axis = RepCreator.createRepList3Axis(exerciseRaw.getSensorSampleList(),averages,maximas);
		List<Rep> reps3AxisNormalized = RepCreator.normalizeReps(reps3Axis);
  		for(Rep rep : reps3AxisNormalized) {	
			List<Double> adjustedSize = AdjustArraySize.adjustTo(600, rep.getSamples());
			rep.setSamples(adjustedSize);	
		}  	
  		
		ProcessedExercise processedExercise = new ProcessedExercise(exerciseRaw.getUsername(), exerciseRaw.getExerciseName(), exerciseRaw.getDate(), exerciseRaw.getSensorSampleList(), 
				averages, maximas, reps3Axis, reps3AxisNormalized, "not_sent_to_ann");
		processedExercise.setWeight(exerciseRaw.getWeight());

		return processedExercise;
	}
	
	public Routine createNewRoutine(ProcessedExercise processedExercise, String collection) {	
		
		int time = getTimeLengthOfLastRep(processedExercise);
		int oneRM = get1RM(processedExercise.getExtractedReps().size(), processedExercise.getWeight());
		int nextWeight = (int) Math.round(oneRM * 0.75);	
		return new Routine(processedExercise.getUsername(), time, oneRM, nextWeight, processedExercise);		
	}
	
	public Routine updateRoutine(Routine currentRoutine, ProcessedExercise processedExercise) {
		
		int nextWeight;
		int oneRM = currentRoutine.getCurrent1RM();
		int oneRMtime = currentRoutine.getTimeOfLastRep();		
		List<Integer> repTimes = getRepTimes(processedExercise);
		int slowerRep = findFirstSlowerRep(repTimes, oneRMtime);
		if(slowerRep == 99) {
			nextWeight = (int) Math.ceil(processedExercise.getWeight() * 1.1);
			oneRM = get1RM(processedExercise.getExtractedReps().size() + 1 , processedExercise.getWeight());
		}
		else {			
			oneRM = get1RM(slowerRep, processedExercise.getWeight());
			nextWeight = (int) Math.round(oneRM * 0.75);			
		}		
		return new Routine(processedExercise.getUsername(), oneRMtime, oneRM, nextWeight, processedExercise);
	}
	
	private List<Integer> getRepTimes(ProcessedExercise exerciseProcessed) {
		
		List<Integer> repTimes = new ArrayList<Integer>();
		List<Integer> maximas = exerciseProcessed.getMaximas();
		int previousTime, currentTime;
		
		for(int maxima = 1 ; maxima < maximas.size() ; maxima ++ ) {
			currentTime = (int) exerciseProcessed.getRawSensorSamples().get(maximas.get(maxima)).getTimestamp();
			previousTime = (int) exerciseProcessed.getRawSensorSamples().get(maximas.get(maxima - 1)).getTimestamp();
			repTimes.add(currentTime - previousTime);
		}
		return repTimes;
	}
	
	private int findFirstSlowerRep(List<Integer> repTimes, int oneRMtime) {
		
		for(int rep = 0 ; rep < repTimes.size() ; rep ++ ) {
			if(repTimes.get(rep) > oneRMtime) {
				return rep + 1;
			}			
		}	
		return 99;
	}
		
	private int getTimeLengthOfLastRep(ProcessedExercise processedExercise) {
		
		int numOfReps = processedExercise.getExtractedReps().size();	
		int maximaLast = processedExercise.getMaximas().get(numOfReps - 1);
		int maximaPenultimate = processedExercise.getMaximas().get(numOfReps - 2);
		int maximaLastTime = (int) processedExercise.getRawSensorSamples().get(maximaLast).getTimestamp();
		int maximaPenultimateTime = (int) processedExercise.getRawSensorSamples().get(maximaPenultimate).getTimestamp();	
		return maximaLastTime - maximaPenultimateTime;
	}
	
	public int get1RM(int numOfReps, double weightLifted) {
			
		double oneRM =  weightLifted *(1.0278 - (0.0278 * numOfReps)); 		
		return (int) Math.round(oneRM);
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
