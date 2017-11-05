import java.util.List;

/**
 * Beschreiben Sie hier die Klasse BinaryClassifier.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class BinaryMLP extends Perzeptron implements BinaryClassifier
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
    

    public void lernen(double[][] x,  boolean[] y, List parameters) throws Exception
    {
        double[][] mlpY = new double[y.length][];
        
        for(int n = 0; n < y.length; n++)
        {
            mlpY[n] = new double[1];
            mlpY[n][0] = y[n]? 1 : 0;
        }
        
        super.lernen(x, mlpY, (int) parameters.get(0), (int) parameters.get(1), (double) parameters.get(2));
    }
    
    
    public boolean vorhersage(double[] x) throws Exception
    {
        return heaviside(super.vorhersageWahrscheinlichkeit(x)[0]);
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
        if(x >= cutoff)
        {
            return true;
        }
        
        return false;
    }
}
