package ml_ex1;

import java.util.List;

public class DataStructureSingleton {
	//private static FileReader originalData;
	private static FileReader trainingSet;
	private static FileReader testSet;
	
	protected DataStructureSingleton() {
	      // Exists only to defeat instantiation.
	}
	
	public static FileReader getInstance1() {
	      if( trainingSet == null) {
	    	 // uncomment this to use whole training set
	    	 //  trainingSet = new FileReader("data_exercise_1.csv");
	    	  trainingSet = new FileReader("training_set.csv");
	    	  testSet     = new FileReader("test_set.csv");
	      }
	      return trainingSet;
	}	
}