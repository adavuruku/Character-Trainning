package machineLearn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class AppConfig {
	//Default settings
    public static final String TRAINING_FILE_PATH = System.getProperty("user.dir") + "\\\\Data\\\\cw2DataSet2.csv";
    public static final String TEST_FILE_PATH = System.getProperty("user.dir") + "\\\\Data\\\\cw2DataSet1.csv"; 

    //Neural Network settings
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
    
    /*
     * Method that read the csv 
     * file and load the neurons
     * for training dataSet
     * */
    public static TrainingSet generateTrainingDataFromFile(){
    	TrainingSet set = new TrainingSet(INPUT_LAYER_NODE_AMOUNT, 10);
    	Scanner fileScanner =null;
    	try {
    		 	fileScanner = new Scanner(new File(TRAINING_FILE_PATH));
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
    
    /*
     * Method that read the csv 
     * file and load the neurons
     * for testing dataSet
     * */
    public static TrainingSet generateTestingDataFromFile(){
    	TrainingSet set = new TrainingSet(INPUT_LAYER_NODE_AMOUNT, 10);
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
    
    /**
     * Function that trains the testing dataset to convert it to neuron 
     * for the Network Training the
     * */
    
    public static void trainData(NetworkBase net, TrainingSet set, int epochs, int loops, int batch_size){
    	System.out.println("Training neural network...");
    	for(int epoch= 0; epoch < epochs; epoch++){
            net.startTrain(set, loops, batch_size);
            System.out.println("Training : " + epoch + "/" + epochs);
        }
    }
    
  /*
   * print of Result of the result of train dataset (expected reuslt and trained result)
   * */
    public static void runTrainSet(NetworkBase net, TrainingSet set){
    	System.out.println("Result Of Train Datasets");
    	System.out.println("****************************************");
        int correct = 0;
        for(int i = 0; i < set.getDatasize(); i++){
            double highest = returnIndexOfHighestValue(net.calculationFunction(set.getAllInputData(i)));
            double actualHighest = returnIndexOfHighestValue(set.getAllOutputData(i));
            System.out.println("Train : ("+ i + ") -> Actual Value : " + actualHighest + " Guess Value : " + highest);
            if(highest == actualHighest){
                correct ++;
            }
        }
        printFinalResults(correct, set.getDatasize());
    }
    
    

    public static double[] buildRandomArray(int range, double smallest, double biggest){
        if(range < 1){
            return null;
        }
        double[] returnArray = new double[range];
        for(int index = 0; index < range; index++){
            returnArray[index] = generateRandomValue(smallest, biggest);
        }
        return returnArray;
    }
    public static double[][] buildRandomArray(int rangeX, int rangeY, double smallest, double biggest){
        //If requested to generate an empty array return null
        if(rangeX < 1 || rangeY < 1){
            return null;
        }
        //Create an array of specified range
        double[][] returnArray = new double[rangeX][rangeY];
        //Iterate through the array of specified size, and build a random array
        // of specified size
        for(int index = 0; index < rangeX; index++){
            returnArray[index] = buildRandomArray(rangeY,smallest, biggest);
        }
        return returnArray;
    }
    
    /**
     * Method that creates a random value of specified bounds
     * will be used when generating a random array
     * @param smallest lowest bound
     * @param biggest highest bound
     * @return  random number
     */
    public static double generateRandomValue(double smallest, double biggest){
        return Math.random()*(biggest - smallest) + smallest;
    }
    
    /**
     * Generates an array of integers of specified bounds and size, but also limits
     * the values to once in array
     * @param smallest lower bound
     * @param biggest higher bound
     * @param size size of random array 
     * @return array of random values
     */
    public static Integer[] randomValues(int smallest, int biggest, int size){
        smallest --;
        
        if(size > (biggest - smallest)){
            return null;
        }
        
        Integer[] values = new Integer[size];
        for(int index = 0; index < size; index++){
            int number = (int) (Math.random() * (biggest - smallest + 1) + smallest);
            while(containsValue(values, number)){
                number = (int)(Math.random() * (biggest - smallest + 1) + smallest);
            }
            values[index] = number;
        }
        return values;
    }
    
    /**
     * Checks if array contains a value comparable extension
     * @param <T> object
     * @param array array of comparison
     * @param value value to compare
     * @return result 
     */
   public static <T extends Comparable<T>> boolean containsValue(T[] array, T value){
       for(int index = 0; index < array.length; index++){
           if(array[index] != null){
               if(value.compareTo(array[index]) == 0 ){
                   //If contains return true
                   return true;
               }
           }
       }
       //If doesnt contain return false
       return false;
   }
   
   /**
    * Return the index of the highest value in the provided array
    * @param input array to get the highest value index
    * @return index of the highest value
    */
   public static int returnIndexOfHighestValue(double[] input){
       int returnIndex = 0;
       for(int iterationIndex = 1; iterationIndex < input.length; iterationIndex++){
           if(input[iterationIndex] > input[returnIndex]){
               returnIndex = iterationIndex;
           }
       }
       return returnIndex;
   }
   

    
    /**
     * Prints out the accuracy of an algorithm
     * @param goodResults total of correct guessed results
     * @param totalInputs total inputs number
     */
    public static void printFinalResults(int goodResults, int totalInputs){
    	System.out.println("\n**********************************************");
    	System.out.println("Training Character Set Result");
    	System.out.println("**********************************************\n");
    	System.out.println("Total No. of Inputs :" + totalInputs);
        System.out.println("No of Good Result : "+ goodResults);
        System.out.println("Estimated Accuracy (%): " + (double)((double)goodResults * 100 / (double)totalInputs) + "% ");
        System.out.println("**********************************************");
    }
}
