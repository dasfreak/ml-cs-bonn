package ml_ex1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Train {
	
	public static Tree tree;
	
	public static void main(String[] args)  {
		
		tree=new Tree();
		double entropy_target = tree.calculateTargetEntropy(DataStructureSingleton.getInstance1()); //calculate the entropy of the target which is used in other calculations
		double range = DataStructureSingleton.getInstance1().length;
		tree.go(entropy_target, range);
		
//		FileReader.printReadDataArray(DataStructureSingleton.getInstance1());
//		FileReader.printReadDataArrayList(DataStructureSingleton.getInstance());

	}
}