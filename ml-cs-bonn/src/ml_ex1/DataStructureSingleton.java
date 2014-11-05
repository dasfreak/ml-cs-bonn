package ml_ex1;

import java.util.List;

public class DataStructureSingleton {
	private static List<List<Attribute>> instance = null;
	private List<Attribute> pattern = null;
	private static Attribute[][] instance1 = null; 
	private static Attribute[] pattern1;
	private static String[] nodesNames;
	
	protected DataStructureSingleton() {
	      // Exists only to defeat instantiation.
	}
	
	public static List<List<Attribute>> getInstance()  {
	      if(instance == null) {
	         instance = FileReader.loadDatasetFromFile("data_exercise_1.csv");
	      }
	      return instance;
	}
	
	public static Attribute[][] getInstance1() {
	      if(instance1 == null) {
	         instance1 = FileReader.loadDatasetFromFile1("data_exercise_1.csv");
	      }
	      return instance1;
	}	
	
	private List<Attribute> getPattern() {
	      if(pattern == null) {
	    	  pattern = FileReader.pattern;
	      }
	      return pattern;
	}
	
	public static Attribute[] getPattern1() {
	      if(pattern1 == null) {
	    	  pattern1 = FileReader.pattern1;
	      }
	      return pattern1;
	}
	
	public static String[] getNodeNames() {
	      if(nodesNames == null) {
	    	  nodesNames = FileReader.nodesNames;
	      }
	      return nodesNames;
	}
}