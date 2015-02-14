package com.gbrosnan.fyp.objects;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ExerciseRaw {
	
	@Id
	private int id;
	private String username;
	private String exerciseName;
	private Date date;
	private List<SensorSample> sensorSampleList;
	
	public ExerciseRaw(int id, String username, String exerciseName, Date date, List<SensorSample> sensorSampleList){
		setId(id);
		setUsername(username);
		setExerciseName(exerciseName);
		setDate(date);
		setSensorSampleList(sensorSampleList);
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
	public List<SensorSample> getSensorSampleList() {
		return sensorSampleList;
	}
	public void setSensorSampleList(List<SensorSample> sensorSampleList) {
		this.sensorSampleList = sensorSampleList;
	}
}
