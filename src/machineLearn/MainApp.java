package machineLearn;

import java.io.FileNotFoundException;

import config.AppConfig;


public class MainApp {
	public static void main(String[] args) {
		
		System.out.println("Starting neural network...");
	    //Build a network with 64 nodes in input layer, 26 in 1st hidden layer, 15 in 2nd hidden layer and 10 output
	    NetworkBase network = new NetworkBase(new int[]{AppConfig.INPUT_LAYER_NODE_AMOUNT, AppConfig.FIRST_HIDDEN_LAYER_NODE_AMOUNT, AppConfig.SECOND_HIDDEN_LAYER_NODE_AMOUNT, 10});
	    try{
	        GenerateTrainingSet set = AppConfig.generateTrainingDataFromFile();
	        AppConfig.trainData(network, set , AppConfig.TRAINING_EPOCHS_VALUE, AppConfig.TRAINING_LOOPS_VALUE, AppConfig.TRAINING_BATCH_SIZE);
	        GenerateTrainingSet testSet = AppConfig.generateTestingDataFromFile();
	        AppConfig.runTrainSet(network, testSet);
	    }catch(Exception ex){
	        System.out.println("Error when reading NN test or train file!");
	    }
	}
	
}
