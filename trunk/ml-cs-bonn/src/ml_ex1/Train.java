package ml_ex1;

public class Train {
	
	public static final boolean PRINT_TABLE = true;

	public static void main(String[] args)  {
		int numHits   = 0;
		int numMisses = 0;
		TdidtAlgo algo = new TdidtAlgo();
		algo.printTree();
		
		for (int i=0; i<InputReader.getTestSet().getData().length; i++ )
		{
			Attribute[] row = InputReader.getTestSet().getData()[i];
//			System.out.println("row[0] = "+row[0].getData());
			boolean answer = algo.runExample( algo.getRoot(), row );
//			System.out.println(" <=== runExample");
			if ( answer == (boolean)(row[row.length-1].getData()) )
			{
				numHits++;
			}
			else
			{
				numMisses++;
			}
		}
//		System.out.println("NumHits = "+numHits+" NumMisses = "+numMisses);
		
		algo.saveToFile();
	}
}