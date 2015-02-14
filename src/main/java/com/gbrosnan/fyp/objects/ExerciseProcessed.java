package com.gbrosnan.fyp.objects;

import java.util.Date;
import java.util.List;

public class ExerciseProcessed {
	
	private int id;
	private String username;
	private String exerciseName;
	private Date date;
	private List<SensorSample> rawSensorSamples;
	private List<Double> averages;
	private List<Rep> extractedReps;
	private List<Rep> normalisedReps;
	private String exerciseDetected;
	
	public ExerciseProcessed() {
		
	}
	
	public ExerciseProcessed(String username, String exerciseName, Date date, List<SensorSample> rawSensorSamples) {
		
		setUsername(username);
		setExerciseName(exerciseName);
		setDate(date);
		setRawSensorSamples(rawSensorSamples);
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

