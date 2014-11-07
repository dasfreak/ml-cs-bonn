package ml_ex1;

import java.util.List;

public class DataStructureSingleton {
	private static Attribute[][] instance = null; 
	private static Attribute[] pattern;
	private static String[] nodesNames;
	
	protected DataStructureSingleton() {
	      // Exists only to defeat instantiation.
	}
	
	
	public static Attribute[][] getInstance1() {
	      if(instance == null) {
	         instance = FileReader.loadDatasetFromFile1("data_exercise_1.csv");
	      }
	      return instance;
	}	
		
	public static Attribute[] getPattern1() {
	      if(pattern == null) {
	    	  pattern = FileReader.pattern;
	      }
	      return pattern;
	}
	
	public static String[] getNodeNames() {
	      if(nodesNames == null) {
	    	  nodesNames = FileReader.nodesNames;
	      }
	      return nodesNames;
	}
}