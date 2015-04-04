package com.gbrosnan.fyp.objects;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Routine {
	
	@Id
	private int id;
	private String username;
	private String collection;
	private int timeOfLastRep;
	private int current1RM;
	private int nextWeight;
	private ProcessedExercise processedExercise;
	
	public Routine() {}
	
	public Routine(String username, int timeOfLastRep, int current1RM, int nextWeight, ProcessedExercise processedExercise) {
		setUsername(username);
		setTimeOfLastRep(timeOfLastRep);
		setCurrent1RM(current1RM);
		setNextWeight(nextWeight);
		setProcessedExercise(processedExercise);
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

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public int getTimeOfLastRep() {
		return timeOfLastRep;
	}

	public void setTimeOfLastRep(int timeOfLastRep) {
		this.timeOfLastRep = timeOfLastRep;
	}

	public int getCurrent1RM() {
		return current1RM;
	}

	public void setCurrent1RM(int current1rm) {
		current1RM = current1rm;
	}

	public int getNextWeight() {
		return nextWeight;
	}

	public void setNextWeight(int nextWeight) {
		this.nextWeight = nextWeight;
	}

	public ProcessedExercise getProcessedExercise() {
		return processedExercise;
	}

	public void setProcessedExercise(ProcessedExercise processedExercise) {
		this.processedExercise = processedExercise;
	}
	
	public String toJsonString(String differenceFromPrevious1RM) {
		return "{\"status\":\"ok\",\"rm\":\"" + getCurrent1RM() + "\",\"difference\":\"" + differenceFromPrevious1RM  + "\",\"next_weight\":\"" + getNextWeight() + "\"}";
	}
	
	

}
