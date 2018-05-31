public class LinearRegression
{
    private int n;
    private double[] factors;
    private double constant;
    
    public LinearRegression(int features)
    {
        n = features;
    }

    public void train(double[][] x, double[] y)
    {
        
    }
    
    public double predict(double[] x)
    {
        double sum = 0;
        
        for(int i = 0; i < factors.length; i++)
        {
            sum += x[i]*factors[i];
        }
        
        return sum+constant;
    }
}
