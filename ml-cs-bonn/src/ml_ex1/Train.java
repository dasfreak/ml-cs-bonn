package ml_ex1;

public class Train {
	
	public static final boolean PRINT_TABLE = false;

	public static void main(String[] args)  {
		TdidtAlgo algo = new TdidtAlgo();
		//algo.printTree();
		Attribute[] row = InputReader.getTestSet().getData()[0];
		for ( int i = 0; i < row.length; i++ )
			System.out.print(row[i].getData()+", ");
		System.out.println();

	//	algo.runExample(row);
		
		algo.saveToFile();
	}
}