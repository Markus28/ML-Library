import java.util.List;
import java.util.Arrays;

/**
 * Beschreiben Sie hier die Klasse MultiClassMLP.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class MultiClassMLP extends Perzeptron implements MultiClassClassifier, java.io.Serializable
{
    OneHotCoder coder;
    
    public MultiClassMLP(int[] layers)
    {
        super(layers);
    }

    public void lernen(double[][] x, List y, List parameters) throws Exception
    {
        coder = new OneHotCoder(y);
        
        if(coder.encode(y.get(0)).length != getLayers()[getLayers().length - 1])
        {
            throw new Exception("Dimensions of Output Layer and Labels do not match...");
        }
        
        int[][] trainOut = new int[y.size()][];
        
        for(int n = 0; n < y.size(); n++)
        {
            trainOut[n] = coder.encode(y.get(n));
        }
        
        //System.out.println(Arrays.deepToString(trainOut));
        
        super.lernen(x, Utils.intToDoubleArr(trainOut), (int) parameters.get(0), (int) parameters.get(1), (double) parameters.get(2));
    } 
    
    public Object vorhersage(double[] x) throws Exception
    {
        return coder.decode(Utils.maxProb(super.vorhersageWahrscheinlichkeit(x)));
    }
}
