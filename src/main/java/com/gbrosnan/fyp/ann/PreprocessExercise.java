package com.gbrosnan.fyp.ann;

import java.util.ArrayList;
import java.util.List;

import com.gbrosnan.fyp.objects.*;

public class PreprocessExercise {

	private ExerciseProcessed processedExercise;
	private ArrayList<Peak> peaks;
	private ArrayList<Double> maximas;
	private ArrayList<Double> minimas;
	private ArrayList<Rep> extractedReps; 
	private ArrayList<Rep> normalizedReps; 
	
	public PreprocessExercise() {}
	
	public ExerciseProcessed preProcess(ExerciseRaw rawExerciseData) {
		
		processedExercise = new ExerciseProcessed(rawExerciseData.getUsername(), rawExerciseData.getExerciseName(),
				rawExerciseData.getDate(), rawExerciseData.getSensorSampleList());
		
		setAverages(processedExercise.getRawSensorSamples());
		detectPeaks();
		filterPeaks();
		extractReps();
		normalizeReps();
		normalizeRepArraySizeTo200();
		
		processedExercise.setExtractedReps(extractedReps);
		processedExercise.setNormalisedReps(normalizedReps);
		
		return processedExercise;
	}
	
	private void setAverages(List<SensorSample> rawSensorSamples) {
		
		List<Double> averages = new ArrayList<Double>();
		double x, y, z, average;
		for(SensorSample sample : rawSensorSamples) {
			x = sample.getX();
			y = sample.getY();
			z = sample.getZ();
			average = (x + y + z) / 3;
			averages.add(average);	
		}
		processedExercise.setAverages(averages);
	}
	
	
	private void detectPeaks() {
		
		boolean max, min;;
		double currentValue = 0;
		int high = 0, low = 0, avrSize = processedExercise.getAverages().size();
		maximas = new ArrayList<Double>();
		minimas = new ArrayList<Double>();
		peaks = new ArrayList<Peak>();

		for(int point = 0 ; point < avrSize ; point ++) {
			max = true;
			min = true;
			currentValue = processedExercise.getAverages().get(point);
			high = getHigh(point, avrSize, 40);
			low = getLow(point, 40);
			
			checkForMax(point, currentValue, low, high, max);
			checkForMin(point, currentValue, low, high, min);
		}
	}
	
	private int getHigh(int point, int avrSize, int scope) {
		
		int high = point + scope;
		if(high > avrSize) {
			high = avrSize;
		}
		return high;
	}
	
	private int getLow(int point, int scope) {
		
		int low = point - scope;
		if(low < 0) {
			low = 0;
		}
		return low;
	}
	
	private void checkForMax(int point, double currentValue, int low, int high, boolean max) {
		for(int neighbour = low; neighbour < high ; neighbour ++) {
			if(currentValue < processedExercise.getAverages().get(neighbour)) {
				max = false;
			}
			if(!max)break;
		}		
		if(max) {
			maximas.add(currentValue);
			peaks.add(new Peak("maxima", point, currentValue));
		}		
	}
	
	private void checkForMin(int point, double currentValue, int low, int high, boolean min) {
		for(int neighbour = low; neighbour < high ; neighbour ++) {
			if(currentValue > processedExercise.getAverages().get(neighbour)) {
				min = false;
			}
			if(!min)break;
		}		
		if(min) {
			minimas.add(currentValue);
			peaks.add(new Peak("minima", point, currentValue));
		}
	}
	
	public void filterPeaks() {
		
		double averageMinimaValue = getAverageMinimaValue();
		removeOutOfBoundsMinimas(averageMinimaValue);	
	}
	
	private double getAverageMinimaValue() {
		
		double total = 0, average = 0;		
		for(int point = 0 ; point < peaks.size() ; point ++) {
			if(peaks.get(point).getType().equals("minima")) {
				total += peaks.get(point).getValue();
			}
		}
		average = total / minimas.size();	
		return average;
	}
	
	private void removeOutOfBoundsMinimas(double averageMinimaValue) {
		
		for(int peak = 0 ; peak < peaks.size() ; peak ++) {			
			if(peaks.get(peak).getType().equals("minima") && peaks.get(peak).getValue() > averageMinimaValue ) {
				peaks.remove(peak);
			}			
		}
	}
	
	private void extractReps() {

		extractedReps = new ArrayList<Rep>();
		double startPointValue, endPointValue;
		int id = 0, startPointIndex, endPointIndex;
		for(int peak = 0 ; peak < peaks.size() ; peak ++) {	
			if(peaks.get(peak).getType().equals("minima")) {
				id++;
				startPointIndex = peaks.get(peak - 1).getIndex();
				startPointValue = peaks.get(peak - 1).getValue();
				endPointIndex = peaks.get(peak + 1).getIndex();
				endPointValue = peaks.get(peak + 1).getValue();	
				ArrayList<Double> repSamples = populateRep(startPointIndex, endPointIndex);
				extractedReps.add(new Rep(id, startPointIndex, startPointValue, endPointIndex, endPointValue, repSamples));
			}
		}	
	}
	
	private ArrayList<Double> populateRep(int startPointIndex, int endPointIndex) {
		
		ArrayList<Double> repSamples = new ArrayList<Double>();
		for(int point = startPointIndex ; point < (endPointIndex + 1) ; point ++) {
			repSamples.add(processedExercise.getAverages().get(point));
		}
		return repSamples;
	}
	
	private void normalizeReps() {
		
		normalizedReps = new ArrayList<Rep>();
		for(int point = 0; point < extractedReps.size() ; point ++) {
			
			Rep thisRep = extractedReps.get(point);
			ArrayList<Double> samples = thisRep.getSamples();
			ArrayList<Double> normedSamples = new ArrayList<Double>();
			
			double maxVal = getMaxVal(thisRep.getSamples());
			double minVal = getMinVal(thisRep.getSamples());
			
			for(double sample : samples) {
				double normalized = (sample - minVal) / (maxVal - minVal);
				normedSamples.add(normalized);
			}
			
			Rep normalizedRep = new Rep(thisRep.getId(), thisRep.getStartPointIndex(), thisRep.getStartPointValue(), thisRep.getStartPointIndex(),
					thisRep.getEndPointValue(), normedSamples);
			normalizedReps.add(normalizedRep);
		}

	}
	
	private double getMaxVal(ArrayList<Double> samples) {
		
		double max = 0;
		for( double sample : samples) {
			if(sample > max) {
				max = sample;
			}
		}	
		return max;
	}
	
	private  double getMinVal(ArrayList<Double> samples) {
		
		double min = 100000000;
		for( double sample : samples) {
			if(sample < min) {
				min = sample;
			}
		}	
		return min;
	}
	
	private void normalizeRepArraySizeTo200() {
		
		for(int repNum = 0 ; repNum < normalizedReps.size() ; repNum ++) {
			
			ArrayList<Double> samples = normalizedReps.get(repNum).getSamples();
			
			if(samples.size() > 200) {
				samples = adjustDownTo200(samples);
			}
			if(samples.size() < 200) {
				samples = adjustUpTo200(samples);
			}
			
			normalizedReps.get(repNum).setSamples(samples);
		}


	}
	
	private ArrayList<Double> adjustDownTo200(ArrayList<Double> samples) {
		
		int size = samples.size();
		int adjustment = size - 200;
		int frequency = size/adjustment;
	    int frequencyNext = samples.size() - 1;
		
		while(samples.size() > 201) {
			frequencyNext -= frequency;
			if(frequencyNext > 0) {
				samples.remove(frequencyNext);					
			}			
		}
		if(samples.size() > 200) {
			samples.remove(199);
		}
		return samples;
	}
	
	private ArrayList<Double> adjustUpTo200(ArrayList<Double> samples) {
		
		int size = samples.size();
		int adjustment = 200 - size;
		int frequency = 200/adjustment;
		int frequencyNext = 0;
		double value = 0;

		while(samples.size() < 200) {
			frequencyNext += frequency;
			if(frequencyNext >= samples.size()) {
				frequencyNext = samples.size() - 1;
			}	
			value = (samples.get(frequencyNext - 1) + samples.get(frequencyNext)) / 2; 
			samples.add(frequencyNext, value);
		}
		return samples;
	}
	
		
}
;