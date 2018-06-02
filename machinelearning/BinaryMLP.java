package machinelearning;

public class BinaryMLP extends Perceptron implements BinaryClassifier
{
    private double cutoff;
    
    public BinaryMLP(int[] layers, double cutoff) throws IllegalArgumentException
    {
        super(layers);
        
        if(cutoff > 1 || cutoff < 0)
        {
            throw new IllegalArgumentException("Cutoff must be between 0 and 1");
        }
        
        if(layers[layers.length-1] != 1)
        {
            throw new IllegalArgumentException("Last layer must only contain 1 neuron...");
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

    public double getTrueProbability(double[] x) throws Exception {
        return super.predictProbability(x)[0];
    }

    public void setCutoff(double x) throws Exception
    {
        if(x < 0 || x > 1)
        {
            throw new IllegalArgumentException("Last layer must only contain 1 neuron...");
        }
        
        cutoff = x;
    }


    private boolean heaviside(double x)
    {
        return x >= cutoff;
    }
}
