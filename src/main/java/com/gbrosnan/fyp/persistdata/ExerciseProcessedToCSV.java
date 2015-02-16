package com.gbrosnan.fyp.persistdata;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.gbrosnan.fyp.objects.Rep;

public class ExerciseProcessedToCSV {

	public void createCSVFile(List<Rep> normalizedReps, int id, String exerciseName) {
	
		try {	
			String filePath = getFilePath(id, exerciseName);
			FileWriter writer = new FileWriter(filePath);	
			
			int max = getMaxSize(normalizedReps);
			int amountOfReps = normalizedReps.size();
			String value = "";
			
			for(int i = 0 ; i < max ; i ++) {
				for(int j = 0 ; j < amountOfReps ; j++) {				
					if(normalizedReps.get(j).getSamples().size() > i) {
						value = normalizedReps.get(j).getSamples().get(i).toString();
					}
					else {
						value = "";
					}
					writer.append(value + ",");
				}
				writer.append("\n");
			}
			
			writer.flush();
			writer.close();
		}
		catch(Exception ex) {
			System.out.println(ex.toString());
		}
	}
	
	private int getMaxSize(List<Rep> normalizedReps) {
		
		int max = 0;
		for(Rep rep : normalizedReps) {
			if(rep.getSamples().size() > max) {
				max = rep.getSamples().size();
			}
		}
		return max;
	}
	
	
	private String getFilePath(int id, String exerciseName) {
		
		String pathAsString = "\\Dropbox\\Edu_ITTralee\\zFinal Year Project\\Data\\csv_files";		
		Path desktopPath = Paths.get("G:" + pathAsString);
		if(Files.exists(desktopPath)) {
			pathAsString = "G:" + pathAsString;
		}
		else {
			pathAsString = "C:" + pathAsString;
		}
		pathAsString += "\\Exercise-" + id + "-" + exerciseName + ".csv";
		return pathAsString;
	}
	
}
