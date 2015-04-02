package com.gbrosnan.fyp.ann;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TrainingSetImport;
import org.neuroph.util.TransferFunctionType;

import com.gbrosnan.fyp.objects.ANN_ConfigData;

public class ArtificalNeuralNetwork {

	private MultiLayerPerceptron neuralNet;
	private DataSet trainingSet;
	private DataSet testingSet;
		
	public ArtificalNeuralNetwork() {}
	
	public void configNetwork(ANN_ConfigData  annConfigData) {
		
		String trainingFileName = annConfigData.getTrainingFileName();
		String testingFileName = annConfigData.getTestingFileName();
		int inputCount = annConfigData.getInputPerceptrons();
		int outputCount = annConfigData.getOutputPerceptrons();
		int hiddenCount = annConfigData.getHiddenPerceptrons();
		double learingRate = annConfigData.getLearningRate();
		double momentum = annConfigData.getMomentum();
		int maxIterations = annConfigData.getMaxIterations();
		
		neuralNet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, inputCount, hiddenCount, outputCount);		
		trainingSet = getDataSet(trainingFileName, inputCount, outputCount);
		testingSet= getDataSet(testingFileName, inputCount, outputCount);		
		MomentumBackpropagation learningRule = getLearningRule(learingRate, momentum, maxIterations);
	}
	
	public void trainNetwork() {
		
		neuralNet.learn(trainingSet);
	}
	
	public List<List<String>> testClassificationOfExercises() {
		
		List<List<String>> outputList = new ArrayList<List<String>>(); 
		List<String> outputItem;		
		NeuralNetwork testNet = neuralNet;		
		for(DataSetRow dataSetRow : testingSet.getRows()) {
			
			testNet.setInput(dataSetRow.getInput());
			testNet.calculate();		
			double[] desiredOutput = dataSetRow.getDesiredOutput();
			double[] networkOutput = testNet.getOutput();
			
			int desiredFirst = (int) desiredOutput[0];
			int desiredSecond = (int) desiredOutput[1];
			int actualFirst = (int) Math.round(networkOutput[0]);
			int actualSecond = (int) Math.round(networkOutput[1]);
			String desiredExercise = getExercise(desiredFirst, desiredSecond);
			String recognizedExercise = getExercise(actualFirst, actualSecond);
			
			outputItem = new ArrayList<String>();
			outputItem.add(desiredExercise);
			outputItem.add(recognizedExercise);
			outputList.add(outputItem);
		}	
		return outputList;
	}
	
	public String recognizeExercise(DataSet dataset) {
		
		List<String> exercisesRecognized = new ArrayList<String>();
		NeuralNetwork testNet = neuralNet;		
		int row = 0;
		for(DataSetRow dataSetRow : dataset.getRows()) {
			
			testNet.setInput(dataSetRow.getInput());
			testNet.calculate();		
			double[] networkOutput = testNet.getOutput();
			
			int actualFirst = (int) Math.round(networkOutput[0]);
			int actualSecond = (int) Math.round(networkOutput[1]);	
			exercisesRecognized.add(getExercise(actualFirst, actualSecond));
		}			
		return getMode(exercisesRecognized);
	}
	
	private String getMode(List<String> exercisesRecognize) {
		
		int bicepCurl = 0, kickBack = 0, latRaise = 0, other = 0;
		
		for(String exercise : exercisesRecognize) {
			System.out.println(exercise);
			if(exercise.equals("bicep_curl")) {
				bicepCurl ++;
			} 
			else if (exercise.equals("lat_raise")) {
				latRaise ++;
			} 
			else if (exercise.equals("kick_back")) {
				kickBack ++;
			}  
			else {
				other ++;
			}
		}				
		return getMax(bicepCurl, kickBack, latRaise, other);
	}
	
	private String getMax(int bicepCurl, int kickBack, int latRaise, int other){
		
		System.out.println("BC= " + bicepCurl + "   KB= " + kickBack + "     LR= " + latRaise + "    O= " + other);
		
		if(bicepCurl > kickBack && bicepCurl > latRaise  && bicepCurl > other) {
			return "bicep_curl";
		}
		else if(kickBack > bicepCurl && kickBack > latRaise  && kickBack > other) {
			return "kick_back";
		} 
		else if(latRaise > bicepCurl && latRaise > kickBack  && latRaise > other) {
			return "lat_raise";
		} else {
			return "unrecognized";
		}
	}
	
	private String getExercise(int first, int second) {
		
		if(first == 0 && second == 0) {
			return "bicep_curl";
		}
		else if(first == 0 && second == 1) {
			return "lat_raise";
		}
		else if(first == 1 && second == 1) {
			return "kick_back";
		}
		else {
			return "unkown";
		}
	}
	
	private DataSet getDataSet(String filename, int inputCount, int outputCount) {
		
		String fullFilePath = getFilePath() + filename;

		DataSet dataset = null;
		try {
			dataset = TrainingSetImport.importFromFile(fullFilePath, inputCount, outputCount, ",");
		} catch (FileNotFoundException ex ){
			System.out.println(ex.getMessage());
		} catch (IOException ex){
			System.out.println(ex.getMessage());
		} catch (NumberFormatException ex) {
			System.out.println(ex.getMessage());
		}
		return dataset;		
	}
	
	private MomentumBackpropagation getLearningRule(double learningRate, double momentum, int maxIterations) {
		
		MomentumBackpropagation learningRule = (MomentumBackpropagation) neuralNet.getLearningRule();
		learningRule.setLearningRate(learningRate);
		learningRule.setMomentum(momentum);
		learningRule.setMaxIterations(maxIterations);		
		return learningRule;
	}
	
	
    private String getFilePath() {
    	
    	if(System.getProperty("catalina.base").equals("G:\\eclipse_workspaces\\fyp\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0")) {
    		return System.getProperty("catalina.base") + "/wtpwebapps/fyp-server/csv/";
    	}
    	else {
    		return System.getProperty("catalina.base") + "/webapps/fyp-server/csv/";
    	}
    }
	
	
	
	


	


	

	
	
	
	
	
}
