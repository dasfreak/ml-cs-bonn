package ml_ex1;

public class InputReader {
	//private static FileReader originalData;
	private static FileReader trainingSet;
	private static FileReader testSet;
	
	protected InputReader() {
	      // Exists only to defeat instantiation.
	}
	
	public static FileReader getInstance() {
	      if( trainingSet == null) {
	    	 // uncomment this to use whole training set
	    	 //  trainingSet = new FileReader("data_exercise_1.csv");
	    	// trainingSet = new FileReader("training_set.csv");

//	    	 trainingSet = new FileReader("data_exercise_1.csv");

	    	 trainingSet = new FileReader("ex2.csv");
//	    	 trainingSet = new FileReader("lecture_example.csv");


	      }
	      return trainingSet;
	}
	
	public static FileReader getTestSet() {
	      if( testSet == null) {
	    	  testSet = new FileReader ("ex2.csv");
	      }
	      return testSet;
	}
}