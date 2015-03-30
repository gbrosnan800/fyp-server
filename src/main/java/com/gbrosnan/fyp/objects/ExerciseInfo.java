package com.gbrosnan.fyp.objects;

public class ExerciseInfo {

	private int id;
	private String exerciseName;
	private int repCount;
	
	public ExerciseInfo(){}
	
	public ExerciseInfo(int id, String exerciseName, int repCount) {
		setId(id);
		setExerciseName(exerciseName);
		setRepCount(repCount);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getExerciseName() {
		return exerciseName;
	}
	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}
	public int getRepCount() {
		return repCount;
	}
	public void setRepCount(int repCount) {
		this.repCount = repCount;
	}
	
	
	
}
