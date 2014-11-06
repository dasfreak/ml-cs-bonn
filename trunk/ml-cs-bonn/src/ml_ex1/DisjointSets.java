package ml_ex1;

public class DisjointSets {
    Subset set1;
    Subset set2;
    double mean;
    double entropySubsets;
    double informationGain;
    
    public DisjointSets( Subset set1, Subset set2, double mean )
    {
            this.set1 = set1;
            this.set2 = set2;
            this.mean = mean;
    }
}
