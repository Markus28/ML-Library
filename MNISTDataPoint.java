import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Beschreiben Sie hier die Klasse MNISTData.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class MNISTDataPoint implements CSVDataPoint
{
    private double[] features;
    private int label;
    
    public MNISTDataPoint()
    {}
    
    public MNISTDataPoint(double[] features, int label)
    {
        this.features = features;
        this.label = label;
    }
    
    public MNISTDataPoint fromLine(String[] line)
    {
        double[] feat = new double[line.length - 1];
        for(int n = 0; n < line.length-1; n++)
        {
            feat[n] = Double.valueOf(line[n+1])/255.0;
        }
        return new MNISTDataPoint(feat, Integer.valueOf(line[0]));
    }
    
    public String toLine()
    {
        String result = "";
        result += label + ",";
        
        for(int n = 0; n < features.length; n++)
        {
            result += features[n];
            if(n != features.length - 1)
            {
                result += ",";
            }
        }
        
        return result;
    }

    public double[] getFeatures()
    {
        return features;
    }
    
    public int getLabel()
    {
        return label;
    }
}
