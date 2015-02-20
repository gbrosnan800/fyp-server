package com.gbrosnan.fyp.persistdata;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gbrosnan.fyp.objects.ProcessedExercise;
import com.gbrosnan.fyp.objects.Rep;
import com.gbrosnan.fyp.objects.SensorSample;

public class ExerciseProcessedToExcel {

	public void createExcelFile(ProcessedExercise exerciseProcessed) {
		
		try {	
			String filePath = getFilePath(exerciseProcessed.getId(), exerciseProcessed.getExerciseName());
			FileOutputStream fileOut = new FileOutputStream(filePath);

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet rawDataSheet = createRawDataSheet(workbook, exerciseProcessed.getRawSensorSamples(), exerciseProcessed.getAverages()); 
			HSSFSheet repDataSheet = createRepDataSheet(workbook, exerciseProcessed.getExtractedReps(), "Extracted Reps");
			HSSFSheet repNormalizedDataSheet = createRepDataSheet(workbook, exerciseProcessed.getNormalisedReps(), "Normalised Reps");

			workbook.write(fileOut);
			workbook.close();
			fileOut.flush();
			fileOut.close();						
		}
		catch(Exception ex) {
			System.out.println(ex.toString());
		}		
	}
	
	
	private HSSFSheet createRawDataSheet(HSSFWorkbook workbook, List<SensorSample> rawSamples, List<Double> averages ) {
		
		HSSFSheet worksheet = workbook.createSheet("Raw Data + Averages");
		HSSFRow row;
		HSSFCell cell;					
		long time = 0;
		double x = 0, y = 0, z = 0, average = 0;
		
		for(int sample = 0 ; sample < rawSamples.size() ; sample++ ) {		
			
			time = rawSamples.get(sample).getTimestamp();
			x = rawSamples.get(sample).getX();
			y = rawSamples.get(sample).getY();
			z = rawSamples.get(sample).getZ();
			average = averages.get(sample);
			
			row = worksheet.createRow(sample);
			cell = row.createCell(0);
			cell.setCellValue(time);
			cell = row.createCell(1);
			cell.setCellValue(x);		
			cell = row.createCell(2);
			cell.setCellValue(y);		
			cell = row.createCell(3);
			cell.setCellValue(z);		
			cell = row.createCell(4);
			cell.setCellValue(average);
		}		
		return worksheet;	
	}
	
	private HSSFSheet createRepDataSheet(HSSFWorkbook workbook, List<Rep> repList, String sheetName) {

		HSSFSheet worksheet = workbook.createSheet(sheetName);	
		HSSFRow row;
		HSSFCell cell;			
		
		int maxSize = 0;
		for(Rep rep : repList) {
			if(rep.getSamples().size() > maxSize) {
				maxSize = rep.getSamples().size();
			}
		}	
		ArrayList<HSSFRow> rows = new ArrayList<HSSFRow>();
		for(int i = 0 ; i < (maxSize + 1) ; i ++) {
			row = worksheet.createRow(i);
			rows.add(row);
		}
		int rowNum = 0;			
		for(int repNum = 0; repNum < repList.size() ; repNum ++) {
			
			List<Double> repSamples = repList.get(repNum).getSamples();
			for(int r = 0 ; r < repSamples.size() ; r ++) {
				rowNum = 0;
				cell = rows.get(r).createCell(repNum);
				cell.setCellValue(repSamples.get(r));
			}
		}
		return worksheet;
	}
	
	
	
	private String getFilePath(int id, String exerciseName) {
		
		String pathAsString = "\\Dropbox\\Edu_ITTralee\\zFinal Year Project\\Data\\excel_files";		
		Path desktopPath = Paths.get("G:" + pathAsString);
		if(Files.exists(desktopPath)) {
			pathAsString = "G:" + pathAsString;
		}
		else {
			pathAsString = "C:" + pathAsString;
		}
		pathAsString += "\\Exercise-" + id + "-" + exerciseName + ".xls";
		return pathAsString;
	}
	
	
}
