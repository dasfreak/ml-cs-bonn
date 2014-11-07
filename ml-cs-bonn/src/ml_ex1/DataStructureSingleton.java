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
	    	  trainingSet = new FileReader("data_exercise_1.csv");
	      }
	      return trainingSet;
	}	
}