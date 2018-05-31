import java.util.List;

/**
 * Beschreiben Sie hier die Klasse Autoencoder.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class AutoEncoder extends Perzeptron
{
    int halfLayerCount;
    
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

    public void lernen(double[][] x) throws Exception
    {
        super.lernen(x, x);
    }
    
    public double[] encode(double[] x) throws Exception
    {
     return aktivierungen(x)[halfLayerCount - 1];
    }
    
    public double[] decode(double[] x) throws Exception
    {
        double[][] akt = new double[halfLayerCount][];
        akt[0] = x;
        
        for(int n = 0; n < getWeights().length; n++)
        {
            akt[n + 1] = aktiviereVektor(getWeights()[n+halfLayerCount-1].vektorMul(akt[n]), n+halfLayerCount-1);      //Davor x anstatt akt[n]
        }
        
        return akt[halfLayerCount-1];
    }
    
    public String getDescription()
    {
        return "Autoencoder";
    }
}