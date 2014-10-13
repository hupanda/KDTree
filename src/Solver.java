
public class Solver {

	public Solver() {}
	
	public double Solve(double b)
	{
 	   
	 	double[] N = {65536,131072,262144,524288,1048576,2097152,4194304,8388608,16777216,33554432,67108864,134217728,268435456,536870912};
	 	double[] T = {0.001,0.001,0.004,0.009, 0.022,0.056,0.137,0.340,0.842,2.082,5.155,12.757,31.582,78.182};
	 	double[] a = new double[T.length];
	     
	 	for (int i=0;i<a.length;i++)
	 	{
	 		a[i] = T[i] / Math.pow(N[i], b);
	 	}
	 	
	 	
	 	
	 	double powerSum1 = 0;
	 	double powerSum2 = 0;
	
	 	for (int i=0;i<a.length;i++)
	 	{
	 		System.out.println(a[i]);
	 	    powerSum1 += a[i];
	 	    powerSum2 += Math.pow(a[i], 2);
	 	}
	 	return powerSum1/14;
	}
	
	
    public static void main(String[] args) 
    {
    	Solver s = new Solver();
    	System.out.println(s.Solve(1.28));
//    	for (int i=0;i<100;i++)
//    	{
//    		double x = 1 + (double)i/100;
//    		System.out.println(i + ": " + x + "   " + s.Solve(x));
//    	}
        
    }
    
    
    
}
