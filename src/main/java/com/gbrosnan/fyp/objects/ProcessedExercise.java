package com.gbrosnan.fyp.objects;

import java.util.Date;
import java.util.List;

public class ProcessedExercise {
	
	private int id;
	private String username;
	private String exerciseName;
	private Date date;
	private double weight;
	private List<SensorSample> rawSensorSamples;
	private List<Double> averages;
	private List<Integer> maximas;
	private List<Rep> extractedReps;
	private List<Rep> normalisedReps;
	private String exerciseDetected;
	
	public ProcessedExercise() {
		
	}
	
	public ProcessedExercise(String username, String exerciseName, Date date, List<SensorSample> rawSensorSamples) {
		
		setUsername(username);
		setExerciseName(exerciseName);
		setDate(date);
		setRawSensorSamples(rawSensorSamples);
	}	
	
	public ProcessedExercise(String username, String exerciseName, Date date, List<SensorSample> rawSensorSamples, List<Double> averages, List<Integer> maximas,
			List<Rep> extractedReps, List<Rep> normalisedReps, String exerciseDetected) {
		
		setUsername(username);
		setExerciseName(exerciseName);
		setDate(date);
		setRawSensorSamples(rawSensorSamples);
		setMaximas(maximas);
		setAverages(averages);
		setExtractedReps(extractedReps);
		setNormalisedReps(normalisedReps);
		setExerciseDetected(exerciseDetected);
	}	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getExerciseName() {
		return exerciseName;
	}

	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public List<SensorSample> getRawSensorSamples() {
		return rawSensorSamples;
	}

	public void setRawSensorSamples(List<SensorSample> rawSensorSamples) {
		this.rawSensorSamples = rawSensorSamples;
	}

	public List<Double> getAverages() {
		return averages;
	}

	public void setAverages(List<Double> averages) {
		this.averages = averages;
	}

	public List<Integer> getMaximas() {
		return maximas;
	}

	public void setMaximas(List<Integer> maximas) {
		this.maximas = maximas;
	}

	public List<Rep> getExtractedReps() {
		return extractedReps;
	}

	public void setExtractedReps(List<Rep> extractedReps) {
		this.extractedReps = extractedReps;
	}

	public List<Rep> getNormalisedReps() {
		return normalisedReps;
	}

	public void setNormalisedReps(List<Rep> normalisedReps) {
		this.normalisedReps = normalisedReps;
	}

	public String getExerciseDetected() {
		return exerciseDetected;
	}

	public void setExerciseDetected(String exerciseDetected) {
		this.exerciseDetected = exerciseDetected;
	}
	
	
	
}

