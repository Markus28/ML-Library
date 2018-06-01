public class AutoEncoder extends Perceptron
{
    private int halfLayerCount;
    
    public AutoEncoder(int[] halfLayers)
    {
        super(AutoEncoder.halfToFull(halfLayers));
        halfLayerCount = halfLayers.length;
    }
    
    private static int[] halfToFull(int[] halfLayers)
    {
        int[] layers = new int[2*halfLayers.length - 1];
        
        for(int n = 0; n < layers.length; n++)
        {
            if(n < halfLayers.length)
            {
            layers[n] = halfLayers[n];
            }
            
            else{
                layers[n] = halfLayers[2*halfLayers.length - n - 1];
            }
        }
        
        return layers;
    }

    public void train(double[][] x) throws Exception
    {
        super.train(x, x);                 //This is correct, in an AutoEncoder the output should be equal to the input
    }
    
    public double[] encode(double[] x) throws Exception
    {
        return activations(x)[halfLayerCount - 1];
    }
    
    public double[] decode(double[] x) throws Exception
    {
        double[][] akt = new double[halfLayerCount][];
        akt[0] = x;
        
        for(int n = 0; n < getWeights().length; n++)
        {
            akt[n + 1] = activivateVector(getWeights()[n+halfLayerCount-1].vektorMul(akt[n]), n+halfLayerCount-1);      //Davor x anstatt akt[n]
        }
        
        return akt[halfLayerCount-1];
    }
    
    public String getDescription()
    {
        return "Autoencoder";
    }
}
