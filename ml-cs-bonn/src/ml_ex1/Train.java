package ml_ex1;

public class Train {
	
	public static final boolean PRINT_TABLE = false;

	public static void main(String[] args)  {
		int numHits   = 0;
		int numMisses = 0;
		TdidtAlgo algo = new TdidtAlgo();
		algo.printTree();
		
		for (int i=0; i<InputReader.getTestSet().getData().length; i++ )
		{
			Attribute[] row = InputReader.getTestSet().getData()[i];
			boolean answer = algo.runExample( algo.getRoot(), row );
			if ( answer == (boolean)row[row.length-1].getData() )
			{
				numHits++;
			}
			else
			{
				numMisses++;
			}
		}
		System.out.println("NumHits = "+numHits+" NumMisses = "+numMisses);
		
		algo.saveToFile();
	}
}