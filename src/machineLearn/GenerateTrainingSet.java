/*
 * Class that holds sets for training and testing, includes methods for control of a set
 */
package machineLearn;

import java.util.ArrayList;

/**
 * @author Antanas
 * @date 2nd of March 2019
 */
public class GenerateTrainingSet {
    public final int sizeOfInput;
    public final int sizeOfOutput;

    //creat a multi dimensional 2X2 array
    //side 0 - input and side 1 = is expected output
    private ArrayList<double[][]> data = new ArrayList<>();

    public GenerateTrainingSet(int sizeOfInput, int sizeOfOutput) {
        this.sizeOfInput = sizeOfInput;
        this.sizeOfOutput = sizeOfOutput;
    }

   
    //generate arrays of data for training
    
    public void addDataToSet(double[] inputData, double[] expectedData) {
    	//make sure the user sent range is not greated or less than the
    	//specify size of training set to avoid overflow
        if(inputData.length == sizeOfInput && expectedData.length == sizeOfOutput) {
        	data.add(new double[][]{inputData, expectedData});
        };
    }

    //generate batch of data for the training to improve accuracy
    public GenerateTrainingSet extractBatch(int sizeOfBatch) {
        if(sizeOfBatch > 0 && getDatasize() <= this.getDatasize()) {
        	GenerateTrainingSet set = new GenerateTrainingSet(sizeOfInput, sizeOfOutput);
            Integer[] ids = HelperClass.randomValues(0,this.getDatasize() - 1, sizeOfBatch);
            for(Integer i:ids) {
                set.addDataToSet(this.getAllInputData(i),this.getAllOutputData(i));
            }
            return set;
        }else return this;
    }
    
    

    //to get the length of the data
    public int getDatasize() {
        return data.size();
    }

    //this fetch all the input sets
    public double[] getAllInputData(int index) {
        if(index >= 0 && index < getDatasize())
            return data.get(index)[0];
        else return null;
    }

    
    public double[] getAllOutputData(int index) {
        if(index >= 0 && index < getDatasize())
            return data.get(index)[1];
        else return null;
    }


	public int getSizeOfInput() {
		return sizeOfInput;
	}


	public int getSizeOfOutput() {
		return sizeOfOutput;
	}

	 
    
   
    
}
