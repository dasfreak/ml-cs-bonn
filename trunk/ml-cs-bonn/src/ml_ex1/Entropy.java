package ml_ex1;

public class Entropy {

	static double log(double x, int base)
	{
	    return (double) (Math.log(x) / Math.log(base));
	}
	
	static double calcEntropy(double first_set, double second_set, double all )
	{
		return ( first_set/(all) )*Entropy.log( (1.0/(first_set/all)) , 2 ) + ( second_set/all ) * Entropy.log( (1.0/(second_set/all)) ,2 );
	}
}
