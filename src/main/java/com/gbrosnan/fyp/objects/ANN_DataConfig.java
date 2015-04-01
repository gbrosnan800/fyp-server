package com.gbrosnan.fyp.objects;

public class ANN_DataConfig {

	private String trainingFileName;
	private String testingFileName;
	private int inputPerceptrons;
	private int hiddenPerceptrons;
	private int outputPerceptrons;
	private double learningRate;
	private double momentum;
	private int maxIterations;
	
	public ANN_DataConfig() {}
	
	public ANN_DataConfig(	String trainingFileName, String testingFileName, int inputPerceptrons, 
			int hiddenPerceptrons, int outputPerceptrons, double learningRate, double momentum, int maxIterations) {
		
		setTrainingFileName(trainingFileName);
		setTestingFileName(testingFileName);
		setInputPerceptrons(inputPerceptrons);
		setHiddenPerceptrons(hiddenPerceptrons);
		setOutputPerceptrons(outputPerceptrons);
		setLearningRate(learningRate);
		setMomentum(momentum);
		setMaxIterations(maxIterations);	
	}

	public String getTrainingFileName() {
		return trainingFileName;
	}

	public void setTrainingFileName(String tainingFileName) {
		this.trainingFileName = tainingFileName;
	}

	public String getTestingFileName() {
		return testingFileName;
	}

	public void setTestingFileName(String testingFileName) {
		this.testingFileName = testingFileName;
	}

	public int getInputPerceptrons() {
		return inputPerceptrons;
	}

	public void setInputPerceptrons(int inputPerceptrons) {
		this.inputPerceptrons = inputPerceptrons;
	}

	public int getHiddenPerceptrons() {
		return hiddenPerceptrons;
	}

	public void setHiddenPerceptrons(int hiddenPerceptrons) {
		this.hiddenPerceptrons = hiddenPerceptrons;
	}

	public int getOutputPerceptrons() {
		return outputPerceptrons;
	}

	public void setOutputPerceptrons(int outputPerceptrons) {
		this.outputPerceptrons = outputPerceptrons;
	}

	public double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	public double getMomentum() {
		return momentum;
	}

	public void setMomentum(double momentum) {
		this.momentum = momentum;
	}

	public int getMaxIterations() {
		return maxIterations;
	}

	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
	}
	

}
