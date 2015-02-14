package com.gbrosnan.fyp.persistdata;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.gbrosnan.fyp.objects.ExerciseProcessed;

public class ExerciseProcessedToExcel {

	public void createExcelFile(ExerciseProcessed exerciseProcessed) {
		
		try {
			
			String filePath = getFilePath(exerciseProcessed.getId(), exerciseProcessed.getExerciseName());
			FileOutputStream fileOut = new FileOutputStream(filePath);
					
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet1 = workbook.createSheet("Raw Data + Averages");
			HSSFSheet worksheet2 = workbook.createSheet("Extracted Reps");
			HSSFSheet worksheet3 = workbook.createSheet("Normalised Reps");
			
			
			/*
			  
			 
			CreateWorkBook()
				populateWorkSheet1()  
				populateWorkSheet2()
				populateWorkSheet3()  
			Write workbook to fileOut
			
			 
			 */
			
			/* CODE FROM LAST SEMESTER 
			HSSFRow row;
			HSSFCell cell;					
			long current = 0;
			double x = 0, y = 0, z = 0, average = 0;
			
			for(int sample = 0 ; sample < exerciseProcessed.getRawSensorSamples().size() ; sample++ ) {		
				
				current = exerciseProcessed.getRawSensorSamples().get(sample).getTimestamp();
				x = exerciseProcessed.getRawSensorSamples().get(sample).getX();
				y = exerciseProcessed.getRawSensorSamples().get(sample).getY();
				z = exerciseProcessed.getRawSensorSamples().get(sample).getZ();
				average = (x + y + z)/3;
				
				row = worksheet1.createRow(sample);
				cell = row.createCell(0);
				cell.setCellValue(current);				
				cell = row.createCell(1);
				cell.setCellValue(average);
			}
			
			*/
			
			workbook.write(fileOut);
			workbook.close();
			fileOut.flush();
			fileOut.close();						
		}
		catch(Exception ex) {
			System.out.println(ex.toString());
		}		
	}
	
	private String getFilePath(int id, String exerciseName) {
		
		String pathAsString = "\\Dropbox\\Edu_ITTralee\\zFinal Year Project\\Data\\excel_files";		
		Path desktopPath = Paths.get("G:" + pathAsString);
		Path laptopPath = Paths.get("C:" + pathAsString);
		
		if(Files.exists(desktopPath)) {
			pathAsString = "G:" + pathAsString;
		}
		else {
			pathAsString = "C:" + pathAsString;
		}
		pathAsString += "\\Exercise-" + id + "-" + exerciseName + "xls";
		return pathAsString;
	}
	
	
}
