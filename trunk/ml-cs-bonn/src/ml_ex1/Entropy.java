package ml_ex1;

public class Entropy {

	static double log(double x, int base)
	{
		if ( x == 0 )
			return 0;
		else
			return (double) (Math.log(x) / Math.log(base));
	}
	
	static double calcEntropy(double first_set, double second_set )
	{
		double all=first_set+second_set;
		double var1;
		double var2;
		
		if (first_set == 0 && second_set == 0 )
		{
			return 0;
		}
		
		if (first_set==0)
			var1=0;
		else
			var1=1.0/(first_set/all);
			
		
		if(second_set==0)
			var2=0;
		else
			var2=1.0/(second_set/all);
		
		return ( first_set/(all) )*Entropy.log( (var1) , 2 ) + ( second_set/all ) * Entropy.log( (var2) ,2 );
	}
}
