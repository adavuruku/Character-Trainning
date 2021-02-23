package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import machineLearn.GenerateTrainingSet;
import machineLearn.HelperClass;
import machineLearn.NetworkBase;


public class AppConfig {
	//Default settings
    public static final String TRAINING_FILE_PATH = System.getProperty("user.dir") + "\\\\Data\\\\cw2DataSet2.csv";
    public static final String TEST_FILE_PATH = System.getProperty("user.dir") + "\\\\Data\\\\cw2DataSet1.csv"; 

//    public static final String TEST_FILE_PATH = "C:\\\\Users\\\\SHERIFF\\\\Downloads\\\\Recognizing-Handwritten-Digits-KNN-and-MLP-master\\\\Recognizing-Handwritten-Digits-KNN-and-MLP-master\\\\Data\\\\cw2DataSet1.csv"; 
    public final static boolean NERD_PRINTS = true; //If set to true will print the guess and original labels
    
    //K-nearest neigbours  settings
    public final static boolean USE_KNN_ALGORITHM = true; //If set to true, app will run KNN algorithm
    public final static int K_VALUE = 1;   //Value for the amount of the nambers to check for (Best results with K = 1)
    
    //Neural Network settings
    public final static boolean USE_NEURAL_NETWORK = false; //If set to true, app will run NEURAL NETWORK
    public static final double LEARNING_RATE = 0.1;
    public final static double BIAS_RANGE_SMALLEST = -0.5;
    public final static double BIAS_RANGE_BIGGEST = 0.7;
    public final static double WEIGHTS_RANGE_SMALLEST = -1;
    public final static double WEIGHTS_RANGE_BIGGEST = 1;
    public final static int TRAINING_EPOCHS_VALUE = 500;
    public final static int TRAINING_LOOPS_VALUE = 500;
    public final static int TRAINING_BATCH_SIZE = 32;
    public final static int AppConfig = 26;
    public final static int FIRST_HIDDEN_LAYER_NODE_AMOUNT = 26;
    public final static int SECOND_HIDDEN_LAYER_NODE_AMOUNT = 15;
    public final static int INPUT_LAYER_NODE_AMOUNT = 64; //Because the image is 64pixels 8*8
    
    //read excel file to generate training set
    public static GenerateTrainingSet generateTrainingDataFromFile(){
    	GenerateTrainingSet set = new GenerateTrainingSet(INPUT_LAYER_NODE_AMOUNT, 10);
    	Scanner fileScanner =null;
    	try {
    		 	fileScanner = new Scanner(new File(TRAINING_FILE_PATH));
    	        while(fileScanner.hasNextLine()){
    	            String line = fileScanner.nextLine();
    	            int lastCommaIndex = line.lastIndexOf(',');
    	            int label = Integer.parseInt(line.substring(lastCommaIndex+1, lastCommaIndex +2));
    	            String newLine = line.substring(0,lastCommaIndex);
    	            String[] splitLine = newLine.split(",");
    	            System.out.println("lastCommaIndex = "+lastCommaIndex + ", label = " +label + splitLine[0]);
    	            //Build an array to add to a set
    	            double[] splitLineNumber = new double[splitLine.length];
    	            for(int i = 0; i<splitLine.length; i++){
    	                splitLineNumber[i] = Double.parseDouble(splitLine[i]);
    	            }
    	            double[] output = new double[10];
    	            output[label] = 1d;
    	            set.addDataToSet(splitLineNumber, output); 
    	        }
    	        
    	        return set;
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return null;
		}finally {
			fileScanner.close();
		}
    	
       
    }
    
    //read excel file for generate testingSet
    public static GenerateTrainingSet generateTestingDataFromFile(){
    	GenerateTrainingSet set = new GenerateTrainingSet(INPUT_LAYER_NODE_AMOUNT, 10);
    	Scanner fileScanner = null;
        try {
        	 fileScanner = new Scanner(new File(TEST_FILE_PATH));
             while(fileScanner.hasNextLine()){
                 String line = fileScanner.nextLine();
                 int lastCommaIndex = line.lastIndexOf(',');
                 
                 int label = Integer.parseInt(line.substring(lastCommaIndex+1, lastCommaIndex +2));
                 
                 String newLine = line.substring(0,lastCommaIndex);
                 String[] splitLine = newLine.split(",");
                 double[] splitLineNumber = new double[splitLine.length];
                 for(int i = 0; i<splitLine.length; i++){
                     splitLineNumber[i] = Double.parseDouble(splitLine[i]);
                 }
                 double[] output = new double[10];
                 output[label] = 1d;
                 set.addDataToSet(splitLineNumber, output); 
             }
             
             return set;
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return null;
		}finally {
			fileScanner.close();
		}
       
        
    }
    
    public static void trainData(NetworkBase net, GenerateTrainingSet set, int epochs, int loops, int batch_size){
        for(int epoch= 0; epoch < epochs; epoch++){
            net.startTrain(set, loops, batch_size);
            System.out.println("Training neural network...");
            if(NERD_PRINTS){
                System.out.println("Epochs : " + epoch + "/" + epochs);
            }
        }
    }
    
    public static void runTrainSet(NetworkBase net, GenerateTrainingSet set){
        int correct = 0;
        for(int i = 0; i < set.getDatasize(); i++){
            double highest = HelperClass.returnIndexOfHighestValue(net.calculationFunction(set.getAllInputData(i)));
            double actualHighest = HelperClass.returnIndexOfHighestValue(set.getAllOutputData(i));
            if(NERD_PRINTS){
                System.out.println("Guess : " + highest + " Real thing : " + actualHighest);
            }
            if(highest == actualHighest){
                correct ++;
            }
        }
        HelperClass.printFinalResults(correct, set.getDatasize());
    }
}
