package ml_ex1;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class DataStructureSingleton {
	private static List<List<Attribute>> instance = null;
	private static Attribute[][] instance1 = null; 
	
	protected DataStructureSingleton() {
	      // Exists only to defeat instantiation.
	}
	
	public static List<List<Attribute>> getInstance() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
	      if(instance == null) {
	         instance = FileReader.loadDatasetFromFile("data_exercise_1.csv");
	      }
	      return instance;
	}
	
	public static Attribute[][] getInstance1() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
	      if(instance1 == null) {
	         instance1 = FileReader.loadDatasetFromFile1("data_exercise_1.csv");
	      }
	      return instance1;
	}	
}