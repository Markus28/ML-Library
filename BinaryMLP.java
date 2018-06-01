/**
 * Beschreiben Sie hier die Klasse BinaryClassifier.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class BinaryMLP extends Perceptron implements BinaryClassifier
{
    private double cutoff;
    
    public BinaryMLP(int[] layers, double cutoff) throws Exception
    {
        super(layers);
        
        if(cutoff > 1 || cutoff < 0)
        {
            throw new Exception("Cutof...");
        }
        
        if(layers[layers.length-1] != 1)
        {
            throw new Exception("Last layer must only contain 1 neuron...");
        }
        
        this.cutoff = cutoff;
    }
    
    
    @Override
    public void train(double[][] x, boolean[] y) throws Exception
    {
        double[][] mlpY = new double[y.length][];
        
        for(int n = 0; n < y.length; n++)
        {
            mlpY[n] = new double[1];
            mlpY[n][0] = y[n]? 1 : 0;
        }
        
        super.train(x, mlpY);
    }
    
    @Override
    public boolean predict(double[] x) throws Exception
    {
        return heaviside(super.predictProbability(x)[0]);
    }
    
    public void setThreshold(double x) throws Exception
    {
        if(x < 0 || x > 1)
        {
            throw new Exception("Cutoff out of bounds 0 and 1...");
        }
        
        cutoff = x;
    }
    
    public double[] getThresholdBounds()
    {
        double[] bounds = {0.0,1.0};
        return bounds;
    }


    private boolean heaviside(double x)
    {
        return x >= cutoff;
    }
}
