import java.util.ArrayList;

/**
 * Beschreiben Sie hier die Klasse MultiClassMLP.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class MultiClassMLP<T> extends Perceptron implements MultiClassClassifier<T>, java.io.Serializable
{
    private OneHotCoder<T> coder;
    
    public MultiClassMLP(int[] layers)
    {
        super(layers);
    }
    
    @Override
    public void train(double[][] x, ArrayList<T> y) throws Exception
    {
        if(coder == null)
        {
            coder = new OneHotCoder<>(y);
        }
        
        if(coder.encode(y.get(0)).length != getLayers()[getLayers().length - 1])
        {
            throw new Exception("Dimensions of Output Layer and Labels do not match...");
        }
        
        int[][] trainOut = new int[y.size()][];
        
        for(int n = 0; n < y.size(); n++)
        {
            trainOut[n] = coder.encode(y.get(n));
        }
        
        super.train(x, Utils.intToDoubleArr(trainOut));
    } 

    @Override
    public T predict(double[] x) throws Exception
    {
        return coder.decode(Utils.maxProb(super.predictProbability(x)));
    }
}
